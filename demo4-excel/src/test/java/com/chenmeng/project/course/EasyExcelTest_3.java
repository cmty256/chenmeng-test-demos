package com.chenmeng.project.course;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.DateUtils;
import com.chenmeng.project.course.model.OrderInfoModel;
import com.chenmeng.project.course.model.UserInfoModel3;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@SuppressWarnings("all")
public class EasyExcelTest_3 {

    /**
     * 导出路径。
     */
    private final String EXPORT_PATH = "src/main/resources/excel/export.xlsx";

    /**
     * 演示：List<List<Object>> 方式导出。
     */
    @Test
    @SneakyThrows
    public void test1() {
        List<Object> list1 = Arrays.asList("郭德纲", 1, DateUtils.parseDate("1973-01-18"));
        List<Object> list2 = Arrays.asList("于谦", 2, DateUtils.parseDate("1967-12-06"));

        List<List<Object>> list = Arrays.asList(list1, list2);

        EasyExcel.write(EXPORT_PATH)
                .sheet("导出数据")
                .doWrite(list);
    }

    /**
     * 演示：List<Map<Integer, Object>> 方式导出。
     */
    @Test
    @SneakyThrows
    public void test2() {
        Map<Integer, Object> map1 = new HashMap<>();
        map1.put(0, "郭德纲");
        map1.put(1, 1);
        map1.put(2, DateUtils.parseDate("1973-01-18"));

        Map<Integer, Object> map2 = new HashMap<>();
        map2.put(0, "于谦");
        map2.put(1, 2);
        map2.put(2, DateUtils.parseDate("1967-12-06"));

        List<Map<Integer, Object>> list = Arrays.asList(map1, map2);
        EasyExcel.write(EXPORT_PATH)
                .sheet("导出数据")
                .doWrite(list);
    }

    /**
     * 演示：List<Object> 方式导出。
     */
    @Test
    @SneakyThrows
    public void test3() {
        UserInfoModel3 user1 = new UserInfoModel3();
        user1.setUserName("郭德纲");
        user1.setUserGender(1);
        user1.setUserBirth(DateUtils.parseDate("1973-01-18"));

        OrderInfoModel order = new OrderInfoModel();
        order.setOrderTitle("订单标题");
        order.setOrderPrice(BigDecimal.valueOf(100.00));
        order.setOrderTime(new Date());

        UserInfoModel3 user2 = new UserInfoModel3();
        user2.setUserName("于谦");
        user2.setUserGender(2);
        user2.setUserBirth(DateUtils.parseDate("1967-12-06"));

        // 只会导出与第一个元素的类型相同的元素
        List<Object> list = Arrays.asList(user1, order, user2);
        // List<Object> list = Arrays.asList(order,user1, user2);
        EasyExcel.write(EXPORT_PATH)
                .sheet("导出数据")
                .doWrite(list);
    }
}
