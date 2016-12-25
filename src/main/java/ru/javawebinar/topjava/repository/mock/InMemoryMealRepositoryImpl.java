package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
                save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), AuthorizedUser.id());
                save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000), AuthorizedUser.id());
                save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500), AuthorizedUser.id());
                save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000), AuthorizedUser.id());
                save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), AuthorizedUser.id());
                save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), AuthorizedUser.id());
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer,Meal> mealMap = repository.get(userId);
        if(mealMap!=null) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
            }
            mealMap.put(meal.getId(),meal);
            repository.put(userId, mealMap);
            return meal;
        }else {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
            }
            Map<Integer,Meal> mealMap1= new ConcurrentHashMap<>();
            mealMap1.put(meal.getId(),meal);
            repository.put(userId,mealMap1);
            return meal;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer,Meal> mealMap = repository.get(userId);
        if(mealMap==null) return false;
        else {
            repository.get(userId).remove(id);
            return true;
        }
    }


    @Override
    public Meal get(int id, int userId) {
        Map<Integer,Meal> mealMap = repository.get(userId);
        return mealMap!=null?mealMap.get(id):null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.get(userId).values().stream()
                .sorted((m1,m2)->m1.getDateTime().compareTo(m2.getDateTime()))
                .collect(Collectors.toList());
    }
    @Override
    public List<Meal> getWithFilter(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId){
        return getAll(userId).stream()
                .filter(m-> DateTimeUtil.isBetween(m.getDateTime(), startDateTime, endDateTime))
                .sorted((m1,m2)->m1.getDateTime().compareTo(m2.getDateTime()))
                .collect(Collectors.toList());
    }
}

