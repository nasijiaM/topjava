package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMapImpl implements MealDao {
    private static AtomicInteger count = new AtomicInteger();
    private static Map<Integer, Meal> meals = new ConcurrentHashMap<Integer, Meal>() {{
        put(count.incrementAndGet(), new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        put(count.incrementAndGet(), new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        put(count.incrementAndGet(), new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        put(count.incrementAndGet(), new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        put(count.incrementAndGet(), new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        put(count.incrementAndGet(), new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        put(count.incrementAndGet(), new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }};

    @Override
    public Meal getMealById(Integer id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void addMeal(Meal meal) {
        Integer mealId = (meal.getId() == 0) ? count.incrementAndGet() : meal.getId();
        meal.setId(mealId);
        meals.put(mealId, meal);
    }

    @Override
    public void deleteMeal(Integer id) {
        meals.remove(id);
    }
}
