package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CrudMealRepository crudRepository;

    /*rewrite*/
    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(em.getReference(User.class, userId));
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id,userId)!=0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findOneByIdAndUser_Id(id,userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findByUser_IdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.findByUser_IdAndDateTimeBetweenOrderByDateTimeDesc(userId, startDate, endDate);
    }
}
