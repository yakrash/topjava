package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId, Integer id) {
        return repository.save(meal, userId, id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Collection<Meal> getAllWithDateTime(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllWithDateTime(userId, startDate, endDate);
    }

    public void update(Meal meal, int userId, Integer id) {
        checkNotFoundWithId(repository.save(meal, userId, id), id);
    }
}