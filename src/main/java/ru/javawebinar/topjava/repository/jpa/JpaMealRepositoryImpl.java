package ru.javawebinar.topjava.repository.jpa;

import org.hibernate.query.Query;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
public class JpaMealRepositoryImpl implements MealRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(em.find(User.class,userId));
        if(meal.isNew()){
            em.persist(meal);
            return meal;
        }else {

            if(em.find(Meal.class,meal.getId()).getUser().getId()==userId) {
                em.merge(meal);
                return meal;
            }else
            return null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query<Meal> query = (Query<Meal>) em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId");
        return query.setParameter("id", id).setParameter("userId",userId).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> mealList = em.createQuery("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
                    .setParameter("id", id).setParameter("userId", userId).getResultList();
        if(mealList.isEmpty()){
            return null;
        }else {
            return DataAccessUtils.singleResult(mealList);
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC").setParameter("userId",userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN ?1  AND ?2  ORDER BY m.dateTime DESC")
                .setParameter("userId",userId).setParameter("1",startDate).setParameter("2",endDate).getResultList();
    }
}