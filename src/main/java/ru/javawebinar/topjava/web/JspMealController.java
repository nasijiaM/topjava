package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService mealService;

    @GetMapping()
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/filter")
    public String getBetween(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = mealService.getBetweenInclusive(startDate, endDate, userId);
        request.setAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        mealService.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable int id, HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        Meal meal = mealService.get(id, userId);
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/save")
    public String save(HttpServletRequest request) throws UnsupportedEncodingException {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        int userId = SecurityUtil.authUserId();
        if (StringUtils.hasLength(request.getParameter("id"))) {
            assureIdConsistent(meal, Integer.parseInt(request.getParameter("id")));
            log.info("update {} for user {}", meal, userId);
            mealService.update(meal, userId);
        } else {
            log.info("create {} for user {}", meal, userId);
            mealService.create(meal, userId);
        }
        return "redirect:/meals";
    }
}