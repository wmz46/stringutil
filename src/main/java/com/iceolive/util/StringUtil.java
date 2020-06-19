package com.iceolive.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static <T> Boolean parseBoolean(String str, Class<T> clazz) {
        return parseBoolean(str, "true", "false", clazz);
    }


    public static <T> Boolean parseBoolean(String str, String trueString, String falseString, Class<T> clazz) {
        Boolean val = null;
        if (trueString.equals(str)) {
            val = true;
        } else if (falseString.equals(str)) {
            val = false;
        }
        if (val == null && clazz.isAssignableFrom(boolean.class)) {
            val = false;
        }
        return val;
    }

    public static <T> T parse(String str, Class<T> clazz) {
        return parse(str,null,clazz);
    }
    public static <T> T tryParse(String str,  T defaultVal, Class<T> clazz) {
        return  tryParse(str,null,clazz);
    }
    /**
     * 反格式化，出错会抛运行时异常，请自行捕获
     *
     * @param str    字符串
     * @param format 格式化规则
     * @param clazz  类型
     * @param <T>    类型
     * @return 反格式化对象
     */
    public static <T> T parse(String str, String format, Class<T> clazz) {
        String typeName = clazz.getName();
        Object val = null;
        if(clazz.isPrimitive()){
            val = 0;
        }
        if ("byte".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Byte.parseByte(str);
            }
        } else if ("java.lang.Byte".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Byte.parseByte(str);
            }
        } else if ("short".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Short.parseShort(str);
            }
        } else if ("java.lang.Short".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Short.parseShort(str);
            }
        } else if ("int".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Integer.parseInt(str);
            }
        } else if ("java.lang.Integer".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Integer.parseInt(str);
            }
        } else if ("long".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Long.parseLong(str);
            }
        } else if ("java.lang.Long".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Long.parseLong(str);
            }
        } else if ("float".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Float.parseFloat(str);
            }
        } else if ("java.lang.Float".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Float.parseFloat(str);
            }
        } else if ("double".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Double.parseDouble(str);
            }
        } else if ("java.lang.Double".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = Double.parseDouble(str);
            }
        } else if ("java.math.BigDecimal".equals(typeName)) {
            if (!StringUtil.isEmpty(str)) {
                val = new BigDecimal(str);
            }
        } else if ("java.util.Date".equals(typeName)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                return (T) simpleDateFormat.parse(str);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else if ("java.time.LocalDateTime".equals(typeName)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return (T) LocalDate.parse(str, dateTimeFormatter);
        } else if ("java.time.LocalDate".equals(typeName)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return (T) LocalDateTime.parse(str, dateTimeFormatter);
        } else {
            val = str;
        }
        return (T) val;
    }

    /**
     * 反格式化，出错不抛异常
     *
     * @param str        字符串
     * @param format     格式化规则
     * @param defaultVal 出错默认值
     * @param clazz      类型
     * @param <T>        类型
     * @return 反格式化对象
     */
    public static <T> T tryParse(String str, String format, T defaultVal, Class<T> clazz) {
        try {
            return parse(str, format, clazz);
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static void main(String[] args) {
        String format = null;
        Object obj = -123456789.1234;
        System.out.println(obj);
        format = "c2";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "E2";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "f0";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "g0";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "n2";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "p2";
        System.out.println(format + ":" + StringUtil.format(obj, format));
        format = "duo#,##0.00";
        System.out.println(format + ":  " + StringUtil.format(obj, format));

        obj = -15;
        System.out.println(obj);
        //以下两种只支持整型
        format = "d2";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "X2";
        System.out.println(format + ":  " + StringUtil.format(obj, format));

        format = "c2";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "e2";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "f0";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "g0";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "n2";
        System.out.println(format + ":  " + StringUtil.format(obj, format));
        format = "p0";
        System.out.println(format + ":  " + StringUtil.format(obj, format));

        format = "##0.00";
        System.out.println(format + ":  " + StringUtil.format(obj, format));

        System.out.println(StringUtil.parseBoolean("true",  Boolean.class));
        System.out.println(StringUtil.parse("2",null,short.class));
    }
}
