package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    MealRestController(MealService mealService) {
        this.service = mealService;
    }

    public Meal create(Meal meal) {
        log.info("create in MealRestController");
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete in MealRestController");
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        log.info("get in MealRestController");
        return service.get(id, authUserId());
    }

    public Collection<Meal> getAll() {
        log.info("getAll in MealRestController");
        return service.getAll(authUserId());
    }

    public List<MealTo> getAllMealTo() {
        log.info("getAllMealTo() in MealRestController");
        return MealsUtil.getTos(getAll(), authUserCaloriesPerDay());
    }

    public Collection<MealTo> getAllMealTo(LocalDate startDate, LocalTime startTime,
                                           LocalDate endDate, LocalTime endTime) {
        log.info("getAllMealTo(Date .. Time) in MealRestController");

        startDate = (startDate == null) ? LocalDate.MIN : startDate;
        startTime = (startTime == null) ? LocalTime.MIN : startTime;
        endDate = (endDate == null) ? LocalDate.MAX : endDate;
        endTime = (endTime == null) ? LocalTime.MAX : endTime;
        return MealsUtil.getFilteredTos(service.getAll(authUserId(), startDate, endDate),
                                        authUserCaloriesPerDay(), startTime, endTime);
    }

    public void update(Meal meal) {
        log.info("update(Meal meal) in MealRestController");
        service.update(meal, authUserId());
    }
}