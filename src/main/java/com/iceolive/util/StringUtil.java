package com.iceolive.util;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 字符串工具类
 *
 * @author wangmianzhe
 */
public class StringUtil {
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        return isEmpty(str.trim());
    }

    /**
     * 格式化
     *
     * @param obj    对象
     * @param format 格式化规则
     * @return 格式化后的字符串
     */
    public static String format(Object obj, String format) {
        if (obj == null) {
            return null;
        } else {
            IFormatProvider formatProvider = getFormatProvider(obj);
            if (formatProvider == null) {
                return obj.toString();
            } else {
                return formatProvider.format(obj, format);
            }
        }
    }

    private static IFormatProvider getFormatProvider(Object obj) {
        if (obj == null) {
            return null;
        }
        String typeName = obj.getClass().getName();
        if (Arrays.asList("int", "java.lang.Integer", "byte", "java.lang.Byte", "long", "java.lang.Long", "short", "java.lang.Short").contains(typeName)) {
            return new IntegerFormatProvider();
        } else if (Arrays.asList("float", "java.lang.Float", "double", "java.lang.Double", "java.math.BigDecimal").contains(typeName)) {
            return new FloatFormatProvider();
        } else if ("java.util.Date".equals(typeName)) {
            return new DateFormatProvider();
        } else if ("java.time.LocalDate".equals(typeName)) {
            return new LocalDateFormatProvider();
        } else if ("java.time.LocalDateTime".equals(typeName)) {
            return new LocalDateTimeFormatProvider();
        } else {
            return null;
        }

    }

    public static void main(String[] args) {
        String format = null;
        Object obj = -123456789.1234;
        System.out.println(obj);
        format = "c2";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "E2";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "f0";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "g0";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "n2";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "p2";
        System.out.println(format+":"+StringUtil.format(obj,format));
        format = "duo#,##0.00";
        System.out.println(format+":  "+StringUtil.format(obj,format));

        obj = -15;
        System.out.println(obj);
        //以下两种只支持整型
        format = "d2";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "X2";
        System.out.println(format+":  "+StringUtil.format(obj,format));

        format = "c2";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "e2";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "f0";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "g0";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "n2";
        System.out.println(format+":  "+StringUtil.format(obj,format));
        format = "p0";
        System.out.println(format+":  "+StringUtil.format(obj,format));

        format = "##0.00";
        System.out.println(format+":  "+StringUtil.format(obj,format));

    }
}
