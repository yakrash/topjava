package ru.javawebinar.topjava.dao;

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
        List<Meal> mealList = MealTestData.getAll();
        for (Meal meal : mealList) {
            add(meal);
        }
    }

    @Override
    public Meal add(Meal meal) {
        long i = id.incrementAndGet();
        meal.setId(i);
        return mealMap.put(i, meal);
    }

    @Override
    public Meal getById(long index) {
        return mealMap.get(index);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public void update(long id, Meal meal) {
        mealMap.put(id, meal);
    }

    @Override
    public void delete(long index) {
        mealMap.remove(index);
    }
}
