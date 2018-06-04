package ru.s32xlevel.util;

import java.time.LocalDate;

public class DateTimeUtil {
    // DataBase doesn't support LocalDate.MIN/MAX
    public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private DateTimeUtil() {
    }

    public static LocalDate nullToMax(LocalDate date) {
        return date == null || date.isAfter(MAX_DATE) ? MAX_DATE : date;
    }

    public static LocalDate nullToMin(LocalDate date) {
        return date == null || date.isBefore(MIN_DATE) ? MIN_DATE : date;
    }

    public static LocalDate nullToNow(LocalDate date) {
        return date == null ? LocalDate.now() : date;
    }

    public static <T extends Comparable<? super T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }
}
