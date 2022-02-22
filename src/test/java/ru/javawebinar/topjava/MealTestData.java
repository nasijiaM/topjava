package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 3;
    public static final int MEAL2_ID = START_SEQ + 4;
    public static final int MEAL3_ID = START_SEQ + 5;
    public static final int MEAL4_ID = START_SEQ + 6;
    public static final int MEAL5_ID = START_SEQ + 7;
    public static final int MEAL6_ID = START_SEQ + 8;
    public static final int MEAL7_ID = START_SEQ + 9;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final LocalDate LOCAL_DATE = LocalDate.of(2020, Month.JANUARY, 30);
    public static final int NOT_FOUND = 10;

    public static final Meal meal1 = new Meal(MEAL1_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL2_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL3_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL4_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(MEAL5_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);
    public static final Meal meal6 = new Meal(MEAL6_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000);
    public static final Meal meal7 = new Meal(MEAL7_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "New", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.now());
        updated.setDescription("Updated");
        updated.setCalories(100);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
