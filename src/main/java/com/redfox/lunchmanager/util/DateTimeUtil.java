package com.redfox.lunchmanager.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DateTimeUtil {

    public static final String DATE_PATTERN = "dd-MMMM-yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    // DB doesn't support LocalDate.MIN/MAX
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);
    private static final LocalDate NOW = LocalDate.now();

    private DateTimeUtil() {
    }

    public static LocalDate atDayOrNow(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay().toLocalDate() : NOW;
    }

    public static LocalDate atDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay().toLocalDate() : MIN_DATE;
    }

    public static LocalDate atNextDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS): MAX_DATE;
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static @Nullable
    LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.hasLength(str) ? LocalDate.parse(str) : null;
    }

    public static String toString(LocalDate ld) {
        return ld == null ? "" : ld.format(DATE_FORMATTER);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
