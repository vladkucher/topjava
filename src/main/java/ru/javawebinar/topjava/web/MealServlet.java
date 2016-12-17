package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    private MealDao mealDao;
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String MEALS = "/meals.jsp";

    public MealServlet() {
        this.mealDao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("test");
        String forward="";

        String action = request.getParameter("action");
        System.out.println(action);

        if (action.equalsIgnoreCase("delete")){
            int Id = Integer.parseInt(request.getParameter("Id"));
            mealDao.deleteMeal(Id);
            forward = MEALS;
            request.setAttribute("meals",MealsUtil.getFilteredWithExceeded(mealDao.getAllMeals(),LocalTime.MIN,LocalTime.MAX,2000));
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int Id = Integer.parseInt(request.getParameter("Id"));
            Meal meal = mealDao.getMealById(Id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeal")){
            forward = MEALS;
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDao.getAllMeals(),LocalTime.MIN,LocalTime.MAX,2000));
        } else {
            forward = INSERT_OR_EDIT;
        }

        getServletContext().getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"),d);

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(localDateTime,description,calories);


        String id = request.getParameter("id");
        if(id == null || id.isEmpty())
        {
            mealDao.addMeal(meal);
        }
        else
        {
            Meal.getAtomicInteger().incrementAndGet();
            meal.setId(Integer.parseInt(id));
            mealDao.updateMeal(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(MEALS);
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDao.getAllMeals(),LocalTime.MIN,LocalTime.MAX,2000));
        view.forward(request, response);
    }


    /*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            int userId = Integer.parseInt(request.getParameter("userId"));
            dao.deleteUser(userId);
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int userId = Integer.parseInt(request.getParameter("userId"));
            User user = dao.getUserById(userId);
            request.setAttribute("user", user);
        } else if (action.equalsIgnoreCase("listUser")){
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        try {
            Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("dob"));
            user.setDob(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setEmail(request.getParameter("email"));
        String userid = request.getParameter("userid");
        if(userid == null || userid.isEmpty())
        {
            dao.addUser(user);
        }
        else
        {
            user.setUserid(Integer.parseInt(userid));
            dao.updateUser(user);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
        request.setAttribute("users", dao.getAllUsers());
        view.forward(request, response);
    }*/
}
