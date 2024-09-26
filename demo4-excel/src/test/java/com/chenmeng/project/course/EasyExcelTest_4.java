package com.chenmeng.project.course;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.chenmeng.project.course.model.UserInfoModel4;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@SuppressWarnings("all")
public class EasyExcelTest_4 {

    /**
     * 导出路径。
     */
    private final String EXPORT_PATH = "src/main/resources/excel/export2.xlsx";

    /**
     * 测试：自定义导出字段、自动列宽配置
     */
    @Test
    @SneakyThrows
    public void testExport() {
        // 获取：用户信息列表。
        List<UserInfoModel4> list = this.getList();
        // 处理：导出数据。
        EasyExcel.write(EXPORT_PATH)
                .sheet("导出数据")
                .head(UserInfoModel4.class)
                // 配置：导出字段。
                .includeColumnFieldNames(Arrays.asList("userName", "userBirth",
                        "userScore", "userReward"))
                // 配置：自动列宽。
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(list);
    }

    /**
     * 获取用户信息列表。
     *
     * @return 返回结果。
     */
    @SneakyThrows
    private List<UserInfoModel4> getList() {
        UserInfoModel4 user1 = new UserInfoModel4();
        user1.setUserName("郭德纲"); // 用户姓名。
        user1.setUserGender(1); // 用户性别。
        user1.setUserBirth("1973-01-18"); // 用户生日。
        user1.setUserScore(100); // 用户积分。
        user1.setUserReward(BigDecimal.valueOf(123.45)); // 用户佣金。

        UserInfoModel4 user2 = new UserInfoModel4();
        user2.setUserName("于谦"); // 用户姓名。
        user2.setUserGender(2); // 用户性别。
        user2.setUserBirth("1967-12-06"); // 用户生日。
        user2.setUserScore(200); // 用户积分。
        user2.setUserReward(BigDecimal.valueOf(234.56)); // 用户佣金。

        UserInfoModel4 user3 = new UserInfoModel4();
        user3.setUserName("岳云鹏"); // 用户姓名。
        user3.setUserGender(0); // 用户性别。
        user3.setUserBirth("1985-09-17"); // 用户生日。
        user3.setUserScore(300); // 用户积分。
        user3.setUserReward(BigDecimal.valueOf(345.67)); // 用户佣金。

        return Arrays.asList(user1, user2, user3);
    }
}
