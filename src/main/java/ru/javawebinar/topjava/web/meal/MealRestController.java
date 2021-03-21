package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    private final MealService service;

    @Autowired
    MealRestController(MealService mealService) {
        this.service = mealService;
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        return service.get(id, authUserId());
    }

    public Collection<Meal> getAll() {
        return service.getAll(authUserId());
    }

    public List<MealTo> getAllMealTo() {
        return MealsUtil.getTos(getAll(), authUserCaloriesPerDay());
    }

    public void update(Meal meal) {
        service.update(meal, authUserId());
    }
}