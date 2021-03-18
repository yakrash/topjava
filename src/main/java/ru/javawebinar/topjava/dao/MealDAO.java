package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    Meal add(Meal meal);

    Meal getById(Long index);

    List<Meal> getAll();

    void update(Long id, Meal meal);

    void delete(Long index);
}
