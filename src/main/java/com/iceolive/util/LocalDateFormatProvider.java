package com.iceolive.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author wangmianzhe
 */
public class LocalDateFormatProvider implements IFormatProvider {
    @Override
    public String format(Object obj, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format((LocalDate) obj);
    }
}
