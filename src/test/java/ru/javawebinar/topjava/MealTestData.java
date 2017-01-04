package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final int MEAL_ID = BaseEntity.START_SEQ;

    public static final Meal MEAL = new Meal(MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL_ID+1,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    //public static final Meal MEAL3 = new Meal(MEAL_ID+1,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    /*public static final Meal meal4 = new Meal(MEAL_ID+3,LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal5 = new Meal(MEAL_ID+4,LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal meal6 = new Meal(MEAL_ID+5,LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);*/

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual)-> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                    )
    );

}
