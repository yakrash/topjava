package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealInMemoryDAO implements MealDAO {
    private final AtomicLong id = new AtomicLong(0);
    private final Map<Long, Meal> mealMap = new ConcurrentHashMap<>();

    public MealInMemoryDAO() {
        List<Meal> mealList = new MealByDefault().getAll();
        for (Meal meal : mealList) {
            add(meal);
        }
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(id.incrementAndGet());
        return mealMap.put(id.longValue(), meal);
    }

    @Override
    public Meal getById(Long index) {
        return mealMap.get(index);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public void update(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(Long index) {
        mealMap.remove(index);
    }
}
