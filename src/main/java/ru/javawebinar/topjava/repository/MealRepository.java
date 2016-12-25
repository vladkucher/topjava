package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal meal, int userId);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getWithFilter(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}
