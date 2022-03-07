package com.redfox.lunchmanager.repository.jpa;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaVoteRepository implements VoteRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Modifying
    @Override
    public Vote save(Vote vote, int restaurantId) {
        vote.setUser(em.getReference(User.class, SecurityUtil.authUserId()));
        vote.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        if (vote.isNew()) {
            em.persist(vote);
            return vote;
        } else if (em.find(Vote.class, vote.getId()) == null) {
            return null;
        }
        return em.merge(vote);
    }

    @Transactional
    @Modifying
    @Override
    public boolean delete(int id, int restaurantId) {
        return em.createNamedQuery(Vote.DELETE)
                .setParameter("id", id)
                .setParameter("restaurantId", restaurantId)
                .executeUpdate() != 0;
    }

    @Transactional
    @Modifying
    @Override
    public boolean deleteAllBy(int restaurantId) {
        return em.createNamedQuery(Vote.DELETE_ALL_BY_RESTAURANT_ID)
                .setParameter("restaurantId", restaurantId)
                .executeUpdate() != 0;
    }

    @Override
    public Vote getBy(LocalDate voteDate, int userId) {
        var votes = em.createNamedQuery(Vote.BY_DATE_AND_USER_ID, Vote.class)
                .setParameter("voteDate", voteDate)
                .setParameter("userId", userId)
                .getResultList();
        return DataAccessUtils.singleResult(votes);
    }

    @Override
    public List<Vote> getAll(int userId) {
        return em.createNamedQuery(Vote.ALL_SORTED_BY_USER_ID, Vote.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public int countBy(LocalDate voteDate, int restaurantId) {
        Object result = em.createNativeQuery("SELECT COUNT(*) FROM votes v WHERE vote_date=:voteDate AND v.restaurant_id=:restaurantId")
                .setParameter("voteDate", voteDate)
                .setParameter("restaurantId", restaurantId)
                .getSingleResult();
        if (result != null) {
            return Integer.parseInt(result.toString());
        }
        return 0;
    }
}
