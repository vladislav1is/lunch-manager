package com.redfox.lunchmanager.repository.datajpa;

import com.redfox.lunchmanager.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Transactional
    @Modifying
    @Query(name = Restaurant.DELETE)
    int delete(@Param("id") int id);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes ds WHERE r.id = ?1 and ds.registered=?2")
    Restaurant getWithDishesByDate(int id, LocalDate localDate);
}
