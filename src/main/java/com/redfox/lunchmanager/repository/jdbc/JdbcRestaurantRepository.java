package com.redfox.lunchmanager.repository.jdbc;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.repository.RestaurantRepository;
import com.redfox.lunchmanager.util.validation.ValidationUtil;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcRestaurantRepository implements RestaurantRepository {

    private static final BeanPropertyRowMapper<Restaurant> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Restaurant.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertRestaurant;

    public JdbcRestaurantRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertRestaurant = new SimpleJdbcInsert(jdbcTemplate)
                .usingGeneratedKeyColumns("id")
                .withTableName("restaurants");
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        ValidationUtil.validate(restaurant);

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(restaurant);
        if (restaurant.isNew()) {
            Number newKey = insertRestaurant.executeAndReturnKey(parameterSource);
            restaurant.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                UPDATE restaurants SET name=:name WHERE id=:id""", parameterSource) == 0) {
            return null;
        }
        return restaurant;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM restaurants WHERE id=?", id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        var restaurants = jdbcTemplate.query("SELECT * FROM restaurants WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(restaurants);
    }

    @Override
    public List<Restaurant> getAll() {
        return jdbcTemplate.query("SELECT * FROM restaurants ORDER BY name", ROW_MAPPER);
    }
}
