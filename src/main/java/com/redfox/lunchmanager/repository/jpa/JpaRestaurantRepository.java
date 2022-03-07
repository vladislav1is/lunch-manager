package com.redfox.lunchmanager.repository.jpa;

import com.redfox.lunchmanager.model.Restaurant;
import com.redfox.lunchmanager.repository.RestaurantRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaRestaurantRepository implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Modifying
    @Override
    public Restaurant save(Restaurant restaurant) {
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else if (get(restaurant.id()) == null) {
            return null;
        }
        return em.merge(restaurant);
    }

    @Transactional
    @Modifying
    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(Restaurant.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Restaurant get(int id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.ALL_SORTED, Restaurant.class).getResultList();
    }
}
