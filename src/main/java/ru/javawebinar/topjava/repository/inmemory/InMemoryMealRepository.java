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
        MealsUtil.meals.forEach(meal -> save(meal, 1, meal.getId()));
    }

    @Override
    public Meal save(Meal meal, int userId, Integer id) {
        if (meal.isNew()) {
            log.info("create {}", meal);
            int idTemp = counter.incrementAndGet();
            meal.setId(idTemp);
            meal.setUserId(userId);
            Map<Integer, Meal> mealMap = repository.get(userId) == null ? new HashMap<>() : repository.get(userId);
            mealMap.put(idTemp, meal);
            repository.put(userId, mealMap);
            return meal;
        } else {
            synchronized (this) {
                log.info("update {}", meal);
                Meal mealInRep = repository.get(userId).get(id);
                if (mealInRep != null) {
                    repository.computeIfPresent(userId, (key, mealMap) -> {
                        mealMap.put(id, meal);
                        return mealMap;
                    });
                    return meal;
                }
            }
        }
        return null;
    }

    @Override
    public synchronized boolean delete(int id, int userId) {
        Meal mealToDel = repository.get(userId).get(id);
        if (mealToDel != null) {
            log.info("delete {}", id);
            Meal meal = repository.get(userId).remove(id);
            return meal != null;
        }
        return false;
    }

    @Override
    public synchronized Meal get(int id, int userId) {
        Meal meal = repository.get(userId).get(id);
        if (meal != null) {
            log.info("get id: {}", id);
            return meal;
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
        if (repository.get(userId) == null) {
            return Collections.emptyList();
        }
        return repository.get(userId).values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

