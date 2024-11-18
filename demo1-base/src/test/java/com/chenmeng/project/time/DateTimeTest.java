package com.chenmeng.project.time;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenmeng
 */
@SpringBootTest
public class DateTimeTest {

    /**
     * 导出时间格式
     */
    private static final String PATTERN_DATE_EXPORT = "yyyy年MM月dd日";
    private static final String PATTERN_DATETIME_EXPORT = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 导入时间格式（可在 DatePattern 中获取）
     */
    private static final String PATTERN_DATE_IMPORT = "yyyy-MM-dd";
    private static final String PATTERN_DATETIME_IMPORT = "yyyy-MM-dd HH:mm:ss";

    @Test
    void test1() {
        // 1、要转换的时间字符串（String）
        String timeStr = "2019-10-01 15:01:00";
        // 使用 hutool 工具类进行时间格式转换
        String formattedTime = DateUtil.format(DateUtil.parse(timeStr, DatePattern.NORM_DATETIME_FORMAT), "yyyy年MM月dd日 HH:mm");
        System.out.println("转换后的时间: " + formattedTime); // 转换后的时间: 2019年10月01日 15:01

        // 2、获取当前时间（Date）
        Date time = new Date();
        // 使用 hutool 工具类进行时间格式转换
        String formattedTime2 = DateUtil.format(time, "yyyy年MM月dd日 HH:mm");
        System.out.println("转换后的时间: " + formattedTime2); // 转换后的时间: 2024年07月23日 19:39
    }

    @Test
    void test5() {
        // 导出的格式
        String dateStr = "2024-11-05";
        DateTime date = DateUtil.parseDate(dateStr);

        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE_EXPORT);
        String format1 = sdf.format(date);
        System.out.println("format1 = " + format1); // 2024年11月05日

        String format2 = DateUtil.format(date, PATTERN_DATE_EXPORT);
        System.out.println("format2 = " + format2); // 2024年11月05日
    }

    @Test
    void test6() {
        // 导入的格式
        String dateStr = "2024年11月5日";
        DateTime date = DateUtil.parseDate(dateStr);
        System.out.println("date = " + date);

        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE_IMPORT);
        String format1 = sdf.format(date);
        System.out.println("format1 = " + format1); // 2024-11-05

        String format2 = DateUtil.format(date, PATTERN_DATE_IMPORT);
        // String format3 = DateUtil.format(date, DatePattern.NORM_DATE_PATTERN);
        System.out.println("format2 = " + format2); // 2024-11-05
    }

    @SneakyThrows
    @Test
    void test7() {
        String dateStr = "2024-11-05 11:05:11";
        DateTime date = DateUtil.parseDateTime(dateStr);

        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATETIME_EXPORT);
        String format1 = sdf.format(date);
        System.out.println("format1 = " + format1); // 2024年11月05日 11时05分11秒

        String format2 = DateUtil.format(date, PATTERN_DATETIME_EXPORT);
        System.out.println("format2 = " + format2);

        SimpleDateFormat sdf2 = new SimpleDateFormat(PATTERN_DATETIME_IMPORT);
        Date parse = sdf2.parse(dateStr);
        String format3 = DateUtil.format(parse, PATTERN_DATETIME_EXPORT);
        System.out.println("format3 = " + format3);
    }

    @Test
    void test8() {
        String dateStr1 = "2024-11-05";
        String dateStr2 = "2024-11-05 11:05:11";
        DateTime parse1 = DateUtil.parse(dateStr1);
        System.out.println("parse1 = " + parse1); // 2024-11-05 00:00:00
        String format1 = DateUtil.format(parse1, PATTERN_DATE_EXPORT);
        System.out.println("format1 = " + format1); // 2024年11月05日

        DateTime parse2 = DateUtil.parse(dateStr2);
        System.out.println("parse2 = " + parse2); // 2024-11-05 11:05:11
        String format2 = DateUtil.format(parse2, PATTERN_DATETIME_EXPORT);
        System.out.println("format2 = " + format2); // 2024年11月05日 11时05分11秒
    }
}
