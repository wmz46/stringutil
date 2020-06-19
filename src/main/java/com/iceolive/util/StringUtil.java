package com.iceolive.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

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
        if(clazz.isAssignableFrom(boolean.class) || clazz.isAssignableFrom(Boolean.class)){
            return (T)parseBoolean(str,clazz);
        }
        return parse(str,null,clazz);
    }
    public static <T> T tryParse(String str,  T defaultVal, Class<T> clazz) {
        try{
            return parse(str,clazz);
        }catch (Exception e){
            return defaultVal;
        }
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
        Object val = null;
        if(clazz.isPrimitive()){
            val = 0;
        }
        if (clazz.isAssignableFrom(byte.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Byte.parseByte(str);
            }
        } else if (clazz.isAssignableFrom(Byte.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Byte.parseByte(str);
            }
        } else if (clazz.isAssignableFrom(short.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Short.parseShort(str);
            }
        } else if (clazz.isAssignableFrom( Short.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Short.parseShort(str);
            }
        } else if (clazz.isAssignableFrom(int.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Integer.parseInt(str);
            }
        } else if (clazz.isAssignableFrom(Integer.class) ) {
            if (!StringUtil.isEmpty(str)) {
                val = Integer.parseInt(str);
            }
        } else if (clazz.isAssignableFrom(long.class )) {
            if (!StringUtil.isEmpty(str)) {
                val = Long.parseLong(str);
            }
        } else if (clazz.isAssignableFrom(Long.class )) {
            if (!StringUtil.isEmpty(str)) {
                val = Long.parseLong(str);
            }
        } else if (clazz.isAssignableFrom(float.class )) {
            if (!StringUtil.isEmpty(str)) {
                val = Float.parseFloat(str);
            }
        } else if (clazz.isAssignableFrom(Float.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Float.parseFloat(str);
            }
        } else if (clazz.isAssignableFrom(double.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Double.parseDouble(str);
            }
        } else if (clazz.isAssignableFrom(Double.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Double.parseDouble(str);
            }
        } else if (clazz.isAssignableFrom(BigDecimal.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = new BigDecimal(str);
            }
        } else if (clazz.isAssignableFrom(Date.class)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                return (T) simpleDateFormat.parse(str);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else if (clazz.isAssignableFrom(LocalDate.class)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return (T) LocalDate.parse(str, dateTimeFormatter);
        } else if (clazz.isAssignableFrom(LocalDateTime.class)) {
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


        System.out.println(StringUtil.parseBoolean("true",  Boolean.class));
        System.out.println(StringUtil.parse("1234","N0",int.class));
    }
}
