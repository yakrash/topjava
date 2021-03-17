package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    Meal add(Meal meal);

    Meal getById(Long index);

    List<Meal> getAll();

    void update(Meal meal);

    void delete(Long index);
}
