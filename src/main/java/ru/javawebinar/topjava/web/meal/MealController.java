package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.MealServlet;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
@RequestMapping(value = "/meals")
public class MealController extends MealRestController{

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @RequestMapping(method = RequestMethod.GET)
    public String getMeals(Model model){
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getMealsWithFilter(HttpServletRequest request){
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String creatMeal(HttpServletRequest request){
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateMeal(HttpServletRequest request){
        final Meal meal = get(getId(request));
        request.setAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveMeal(HttpServletRequest request){
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if(request.getParameter("id").isEmpty()) {
            create(meal);
        }
        else {
            update(meal,getId(request));
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteMeal(HttpServletRequest request){
        int id = getId(request);
        delete(id);
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
