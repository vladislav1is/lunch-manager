package com.redfox.lunchmanager.repository.jdbc;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import com.redfox.lunchmanager.util.validation.ValidationUtil;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcVoteRepository implements VoteRepository {

    private static final BeanPropertyRowMapper<Vote> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Vote.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertVote;

    public JdbcVoteRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertVote = new SimpleJdbcInsert(jdbcTemplate)
                .usingGeneratedKeyColumns("id")
                .withTableName("votes");
    }

    @Transactional
    @Modifying
    @Override
    public Vote save(Vote vote, int restaurantId) {
        ValidationUtil.validate(vote);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(vote);
        if (vote.isNew()) {
            Number newKey = insertVote.executeAndReturnKey(parameterSource);
            vote.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                UPDATE votes SET user_id=:userId, restaurant_id=:restaurantId, vote_date=:voteDate
                WHERE id=:id""", parameterSource) == 0) {
            return null;
        }
        setUser(vote, vote.getUserId());
        setRestaurant(vote, restaurantId);
        return vote;
    }

    private void setUser(Vote vote, int userId) {
        if (vote != null && userId != 0) {
            List<User> users = jdbcTemplate.query("""
                    SELECT * FROM users u LEFT JOIN votes v ON u.id=v.user_id
                    WHERE user_id=? AND v.vote_date=?""", (rs, i) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                return user;
            }, userId, vote.getVoteDate());
            vote.setUser(DataAccessUtils.singleResult(users));
        }
    }

    private void setRestaurant(Vote vote, int restaurantId) {
        if (vote != null && restaurantId != 0) {
            List<Restaurant> restaurants = jdbcTemplate.query("""
                    SELECT * FROM restaurants r LEFT JOIN votes v ON r.id=v.restaurant_id
                    WHERE restaurant_id=? AND v.vote_date=?""", (rs, i) -> {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(rs.getInt("id"));
                return restaurant;
            }, restaurantId, vote.getVoteDate());
            vote.setRestaurant(DataAccessUtils.singleResult(restaurants));
        }
    }

    @Transactional
    @Modifying
    @Override
    public boolean delete(int id, int restaurantId) {
        return jdbcTemplate.update("DELETE FROM votes WHERE id=? AND restaurant_id=?", id, restaurantId) != 0;
    }

    @Transactional
    @Modifying
    @Override
    public boolean deleteAllBy(int restaurantId) {
        return jdbcTemplate.update("DELETE FROM votes WHERE restaurant_id=?", restaurantId) != 0;
    }

    @Override
    public Vote getBy(LocalDate voteDate, int userId) {
        var votes = jdbcTemplate.query("SELECT * FROM votes WHERE vote_date=? AND user_id=?",
                ROW_MAPPER, voteDate, userId);
        var vote = DataAccessUtils.singleResult(votes);
        setRestaurant(vote, voteDate, userId);
        return vote;
    }

    private void setRestaurant(Vote vote, LocalDate voteDate, int userId) {
        if (vote != null) {
            List<Restaurant> restaurants = jdbcTemplate.query("""
                    SELECT * FROM restaurants r LEFT JOIN votes v ON r.id=v.restaurant_id
                    WHERE v.user_id=? AND v.vote_date=?""", (rs, i) -> {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(rs.getInt("id"));
                return restaurant;
            }, userId, voteDate);
            vote.setRestaurant(DataAccessUtils.singleResult(restaurants));
        }
    }

    @Override
    public List<Vote> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM votes WHERE user_id=? ORDER BY vote_date DESC",
                ROW_MAPPER, userId);
    }

    @Override
    public int countBy(LocalDate voteDate, int restaurantId) {
        Integer votesNum = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM votes WHERE vote_date=? AND restaurant_id=?", Integer.class, voteDate, restaurantId);
        return votesNum == null ? 0 : votesNum;
    }
}
