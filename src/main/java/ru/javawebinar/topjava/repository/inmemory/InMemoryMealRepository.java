package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = getLogger(InMemoryMealRepository.class);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            log.info("create {}", meal);
            int idTemp = counter.incrementAndGet();
            meal.setId(idTemp);
            repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(idTemp, meal);
            return meal;
        } else {
            log.info("update {}", meal);
            Map<Integer, Meal> mealMap = repository.get(userId);
            int id = meal.getId();
            if (mealMap != null) {
                return mealMap.computeIfPresent(id, (key, oldMeal) -> meal);
            }
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (mealMap != null && mealMap.get(id) != null) {
            return mealMap.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (mealMap != null && mealMap.get(id) != null) {
            return mealMap.get(id);
        } else return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll");
        return filteredByPredicate(userId, meal -> true);
    }

    public Collection<Meal> getAllWithDateTime(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAllWithDateTime");
        return filteredByPredicate(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate));
    }

    private Collection<Meal> filteredByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (mealMap == null) {
            return Collections.emptyList();
        }
        return mealMap.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

