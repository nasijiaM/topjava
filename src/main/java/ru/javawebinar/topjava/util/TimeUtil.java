package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        if (startTime == null && endTime == null) return true;
        else if (startTime == null) startTime = LocalTime.MIN;
        else if (endTime == null) endTime = LocalTime.MAX;

        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }
}
