package com.iceolive.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author wangmianzhe
 */
public class LocalDateTimeFormatProvider implements IFormatProvider {
    @Override
    public String format(Object obj, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return dateTimeFormatter.format((LocalDateTime)obj);
    }
}
