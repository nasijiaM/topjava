package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal getById(int id);

    List<Meal> getAll();

    Meal save(Meal meal);

    void delete(int id);
}
