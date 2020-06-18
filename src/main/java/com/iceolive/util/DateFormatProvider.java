package com.iceolive.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangmianzhe
 */
public class DateFormatProvider implements IFormatProvider {

    @Override
    public String format(Object obj, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format((Date) obj);
    }
}
