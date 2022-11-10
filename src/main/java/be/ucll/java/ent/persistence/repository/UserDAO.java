package be.ucll.java.ent.persistence.repository;

import be.ucll.java.ent.persistence.entities.UserEnt;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO implements Dao<UserEnt> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(UserEnt user) {
        em.persist(user);
    }

    @Override
    // Gebruik Optional om aanroepende code af te dwingen en rekening te houden met NULL
    public Optional<UserEnt> get(long userid) {
        return Optional.ofNullable(em.find(UserEnt.class, userid));
    }

    @Override
    // Zonder Optional kan de return value null zijn en kan je alleen maar hopen
    // dat de aanroepende code daarmee rekening houdt
    public UserEnt read(long userid) {
        return em.find(UserEnt.class, userid);
    }

    public Optional<UserEnt> getOneByLoginId(String loginId) {
        if (loginId == null || loginId.trim().length() == 0) return Optional.ofNullable(null);

        try {
            UserEnt user = null;
            try {
                Query q = em.createQuery("select e from UserEnt e where lower(e.loginId) = :p1");
                q.setParameter("p1", loginId.toLowerCase());
                user = (UserEnt) q.getSingleResult();
            } catch (NoResultException e) {
                // ignore
            }
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<UserEnt> getOneByApiKey(String apikey) {
        if (apikey == null || apikey.trim().length() == 0) return Optional.ofNullable(null);

        try {
            UserEnt user = null;
            try {
                Query q = em.createQuery("select e from UserEnt e where e.apikey = :p1");
                q.setParameter("p1", apikey.trim());
                user = (UserEnt) q.getSingleResult();
            } catch (NoResultException e) {
                // ignore
            }
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserEnt user) {
        em.merge(user);
    }

    @Override
    public void delete(long userid) {
        UserEnt ref = em.getReference(UserEnt.class, userid);
        if (ref != null) {
            em.remove(ref);
        } else {
            // Already removed
        }
    }

    @Override
    public List<UserEnt> getAll() {
        return em.createNamedQuery("User.getAll").getResultList();
    }

    @Override
    public long countAll() {
        Object o = em.createNamedQuery("User.countAll").getSingleResult();
        return (Long) o;
    }
}



