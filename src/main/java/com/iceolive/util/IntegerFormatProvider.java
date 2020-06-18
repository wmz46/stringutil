package com.iceolive.util;

import java.text.NumberFormat;
import java.util.regex.Matcher;

/**
 * @author wangmianzhe
 */
public class IntegerFormatProvider  extends NumberFormatProvider {
    @Override
    public String format(Object obj, String format) {
        Matcher matcher = null;
        matcher = DECIMAL_REG.matcher(format);
        if (matcher.find()) {
            NumberFormat nf = NumberFormat.getIntegerInstance();
            String num = matcher.group(1);
            if (StringUtil.isNotEmpty(num)) {
                nf.setMinimumIntegerDigits(Integer.parseInt(num));
            }
            nf.setGroupingUsed(false);
            return nf.format(obj);
        }
        matcher = HEXADECIMAL_REG.matcher(format);
        if (matcher.find()) {
            int digits = 0;
            String num = matcher.group(1);
            if (StringUtil.isNotEmpty(num)) {
                digits = Integer.parseInt(num);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%x", obj));
            while (sb.length() < digits) {
                sb.insert(0, 0);
            }
            if (format.startsWith("X")) {
                return sb.toString().toUpperCase();
            } else {
                return sb.toString();
            }
        }
        return  super.format(obj,format);
    }


}
