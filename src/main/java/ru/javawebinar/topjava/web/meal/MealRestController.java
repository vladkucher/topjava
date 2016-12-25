package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll(){
        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id()),2000);
    }

    public Meal get(int id){
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal){
        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id){
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal){
        service.update(meal, AuthorizedUser.id());
    }

    public List<MealWithExceed> getWithFilter(LocalDateTime startDateTime, LocalDateTime endDateTime){
        return MealsUtil.getFilteredWithExceeded(service.getWithFilter(startDateTime,endDateTime, AuthorizedUser.id()),2000);
    }

}
