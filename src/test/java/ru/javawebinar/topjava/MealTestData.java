package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

public class MealTestData {
    public static final int MEAL_1_ID = ADMIN_ID + 1;
    public static final int MEAL_2_ID = ADMIN_ID + 2;
    public static final int MEAL_3_ID = ADMIN_ID + 3;
    public static final LocalDateTime LDT_1 = LocalDateTime.parse("2021-02-01T12:22:00");
    public static final LocalDateTime LDT_2 = LocalDateTime.parse("2021-04-01T12:25:00");
    public static final Meal MEAL_1 = new Meal(MEAL_1_ID, LDT_1, "Lanch", 800);
    public static final Meal MEAL_2 = new Meal(MEAL_2_ID, LDT_2, "Breakfast", 500);
    public static final Meal MEAL_3 = new Meal(MEAL_3_ID, LDT_2, "Breakfast2", 1000);

    public static Meal newMeal() {
        return new Meal(LocalDateTime.parse("2021-01-01T12:22:00"), "Dinner", 500);
    }

    public static Meal newDuplicateMeal() {
        return new Meal(LDT_1, "Dinner", 500);
    }

    public static Meal getUpdatedMeal() {
        Meal updatedMeal = new Meal(MEAL_1);
        updatedMeal.setCalories(1500);
        updatedMeal.setDescription("Dinner");
        updatedMeal.setDateTime(LocalDateTime.parse("2021-03-01T12:22:00"));
        return updatedMeal;
    }

    public static void assertMatch(Meal mealActual, Meal mealExpected) {
        assertThat(mealActual).usingRecursiveComparison().isEqualTo(mealExpected);
    }

    public static void assertMatch(Iterable<Meal> mealActual, Meal... mealExpected) {
        assertMatch(mealActual, Arrays.asList(mealExpected));
    }

    public static void assertMatch(Iterable<Meal> mealActual, Iterable<Meal> mealExpected) {
        assertThat(mealActual).usingRecursiveComparison().isEqualTo(mealExpected);
    }
}
