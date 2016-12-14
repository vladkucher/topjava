package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);


   private List<Meal> meals = Arrays.asList(
           new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
           new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
           new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
           new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
           new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
           new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
   );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("test");


        request.setAttribute("list", MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN,LocalTime.MAX,2000));
        getServletContext().getRequestDispatcher("/meals.jsp").forward(request, response);
    }

}
