package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal getMealById(Integer id);

    List<Meal> getAllMeals();

    void addMeal(Meal meal);

    void deleteMeal(Integer id);
}
