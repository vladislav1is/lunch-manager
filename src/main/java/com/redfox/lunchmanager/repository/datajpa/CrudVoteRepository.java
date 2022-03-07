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
    int deleteBy(@Param("id") int id, @Param("restaurantId") int restaurantId);

    @Transactional
    @Modifying
    @Query(name = Vote.DELETE_ALL_BY_RESTAURANT_ID)
    int deleteAllBy(@Param("restaurantId") int restaurantId);

    @Query(name = Vote.ALL_SORTED_BY_USER_ID)
    List<Vote> getAllBy(@Param("userId") int userId);

    @Query(name = Vote.BY_DATE_AND_USER_ID)
    Vote getBy(@Param("voteDate") LocalDate voteDate, @Param("userId") int userId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.voteDate=:voteDate AND v.restaurantId=:restaurantId")
    int countBy(@Param("voteDate") LocalDate voteDate, @Param("restaurantId") int restaurantId);
}
