package com.redfox.lunchmanager.repository.datajpa;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import com.redfox.lunchmanager.web.SecurityUtil;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository, CrudUserRepository crudUserRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    @Transactional
    @Modifying
    public Vote save(Vote vote, int restaurantId) {
        vote.setUser(crudUserRepository.getById(SecurityUtil.authUserId()));
        vote.setRestaurant(crudRestaurantRepository.getById(restaurantId));
        if (!vote.isNew() && crudVoteRepository.getById(vote.id()) == null) {
            return null;
        }
        return crudVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudVoteRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public Vote get(int id, int restaurantId) {
        return crudVoteRepository.findById(id)
                .filter(vote -> vote.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    public Vote getByDate(LocalDate voteDate, int userId) {
        return crudVoteRepository.getByDate(voteDate, userId);
    }

    @Override
    public List<Vote> getAll(int restaurantId) {
        return crudVoteRepository.getAll(restaurantId);
    }

    @Override
    public List<Vote> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return crudVoteRepository.getBetweenHalfOpen(startDate, endDate, restaurantId);
    }

    @Override
    public Vote getWithRestaurant(int id, int restaurantId) {
        return crudVoteRepository.getWithRestaurant(id, restaurantId);
    }
}
