package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static ru.javawebinar.topjava.MealTestData.*;

import static org.junit.Assert.*;

/**
 * Created by vlad on 03.01.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {
    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        Meal createdMeal = service.save(newMeal, AuthorizedUser.id());
        newMeal.setId(createdMeal.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal,MEAL2,MEAL),service.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL_ID,AuthorizedUser.id());
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL2),service.getAll(AuthorizedUser.id()));
    }

    @Test(expected = NotFoundException.class)
    public void testForeignDelete() throws Exception {
        service.delete(MEAL_ID,100);
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL_ID,AuthorizedUser.id());
        MATCHER.assertEquals(MEAL,meal);
    }

    @Test(expected = NotFoundException.class)
    public void testForeignGet() throws Exception {
        service.get(MEAL_ID,100);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = new Meal(MEAL);
        meal.setCalories(600);
        service.update(meal,AuthorizedUser.id());
        MATCHER.assertEquals(meal,service.get(MEAL_ID,AuthorizedUser.id()));
    }

    @Test(expected = NotFoundException.class)
    public void testForeignUpdate() throws Exception {
        Meal meal = new Meal(MEAL);
        meal.setCalories(600);
        service.update(meal,100);
    }


    @Test
    public void getAll() throws Exception {
        List<Meal> mealList = (List<Meal>) service.getAll(AuthorizedUser.id());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL2,MEAL),mealList);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        List<Meal> mealList = (List<Meal>) service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 13, 0),AuthorizedUser.id());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL,MEAL2),mealList);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        List<Meal> mealList = (List<Meal>) service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30),AuthorizedUser.id());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL,MEAL2),mealList);
    }

}