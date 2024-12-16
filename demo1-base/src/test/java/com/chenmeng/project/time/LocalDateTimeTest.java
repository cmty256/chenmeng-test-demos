package com.chenmeng.project.time;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenmeng
 */
@SpringBootTest
public class LocalDateTimeTest {

    @Test
    void test1() {
        // 获取当前时间
        LocalDate now = LocalDate.now();
        System.out.println("now = " + now);

        // 只获取年份
        int year = now.getYear();
        System.out.println("now.getYear() = " + now.getYear());

        // 格式化
        String startYearStr = String.format("%d-01-01", year);
        // String endYearStr = String.format("%d-08-31", year);
        // System.out.println("startYearStr = " + startYearStr);
        // System.out.println("endYearStr = " + endYearStr);

        // 减三年
        LocalDate minusYears = LocalDate.parse(startYearStr).minusYears(3);
        System.out.println("minusYears = " + minusYears);
    }

    /**
     * 获取指定日期范围内的所有日期（包含结束日期）
     */
    @Test
    void test2() {
        String startDate = "2024-12-16";
        String endDate = "2025-01-25";
        List<String> dates = getDatesInRange(startDate, endDate);
        System.out.println("dates.size() = " + dates.size());
        for (int i = 0; i < dates.size(); i++) {
            String date = dates.get(i);
            System.out.println("date" + i + " = " + date);
        }
    }

    /**
     * 获取指定日期范围内的所有日期（包含结束日期）。
     *
     * @param startDate 起始日期，格式为 "yyyy-MM-dd"
     * @param endDate   结束日期，格式为 "yyyy-MM-dd"
     * @return 包含指定日期范围内所有日期的列表
     */
    private List<String> getDatesInRange(String startDate, String endDate) {
        // 使用指定日期格式解析起始日期和结束日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        // 存储日期的列表
        List<String> dateList = new ArrayList<>();

        // 使用循环生成日期范围，并将每个日期格式化为字符串，添加到列表中
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            dateList.add(date.format(formatter));
        }

        return dateList;
    }
}
