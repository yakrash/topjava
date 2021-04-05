package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

public class MealTestData {
    public static final int MEAL_1_ID = ADMIN_ID + 1;
    public static int countId = ADMIN_ID + 1;
    public static final LocalDateTime LDT_1 = LocalDateTime.parse("2021-02-01T12:22:00");
    public static final LocalDateTime LDT_2 = LocalDateTime.parse("2021-04-01T12:25:00");
    public static final LocalDate LD_SAME = LocalDate.parse("2020-01-30");
    public static final Meal MEAL_1 = new Meal(countId++, LDT_1, "Lanch", 800);
    public static final Meal MEAL_2 = new Meal(countId++, LDT_2, "Breakfast", 500);
    public static final Meal MEAL_3 = new Meal(countId++, LDT_2, "Breakfast2", 1000);

    public static final List<Meal> mealsUser = Arrays.asList(MEAL_3,
            new Meal(countId++, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
            new Meal(countId++, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(countId++, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(countId++, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(countId++, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(countId++, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(countId++, LocalDateTime.of(2020, Month.JANUARY, 30, 00, 0), "Завтрак", 500)
    );

    public static final List<Meal> mealsUserSameDate = mealsUser.stream()
            .filter(meal -> meal.getDate().compareTo(LD_SAME) == 0)
            .collect(Collectors.toList());

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
