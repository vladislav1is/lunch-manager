package com.redfox.lunchmanager.repository.datajpa;

import com.redfox.lunchmanager.model.Vote;
import com.redfox.lunchmanager.repository.VoteRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private final CrudVoteRepository crudVoteRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository) {
        this.crudVoteRepository = crudVoteRepository;
    }

    @Override
    public Vote save(Vote vote, int restaurantId) {
        return crudVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudVoteRepository.deleteBy(id, restaurantId) != 0;
    }

    @Override
    public boolean deleteAllBy(int restaurantId) {
        return crudVoteRepository.deleteAllBy(restaurantId) != 0;
    }

    @Override
    public Vote getBy(LocalDate voteDate, int userId) {
        return crudVoteRepository.getBy(voteDate, userId);
    }

    @Override
    public List<Vote> getAll(int userId) {
        return crudVoteRepository.getAllBy(userId);
    }

    @Override
    public int countBy(LocalDate voteDate, int restaurantId) {
        return crudVoteRepository.countBy(voteDate, restaurantId);
    }
}
