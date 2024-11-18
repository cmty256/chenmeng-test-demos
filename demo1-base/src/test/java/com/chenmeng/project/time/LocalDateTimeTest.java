package com.chenmeng.project.time;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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
}
