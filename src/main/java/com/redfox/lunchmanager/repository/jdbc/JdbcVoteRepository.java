package com.redfox.lunchmanager.repository.jdbc;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import com.redfox.lunchmanager.util.ValidationUtil;
import org.springframework.dao.support.DataAccessUtils;
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

    @Override
    @Transactional
    public Vote save(Vote vote, int userId) {
        ValidationUtil.validate(vote);

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(vote);
        if (vote.isNew()) {
            Number newKey = insertVote.executeAndReturnKey(parameterSource);
            vote.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                UPDATE votes SET user_id=:user_id, restaurant_id=:restaurant_id, registered=:registered
                WHERE id=:id AND user_id=:user_id""", parameterSource) == 0) {
            return null;
        }
        return vote;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM votes WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Vote get(int id, int userId) {
        var votes = jdbcTemplate.query("SELECT * FROM votes WHERE id=? AND user_id=?",
                ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(votes);
    }

    @Override
    public Vote getByDate(LocalDate voteDate, int userId) {
        var votes = jdbcTemplate.query("SELECT * FROM votes WHERE votes.registered=? AND user_id=?",
                ROW_MAPPER, voteDate, userId);
        return DataAccessUtils.singleResult(votes);
    }

    @Override
    public List<Vote> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM votes WHERE user_id=? ORDER BY registered DESC",
                ROW_MAPPER, userId);
    }

    @Override
    public List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM votes WHERE user_id=? AND registered >= ? AND registered < ? " +
                        "ORDER BY registered DESC",
                ROW_MAPPER, userId, startDate, endDate);
    }
}
