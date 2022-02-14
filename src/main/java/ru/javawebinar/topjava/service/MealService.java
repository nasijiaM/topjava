package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;

import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int authUserId) {
        return repository.save(meal, authUserId);
    }

    public void delete(int mealId, int authUserId) {
        checkNotFoundWithId(repository.delete(mealId, authUserId), mealId);
    }

    public Meal get(int mealId, int authUserId) {
        return checkNotFoundWithId(repository.get(mealId, authUserId), mealId);
    }

    public List<Meal> getAll(int authUserId) {
        return new ArrayList<>(repository.getAll(authUserId));
    }

    public List<MealTo> getByDateTime(String startDate, String endDate, String startTime, String endTime, int authUserId, int caloriesPerDay) {
        return repository.getByDateTime(startDate, endDate, startTime, endTime, authUserId, caloriesPerDay);
    }

    public void update(Meal meal, int authUserId) {
        checkNotFoundWithId(repository.save(meal, authUserId), meal.getId());
    }
}