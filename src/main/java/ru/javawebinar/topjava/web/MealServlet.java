package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.InMemoryMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String ADD_OR_UPDATE = "/meal.jsp";
    private static final String LIST_OF_MEALS = "/meals.jsp";
    private static final String MEAL_SERVLET = "meals";
    private final int CALORIES_PER_DAY = 2000;

    private MealDao mealDao;

    @Override
    public void init() {
        mealDao = new InMemoryMealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = (request.getParameter("action") == null) ? "show list" : request.getParameter("action");

        switch (action) {
            case "delete":
                mealDao.delete(getId(request));
                StringBuilder message = new StringBuilder("Meal{Id = ")
                        .append(getId(request))
                        .append("} was deleted. Redirect to MealServlet.");
                log.debug(message.toString());
                response.sendRedirect(MEAL_SERVLET);
                break;
            case "update":
                request.setAttribute("meal", mealDao.getById(getId(request)));
                forward = ADD_OR_UPDATE;
                log.debug("Forward to meal.jsp. Action = 'update'");
                request.getRequestDispatcher(forward).forward(request, response);
                break;
            case "add":
                forward = ADD_OR_UPDATE;
                log.debug("Forward to meal.jsp. Action = 'add'");
                request.getRequestDispatcher(forward).forward(request, response);
                break;
            default:
                List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.getAll(), null, null, CALORIES_PER_DAY);
                request.setAttribute("meals", meals);
                forward = LIST_OF_MEALS;
                log.debug("Forward to meals.jsp");
                request.getRequestDispatcher(forward).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Integer mealId = (request.getParameter("mealId").isEmpty()) ? null : getId(request);
        Meal meal = new Meal(mealId,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        Meal mealToUpdate = mealDao.save(meal);

        List<MealTo> meals = MealsUtil.filteredByStreams(mealDao.getAll(), null, null, CALORIES_PER_DAY);
        request.setAttribute("meals", meals);

        StringBuilder message = new StringBuilder();
        if (mealId == null) message.append("New meal was added. Redirect to MealServlet.");
        else if (mealToUpdate != null) {
            message.append("Meal{Id = ")
                    .append(mealId)
                    .append("} was updated. Redirect to MealServlet.");
        } else {
            message.append("There is no meal with id = ")
                    .append(mealId)
                    .append("Redirect to MealServlet.");
        }
        log.debug(message.toString());

        response.sendRedirect(MEAL_SERVLET);
    }

    public Integer getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("mealId"));
    }
}
