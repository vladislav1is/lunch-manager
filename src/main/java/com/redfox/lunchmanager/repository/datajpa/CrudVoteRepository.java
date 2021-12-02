package com.redfox.lunchmanager.repository.datajpa;

import com.redfox.lunchmanager.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query(name = Vote.DELETE)
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Query(name = Vote.BY_DATE)
    Vote getByDate(@Param("voteDate") LocalDate voteDate, @Param("userId") int userId);

    @Query(name = Vote.ALL_SORTED)
    List<Vote> getAll(@Param("restaurantId") int restaurantId);

    @Query(name = Vote.GET_BETWEEN)
    List<Vote> getBetweenHalfOpen(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                  @Param("restaurantId") int restaurantId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.id = ?1 and v.restaurant.id = ?2")
    Vote getWithRestaurant(int id, int restaurantId);
}
