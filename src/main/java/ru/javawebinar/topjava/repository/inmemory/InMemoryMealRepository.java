package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = getLogger(InMemoryMealRepository.class);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1, meal.getId()));
    }

    @Override
    public Meal save(Meal meal, int userId, Integer id) {
        if (meal.isNew()) {
            int idTemp = counter.incrementAndGet();
            meal.setId(idTemp);
            meal.setUserId(userId);
            repository.put(idTemp, meal);
            log.info("create {}", meal);
            return meal;
        } else {
            synchronized (this) {
                if (repository.get(id).getUserId() == userId) {
                    meal.setUserId(userId);
                    log.info("update {}", meal);
                    return repository.computeIfPresent(id, (ident, oldMeal) -> meal);
                }
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal mealToDel = repository.get(id);
        if (mealToDel != null && mealToDel.getUserId() == userId) {
            log.info("delete {}", id);
            Meal meal = repository.remove(id);
            return meal != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal.getUserId() == userId) {
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
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

