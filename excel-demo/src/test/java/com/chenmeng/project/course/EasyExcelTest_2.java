package com.chenmeng.project.course;

import com.alibaba.excel.EasyExcel;
import com.chenmeng.project.excel.listener.UserInfoReadListener;
import com.chenmeng.project.excel.model.UserInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Slf4j
@SuppressWarnings("all")
public class EasyExcelTest_2 {

    /**
     * 测试读取Excel并监听用户信息解析的函数。
     * 该函数旨在演示如何使用EasyExcel框架从Excel文件中批量读取用户信息，并根据指定条件进行处理。
     */
    @Test
    public void testReadListener() {
        // 获取测试Excel文件的输入流
        InputStream inputStream = this.getClass()
                .getResourceAsStream("/excel/user-list.xlsx");

        // 定义条件谓词，用于筛选用户性别为男性的用户
        Predicate<UserInfoModel> predicate = (item) -> item.getUserGender() == 1;

        // 创建用户信息读取监听器，用于处理符合指定条件的用户信息

        // 1、方法引用 -- this::saveBatch 指向了当前对象（this）中的 saveBatch 方法。
        UserInfoReadListener userInfoReadListener1 = new UserInfoReadListener(predicate, this::saveBatch);
        // 2、使用Lambda表达式
        UserInfoReadListener userInfoReadListener2 = new UserInfoReadListener(predicate,
                (List<UserInfoModel> models) -> saveBatch(models));
        // 3、使用匿名内部类
        UserInfoReadListener userInfoReadListener3 = new UserInfoReadListener(predicate,
                new Consumer<List<UserInfoModel>>() {
                    @Override
                    public void accept(List<UserInfoModel> models) {
                        saveBatch(models);
                    }
                });

        // 使用EasyExcel读取Excel文件，指定数据模型类和监听器
        // 读取第一个工作表，并执行读取操作
        EasyExcel.read(inputStream, UserInfoModel.class, userInfoReadListener1)
                .sheet(0)
                .doRead();
    }

    @Test
    public void testReadListener2() {
        InputStream inputStream = EasyExcelTest_2.class
                .getClassLoader()
                .getResourceAsStream("excel/user-list2.xlsx");

        Predicate<UserInfoModel> predicate = (item) -> item.getUserGender() != -1;

        UserInfoReadListener userInfoReadListener = new UserInfoReadListener(5, predicate, this::saveBatch);

        EasyExcel.read(inputStream, UserInfoModel.class, userInfoReadListener)
                .headRowNumber(6)
                .sheet(0)
                .doRead();

        if (userInfoReadListener.getReadErrorModel() != null) {
            log.info("读取错误：{}", userInfoReadListener.getReadErrorModel());
        } else {
            log.info("读取成功");
        }
    }


    private void saveBatch(List<UserInfoModel> list) {
        log.info("批量插入：{}", list.size());
        for (UserInfoModel item : list) {
            log.info("item: {}", item);
        }
    }
}
