package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;


import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;


    @Override
    public Meal save(Meal meal,int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId),userId);
    }

    @Override
    public Meal get(int id, int userId) {
        return  checkNotFoundWithId(repository.get(id, userId),userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<Meal> getWithFilter(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return repository.getWithFilter(startDateTime, endDateTime, userId);
    }

    @Override
    public void update(Meal meal, int userId) {
        repository.save(meal, userId);
    }
}
