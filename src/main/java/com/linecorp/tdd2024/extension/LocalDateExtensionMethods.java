package com.linecorp.tdd2024.extension;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public class LocalDateExtensionMethods {

    public static LocalDate firstDayOfMonth(LocalDate obj) {
        return obj.withDayOfMonth(1);
    }

    public static LocalDate lastDayOfMonth(LocalDate obj) {
        return obj.withDayOfMonth(obj.lengthOfMonth());
    }

    public static boolean isInPeriod(LocalDate obj, LocalDate start, LocalDate end) {
        return (obj.isAfter(start) || obj.equals(start)) && (obj.isBefore(end) || obj.equals(end));
    }

    public static YearMonth toYearMonth(LocalDate obj) {
        return YearMonth.from(obj);
    }

    public static int betweenDays(LocalDate obj, LocalDate target) {
        return Long.valueOf(Math.abs(ChronoUnit.DAYS.between(obj, target)) + 1).intValue();
    }

}
