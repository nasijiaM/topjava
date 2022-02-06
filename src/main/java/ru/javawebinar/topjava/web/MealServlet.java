package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMapImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final static String ADD_OR_UPDATE = "/meal.jsp";
    private final static String LIST_OF_MEALS = "/meals.jsp";

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private MealDao mealDao = new MealDaoMapImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action == null){
            List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.getAllMeals(), null, null, 2000);
            request.setAttribute("meals", meals);
            forward = LIST_OF_MEALS;
            log.debug("redirect to meals");
        }
        else if (action.equalsIgnoreCase("delete")){
            Integer mealId = Integer.parseInt(request.getParameter("mealId"));
            mealDao.deleteMeal(mealId);

            List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.getAllMeals(), null, null, 2000);
            request.setAttribute("meals", meals);
            forward = LIST_OF_MEALS;
            log.debug("redirect to meals");
        } else if (action.equalsIgnoreCase("update")){
            Integer mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealDao.getMealById(mealId);

            request.setAttribute("meal", meal);
            forward = ADD_OR_UPDATE;
            log.debug("redirect to edit meal");
        } else if (action.equalsIgnoreCase("add")) {
            forward = ADD_OR_UPDATE;
            log.debug("redirect to add meal");
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Integer mealId = (request.getParameter("mealId").equals("")) ? 0 : Integer.parseInt(request.getParameter("mealId"));
        Meal meal = new Meal(mealId,
                LocalDateTime.parse(request.getParameter("dateTime"), dtf),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        mealDao.addMeal(meal);

        List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.getAllMeals(), null, null, 2000);
        request.setAttribute("meals", meals);
        log.debug("redirect to meals");
        request.getRequestDispatcher(LIST_OF_MEALS).forward(request, response);
    }
}
