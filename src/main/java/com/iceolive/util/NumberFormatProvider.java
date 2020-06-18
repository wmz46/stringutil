package com.iceolive.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;

/**
 * @author wangmianzhe
 */
public abstract class NumberFormatProvider implements IFormatProvider {
    @Override
    public String format(Object obj, String format) {
        Matcher matcher = null;
        matcher = CURRENCY_REG.matcher(format);
        if (matcher.find()) {
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            int digits = 2;
            String num = matcher.group(1);
            if (StringUtil.isNotEmpty(num)) {
                digits = Integer.parseInt(num);
            }
            nf.setMinimumFractionDigits(digits);
            nf.setMaximumFractionDigits(digits);
            return nf.format(obj);
        }
        matcher = NUMERIC_REG.matcher(format);
        if (matcher.find()) {
            NumberFormat nf = NumberFormat.getIntegerInstance();
            int digits = 2;
            String num = matcher.group(1);
            if (StringUtil.isNotEmpty(num)) {
                digits = Integer.parseInt(num);
            }
            nf.setMinimumFractionDigits(digits);
            nf.setMaximumFractionDigits(digits);
            return nf.format(obj);
        }
        matcher = PERCENT_REG.matcher(format);
        if (matcher.find()) {
            NumberFormat nf = NumberFormat.getPercentInstance();
            int digits = 2;
            String num = matcher.group(1);
            if (StringUtil.isNotEmpty(num)) {
                digits = Integer.parseInt(num);

            }
            nf.setMinimumFractionDigits(digits);
            nf.setMaximumFractionDigits(digits);
            nf.setGroupingUsed(false);
            return nf.format(obj);
        }
        matcher = FIXED_POINT_REG.matcher(format);
        if (matcher.find()) {
            BigDecimal bigDecimal = new BigDecimal(obj.toString());
            int digits = 2;
            String num = matcher.group(1);
            if (StringUtil.isNotEmpty(num)) {
                digits = Integer.parseInt(num);
            }
            return String.format("%." + digits + "f", bigDecimal);
        }
        matcher = GENERAL_REG.matcher(format);
        if (matcher.find()) {
            //todo 不支持
            BigDecimal bigDecimal = new BigDecimal(obj.toString());
            int digits = 2;
            String num = matcher.group(1);
            if (StringUtil.isNotEmpty(num)) {
                digits = Integer.parseInt(num);
            }
            return String.format("%." + digits + "f", bigDecimal);
        }
        matcher = EXPONENTIAL_REG.matcher(format);
        if (matcher.find()) {
            BigDecimal bigDecimal = new BigDecimal(obj.toString());
            int digits = 6;
            String num = matcher.group(1);
            if (StringUtil.isNotEmpty(num)) {
                digits = Integer.parseInt(num);
            }
            return String.format("%." + digits + format.substring(0, 1), bigDecimal);
        }
        matcher = CUSTOM_REG.matcher(format);
        if(matcher.find()){
            DecimalFormat df =  new DecimalFormat();
            df.applyPattern(format);
            return df.format(obj);
        }
        throw new RuntimeException(MessageFormat.format("类型[{0}]不支持格式[{1}]", obj.getClass().getName(), format));

    }
}
