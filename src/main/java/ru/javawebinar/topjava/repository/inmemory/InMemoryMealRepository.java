package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int authUserId) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(authUserId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        if (repository.get(meal.getId()).getUserId() == authUserId) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int mealId, int authUserId) {
        log.info("delete {}", mealId);
        return repository.get(mealId).getUserId() == authUserId && repository.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int authUserId) {
        log.info("get {}", mealId);
        return (repository.get(mealId) != null && repository.get(mealId).getUserId() == authUserId) ?
                repository.get(mealId) : null;
    }

    @Override
    public Collection<Meal> getAll(int authUserId) {
        log.info("getAll");
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == authUserId)
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
    }

    public List<MealTo> getByDateTime(String startDate, String endDate, String startTime, String endTime, int authUserId, int caloriesPerDay) {
        log.info("getByDateTime");
        return MealsUtil.filterByPredicate(getAll(authUserId),
                caloriesPerDay,
                meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDate, endDate, startTime, endTime));
    }
}

