package com.iceolive.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wangmianzhe
 */
public class LocalTimeFormatProvider implements IFormatProvider {
    @Override
    public String format(Object obj, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format((LocalTime) obj);
    }
}
