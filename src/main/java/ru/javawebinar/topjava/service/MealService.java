package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal meal, int userId);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws  NotFoundException;

    List<Meal> getAll(int userId);

    List<Meal> getWithFilter(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    void update(Meal meal, int userId);
}
