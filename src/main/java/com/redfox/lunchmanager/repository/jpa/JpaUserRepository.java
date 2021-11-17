package com.redfox.lunchmanager.repository.jpa;

import com.redfox.lunchmanager.model.User;
import com.redfox.lunchmanager.repository.UserRepository;
import org.hibernate.jpa.QueryHints;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaUserRepository implements UserRepository {

/*
    @Autowired
    private SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }
*/

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else if (get(user.id()) == null) {
            return null;
        }
        return em.merge(user);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
/*
        User ref = em.getReference(User.class, id);
        em.remove(ref);
*/
/*
        Query query = em.createQuery("DELETE FROM User u WHERE u.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;
*/
        return em.createNamedQuery(User.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        var users = em.createNamedQuery(User.BY_EMAIL, User.class)
                .setParameter(1, email)
                //    https://stackoverflow.com/questions/55921415/hint-hint-pass-distinct-through-reduces-the-amount-of-entities-returned-per-page#answer-63537865
                //    https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#hql-distinct-entity-query
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class).getResultList();
    }
}
