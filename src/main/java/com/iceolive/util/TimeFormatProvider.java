package com.iceolive.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangmianzhe
 */
public class TimeFormatProvider implements IFormatProvider {

    @Override
    public String format(Object obj, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format((Time) obj);
    }
}
