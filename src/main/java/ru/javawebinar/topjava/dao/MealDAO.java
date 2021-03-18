package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    Meal add(Meal meal);

    Meal getById(long index);

    List<Meal> getAll();

    void update(long id, Meal meal);

    void delete(long index);
}
