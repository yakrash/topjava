package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.Profiles;

import java.util.ArrayList;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    public void findUserWithMeals() {
        User userActual = service.findUserWithMeals(USER_ID);
        USER_MATCHER.assertMatch(userActual, user);
        MEAL_MATCHER.assertMatch(userActual.getMeals(), meals);
    }

    @Test
    public void findUserWithMealsButUserNotFoundMeals() {
        User newUser = service.create(getNew());
        User findUser = service.findUserWithMeals(newUser.id());
        USER_MATCHER.assertMatch(findUser, newUser);
        MEAL_MATCHER.assertMatch(findUser.getMeals(), new ArrayList<>(0));

    }
}
