package com.iceolive.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
     * 判断两个字符串是否相同
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 是否相同
     */
    public static boolean equals(String str1, String str2) {
        if (null == str1) {
            return str2 == null;
        } else if (null == str2) {
            return false;
        } else {
            return str1.equals(str2);
        }
    }

    /**
     * 获取第一个匹配字符串
     * @param str 源字符串
     * @param reg 正则
     * @param group 分组序号
     * @return 返回第一个匹配字符串
     */
    public static String matchOne(String str, String reg, int group) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(group);
        } else {
            return null;
        }
    }

    /**
     * 获取所有匹配字符串
     * @param str 源字符串
     * @param reg 正则
     * @param group 分组序号
     * @return 返回所有匹配字符串
     */
    public static List<String> matchAll(String str, String reg, int group) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            list.add(matcher.group(group));
        }
        return list;
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
        } else if ("java.time.LocalTime".equals(typeName)) {
            return new LocalTimeFormatProvider();
        }  else if ("java.sql.Time".equals(typeName)) {
            return new TimeFormatProvider();
        }else {
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
        if (clazz.isAssignableFrom(boolean.class) || clazz.isAssignableFrom(Boolean.class)) {
            return (T) parseBoolean(str, clazz);
        }
        return parse(str, null, clazz);
    }

    public static <T> T tryParse(String str, T defaultVal, Class<T> clazz) {
        try {
            return parse(str, clazz);
        } catch (Exception e) {
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
        if (clazz.isPrimitive()) {
            val = 0;
        }
        if (Arrays.asList("int", "java.lang.Integer", "byte", "java.lang.Byte", "long", "java.lang.Long", "short", "java.lang.Short", "float", "java.lang.Float", "double", "java.lang.Double", "java.math.BigDecimal").contains(clazz.getName())) {
            //数值类型，需要处理特殊符号
            str = unFormat(str, format);
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
        } else if (clazz.isAssignableFrom(Short.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Short.parseShort(str);
            }
        } else if (clazz.isAssignableFrom(int.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Integer.parseInt(str);
            }
        } else if (clazz.isAssignableFrom(Integer.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Integer.parseInt(str);
            }
        } else if (clazz.isAssignableFrom(long.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Long.parseLong(str);
            }
        } else if (clazz.isAssignableFrom(Long.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = Long.parseLong(str);
            }
        } else if (clazz.isAssignableFrom(float.class)) {
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
        } else if(clazz.isAssignableFrom(LocalTime.class)){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return (T) LocalTime.parse(str,dateTimeFormatter);
        }else if(clazz.isAssignableFrom(Time.class)){
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                Date date = simpleDateFormat.parse(str);
                return (T)new Time(date.getTime());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }else if (clazz.isAssignableFrom(char.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = str.charAt(0);
            }
        }else if (clazz.isAssignableFrom(Character.class)) {
            if (!StringUtil.isEmpty(str)) {
                val = str.charAt(0);
            }
        } else {
            val = str;
        }
        return (T) val;
    }

    //取出特殊符号
    private static String unFormat(String str, String format) {
        Matcher matcher = null;
        if (isEmpty(format)) {
            return str;
        }
        matcher = IFormatProvider.CURRENCY_REG.matcher(format);
        if (matcher.find()) {
            //货币
            //去掉非数字符号
            String num = IFormatProvider.NOT_NUMBER_CHAR.matcher(str).replaceAll("");
            return num;
        }
        matcher = IFormatProvider.PERCENT_REG.matcher(format);
        if (matcher.find()) {
            //去掉非数字符号
            String num = IFormatProvider.NOT_NUMBER_CHAR.matcher(str).replaceAll("");
            //百分号 *100
            BigDecimal bigDecimal = new BigDecimal(num);
            return bigDecimal.divide(new BigDecimal("100")).toString();
        }
        matcher = IFormatProvider.NUMERIC_REG.matcher(format);
        if (matcher.find()) {
            //千位符
            //去掉非数字符号
            String num = IFormatProvider.NOT_NUMBER_CHAR.matcher(str).replaceAll("");
            return num;
        }
        matcher = IFormatProvider.HEXADECIMAL_REG.matcher(format);
        if (matcher.find()) {
            //十六进制
            return Integer.valueOf(str, 16).toString();
        }
        matcher = IFormatProvider.CUSTOM_REG.matcher(format);
        if (matcher.find()) {
            //判断百分号，千分号，指数符号
            if (format.contains("%") && format.contains("%")) {
                //去掉非数字符号
                String num = IFormatProvider.NOT_NUMBER_CHAR.matcher(str).replaceAll("");
                //百分号
                BigDecimal bigDecimal = new BigDecimal(num);
                return bigDecimal.divide(new BigDecimal("100")).toString();
            } else if (format.contains("‰") && str.contains("‰")) {
                //去掉非数字符号
                String num = IFormatProvider.NOT_NUMBER_CHAR.matcher(str).replaceAll("");
                //百分号
                BigDecimal bigDecimal = new BigDecimal(num);
                return bigDecimal.divide(new BigDecimal("1000")).toString();
            } else {
                //去掉非数字符号
                String num = IFormatProvider.NOT_NUMBER_CHAR.matcher(str).replaceAll("");
                return num;
            }
        }
        return str;

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
}
