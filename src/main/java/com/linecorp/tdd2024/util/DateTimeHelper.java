package com.linecorp.tdd2024.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {

    public static DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    public static LocalDate parseFromYearMonth(String yearMonth) {
        return parseFromYearMonth(YearMonth.parse(yearMonth, YEAR_MONTH_FORMATTER));
    }

    public static LocalDate parseFromYearMonth(YearMonth yearMonth) {
        return yearMonth.atDay(1);
    }

}
