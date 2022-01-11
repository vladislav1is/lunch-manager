package com.redfox.lunchmanager.repository.jdbc;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import com.redfox.lunchmanager.util.validation.ValidationUtil;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

    @Override
    @Transactional
    public Vote save(Vote vote, int restaurantId) {
        ValidationUtil.validate(vote);

        int userId = SecurityUtil.authUserId();
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", vote.getId())
                .addValue("userId", userId)
                .addValue("restaurantId", restaurantId)
                .addValue("registered", vote.getRegistered());
        if (vote.isNew()) {
            Number newKey = insertVote.executeAndReturnKey(map);
            vote.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                UPDATE votes SET user_id=:userId, restaurant_id=:restaurantId, registered=:registered
                WHERE id=:id""", map) == 0) {
            return null;
        }
        setUser(vote, userId);
        setRestaurant(vote, restaurantId);
        return vote;
    }

    private void setUser(Vote vote, int userId) {
        if (vote != null && userId != 0) {
            List<User> users = jdbcTemplate.query("""
                    SELECT * FROM users u LEFT JOIN votes v ON u.id=v.user_id
                    WHERE user_id=? AND v.registered=?""", (rs, i) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                return user;
            }, userId, vote.getRegistered());
            vote.setUser(DataAccessUtils.singleResult(users));
        }
    }

    private void setRestaurant(Vote vote, int restaurantId) {
        if (vote != null && restaurantId != 0) {
            List<Restaurant> restaurants = jdbcTemplate.query("""
                    SELECT * FROM restaurants r LEFT JOIN votes v ON r.id=v.restaurant_id
                    WHERE restaurant_id=? AND v.registered=?""", (rs, i) -> {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(rs.getInt("id"));
                return restaurant;
            }, restaurantId, vote.getRegistered());
            vote.setRestaurant(DataAccessUtils.singleResult(restaurants));
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int restaurantId) {
        return jdbcTemplate.update("DELETE FROM votes WHERE id=? AND restaurant_id=?", id, restaurantId) != 0;
    }

    @Override
    public Vote get(int id, int restaurantId) {
        var votes = jdbcTemplate.query("SELECT * FROM votes WHERE id=? AND restaurant_id=?",
                ROW_MAPPER, id, restaurantId);
        return DataAccessUtils.singleResult(votes);
    }

    @Override
    public Vote getByDate(LocalDate voteDate, int userId) {
        var votes = jdbcTemplate.query("SELECT * FROM votes WHERE registered=? AND user_id=?",
                ROW_MAPPER, voteDate, userId);
        var vote = DataAccessUtils.singleResult(votes);
        setRestaurant(vote, voteDate, userId);
        return vote;
    }

    private void setRestaurant(Vote vote, LocalDate voteDate, int userId) {
        if (vote != null) {
            List<Restaurant> restaurants = jdbcTemplate.query("""
                    SELECT * FROM restaurants r LEFT JOIN votes v ON r.id=v.restaurant_id
                    WHERE v.user_id=? AND v.registered=?""", (rs, i) -> {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(rs.getInt("id"));
                return restaurant;
            }, userId, voteDate);
            vote.setRestaurant(DataAccessUtils.singleResult(restaurants));
        }
    }

    @Override
    public List<Vote> getAll(int restaurantId) {
        return jdbcTemplate.query("SELECT * FROM votes WHERE restaurant_id=? ORDER BY registered DESC",
                ROW_MAPPER, restaurantId);
    }

    @Override
    public List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return jdbcTemplate.query("SELECT * FROM votes WHERE restaurant_id=? AND registered >= ? AND registered < ? " +
                        "ORDER BY registered DESC",
                ROW_MAPPER, restaurantId, startDate, endDate);
    }
}
