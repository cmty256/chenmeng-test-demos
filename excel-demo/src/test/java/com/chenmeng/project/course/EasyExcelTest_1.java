package com.chenmeng.project.course;

import com.alibaba.excel.EasyExcel;
import com.chenmeng.project.excel.model.UserInfoModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@SuppressWarnings("all")
public class EasyExcelTest_1 {

    /**
     * 读取 Excel
     * doReadSync -- 同步读取, 并返回 List
     */
    @SneakyThrows
    @Test
    public void testFile() {
        ClassPathResource resource = new ClassPathResource("excel/user-list.xlsx");
        InputStream file = resource.getInputStream();

        List<Object> list = EasyExcel.read(file)
                .sheet(0)
                .doReadSync();

        for (Object item : list) {
            log.info("item: {}", item);
        }
    }

    /**
     * 读取 Excel
     * 返回一个 List<Map<Integer, Object>>
     */
    @Test
    public void testInputStream() {
        InputStream inputStream = this.getClass()
                .getResourceAsStream("/excel/user-list.xlsx");

        List<Map<Integer, Object>> list = EasyExcel.read(inputStream)
                .sheet(0)
                .doReadSync();

        for (Map<Integer, Object> item : list) {
            log.info("昵称: {}, 性别: {}, 生日: {}, 邮箱: {}, 积分: {}", item.get(0),
                    item.get(1), item.get(2), item.get(3), item.get(4));
        }
    }

    /**
     * 利用 Model 读取 Excel
     * 返回一个 List<UserInfoModel>
     */
    @Test
    public void testInputStreamWithModel() {
        InputStream inputStream = EasyExcelTest_1.class
                .getClassLoader()
                .getResourceAsStream("excel/user-list.xlsx");

        List<UserInfoModel> list = EasyExcel.read(inputStream)
                .sheet(0)
                .head(UserInfoModel.class)
                .doReadSync();

        for (UserInfoModel item : list) {
            log.info("昵称: {}, 性别: {}, 生日: {}, 邮箱: {}, 积分: {}, 排名: {}",
                    item.getUserName(), item.getUserGender(), item.getUserBirth(),
                    item.getUserEmail(), item.getUserScore(), item.getUserRank());
        }
    }
}