package com.redfox.lunchmanager.repository.datajpa;

import com.redfox.lunchmanager.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Transactional
    @Modifying
    @Query(name = Dish.DELETE)
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Query(name = Dish.ALL_SORTED)
    List<Dish> getAll(@Param("restaurantId") int restaurantId);

    @Query(name = Dish.GET_BETWEEN)
    List<Dish> getBetweenHalfOpen(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.id = ?1 and d.restaurant.id = ?2")
    Dish getWithRestaurant(int id, int restaurantId);
}
