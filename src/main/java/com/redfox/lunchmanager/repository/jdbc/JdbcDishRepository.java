package com.redfox.lunchmanager.repository.jdbc;

import com.redfox.lunchmanager.model.Dish;
import com.redfox.lunchmanager.repository.DishRepository;
import com.redfox.lunchmanager.util.validation.ValidationUtil;
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
public class JdbcDishRepository implements DishRepository {

    private static final BeanPropertyRowMapper<Dish> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Dish.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertDish;

    public JdbcDishRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertDish = new SimpleJdbcInsert(jdbcTemplate)
                .usingGeneratedKeyColumns("id")
                .withTableName("dishes");
    }

    @Override
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        ValidationUtil.validate(dish);

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", dish.getId())
                .addValue("name", dish.getName())
                .addValue("price", dish.getPrice())
                .addValue("registered", dish.getRegistered())
                .addValue("restaurant_id", restaurantId);
        if (dish.isNew()) {
            Number newKey = insertDish.executeAndReturnKey(map);
            dish.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                UPDATE dishes SET name=:name, price=:price, registered=:registered
                WHERE id=:id AND restaurant_id=:restaurant_id""", map) == 0) {
            return null;
        }
        return dish;
    }

    @Override
    @Transactional
    public boolean delete(int id, int restaurantId) {
        return jdbcTemplate.update("DELETE FROM dishes WHERE id=? AND restaurant_id=?", id, restaurantId) != 0;
    }

    @Override
    public Dish get(int id, int restaurantId) {
        var dishes = jdbcTemplate.query("SELECT * FROM dishes WHERE id=? AND restaurant_id=?",
                ROW_MAPPER, id, restaurantId);
        return DataAccessUtils.singleResult(dishes);
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return jdbcTemplate.query("SELECT * FROM dishes WHERE restaurant_id=? ORDER BY registered DESC",
                ROW_MAPPER, restaurantId);
    }

    @Override
    public List<Dish> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return jdbcTemplate.query("""
                SELECT * FROM dishes WHERE restaurant_id=? AND registered >= ? AND registered < ? 
                ORDER BY registered DESC""", ROW_MAPPER, restaurantId, startDate, endDate);
    }
}