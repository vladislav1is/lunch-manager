package com.redfox.lunchmanager.repository.jpa;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.springframework.dao.support.DataAccessUtils;
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

    @Override
    @Transactional
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

    @Override
    @Transactional
    public boolean delete(int id, int restaurantId) {
        return em.createNamedQuery(Vote.DELETE)
                .setParameter("id", id)
                .setParameter("restaurantId", restaurantId)
                .executeUpdate() != 0;
    }

    @Override
    public Vote get(int id, int restaurantId) {
        var vote = em.find(Vote.class, id);
        return vote != null && vote.getRestaurant().id() == restaurantId ? vote : null;
    }

    @Override
    public Vote getByDate(LocalDate voteDate, int userId) {
        var votes = em.createNamedQuery(Vote.BY_DATE, Vote.class)
                .setParameter("voteDate", voteDate)
                .setParameter("userId", userId)
                .getResultList();
        return DataAccessUtils.singleResult(votes);
    }

    @Override
    public List<Vote> getAll(int restaurantId) {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

    @Override
    public List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return em.createNamedQuery(Vote.GET_BETWEEN, Vote.class)
                .setParameter("restaurantId", restaurantId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
