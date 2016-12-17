package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealDao {

    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    {
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }



    public void addMeal(Meal meal) {
        meals.put(meal.getId(),meal);
    }

    public void deleteMeal(int Id) {
        meals.remove(Id);
    }

    public void updateMeal(Meal meal) {
        meals.remove(meal.getId());
        meals.put(meal.getId(),meal);
    }

    public List<Meal> getAllMeals() {
        return new CopyOnWriteArrayList<>(meals.values());
    }

    public Meal getMealById(int Id) {
        return meals.get(Id);
    }
}
