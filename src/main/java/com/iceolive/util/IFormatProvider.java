package com.iceolive.util;

import java.util.regex.Pattern;

/**
 * @author wangmianzhe
 */
public interface IFormatProvider {
    /**
     * 货币
     */
    Pattern CURRENCY_REG = Pattern.compile("^[cC](\\d*)$");
    /**
     * 十进制
     */
     Pattern DECIMAL_REG = Pattern.compile("^[dD](\\d*)$");

    /**
     * 指数
     */
    Pattern EXPONENTIAL_REG = Pattern.compile("^[eE](\\d*)$");
    /**
     * 定点
     */
    Pattern  FIXED_POINT_REG = Pattern.compile("^[fF](\\d*)$");
    /**
     * 常规  todo 规则过于复杂，先跳过
     */
    Pattern  GENERAL_REG = Pattern.compile("^[gG](\\d*)$");
    /**
     * 数字
     */
    Pattern  NUMERIC_REG = Pattern.compile("^[nN](\\d*)$");
    /**
     * 数字
     */
    Pattern PERCENT_REG = Pattern.compile("^[pP](\\d*)$");
    /**
     * 十六进制
     */
    Pattern HEXADECIMAL_REG = Pattern.compile("^[xX](\\d*)$");

    /**
     * 自定义格式
     */
    Pattern CUSTOM_REG = Pattern.compile("[#0\\.\\,]+");
    /**
     * 格式化
     * @param obj 对象
     * @param format 格式化规则
     * @return 格式化后的字符串
     */
    String format(Object obj, String format);
}
