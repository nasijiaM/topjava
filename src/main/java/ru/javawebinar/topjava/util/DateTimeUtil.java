package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static boolean isBetweenHalfOpen(LocalDateTime localDateTime, String fromDate, String toDate, String fromTime, String toTime) {
        LocalDate startDate = (!fromDate.isEmpty()) ? LocalDate.parse(fromDate) : LocalDate.MIN;
        LocalDate endDate = (!toDate.isEmpty()) ? LocalDate.parse(toDate) : LocalDate.MAX;
        LocalTime startTime = (!fromTime.isEmpty()) ? LocalTime.parse(fromTime) : LocalTime.MIN;
        LocalTime endTime = (!toTime.isEmpty()) ? LocalTime.parse(toTime) : LocalTime.MAX;

        return  localDateTime.toLocalDate().compareTo(startDate) >= 0 &&
                localDateTime.toLocalDate().compareTo(endDate) <= 0 &&
                isBetweenHalfOpen(localDateTime.toLocalTime(), startTime, endTime);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

