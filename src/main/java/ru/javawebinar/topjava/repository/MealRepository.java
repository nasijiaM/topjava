package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, int authUserId);

    // false if meal does not belong to userId
    boolean delete(int id, int authUserId);

    // null if meal does not belong to userId
    Meal get(int mealId, int authUserId);

    // ORDERED dateTime desc
    Collection<Meal> getAll(int authUserId);

    List<MealTo> getByDateTime(String startDate, String endDate, String startTime, String endTime, int authUserId, int caloriesPerDay);
}
