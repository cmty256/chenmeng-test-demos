package com.chenmeng.project;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @author chenmeng
 */
@SpringBootTest
public class DateTimeTest {

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
}
