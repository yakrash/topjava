package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_1_ID, ADMIN_ID);
        assertMatch(meal, MEAL_1);
    }

    @Test
    public void getNotHaveMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_1_ID, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_1_ID, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_1_ID, ADMIN_ID));
    }

    @Test
    public void deleteAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_1_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(LDT_1.toLocalDate(), LDT_2.toLocalDate(), ADMIN_ID), MEAL_2, MEAL_1);
        assertMatch(service.getBetweenInclusive(null, LDT_1.toLocalDate(), ADMIN_ID), MEAL_1);
        assertMatch(service.getBetweenInclusive(LDT_2.toLocalDate(), null, ADMIN_ID), MEAL_2);
        assertMatch(service.getBetweenInclusive(null, null, USER_ID), MEAL_3);
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(ADMIN_ID);
        assertMatch(mealList, MEAL_2, MEAL_1);
        List<Meal> mealList2 = service.getAll(USER_ID);
        assertMatch(mealList2, MEAL_3);
    }

    @Test
    public void update() {
        Meal meal = getUpdatedMeal();
        service.update(meal, ADMIN_ID);
        assertMatch(service.get(MEAL_1_ID, ADMIN_ID), meal);
    }

    @Test
    public void updateCheckException() {
        Meal meal = getUpdatedMeal();
        assertThrows(NotFoundException.class, () -> service.update(meal, USER_ID));
        assertThrows(DuplicateKeyException.class, () -> service.update(newDuplicateMeal(), ADMIN_ID));

    }

    @Test
    public void create() {
        Meal meal = newMeal();
        Meal created = service.create(meal, ADMIN_ID);
        int id = created.getId();
        meal.setId(id);
        assertMatch(created, meal);
        assertMatch(service.get(id, ADMIN_ID), meal);
    }

    @Test
    public void checkExceptionByCreateDuplicate() {
        assertThrows(DuplicateKeyException.class, () -> service.create(newDuplicateMeal(), ADMIN_ID));
    }
}