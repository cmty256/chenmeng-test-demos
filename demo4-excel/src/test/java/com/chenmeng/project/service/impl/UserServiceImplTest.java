package com.chenmeng.project.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenmeng.common.model.entity.UserDO;
import com.chenmeng.project.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.List;

/**
 * @author chenmeng
 */
@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    /**
     * 导出路径。
     */
    private final String EXPORT_PATH = "src/main/resources/excel/exportByRepeat.xlsx";

    /**
     * 重复多次写到同一个sheet
     */
    @Test
    void test1() {
        try (ExcelWriter excelWriter = EasyExcel.write(EXPORT_PATH, UserDO.class).build()) {
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
            for (int i = 0; i < 5; i++) {
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                Page<UserDO> pageDto = new Page<>(i + 1, 100);
                List<UserDO> records = userService.page(pageDto).getRecords();
                // System.out.println("当前页：" + pageDto.getCurrent());
                // System.out.println("总页数：" + pageDto.getPages());
                // System.out.println("总条数：" + pageDto.getTotal());
                // System.out.println("当前页数量：" + records.size());
                excelWriter.write(records, writeSheet);
            }
        }
    }
    @Test
    void test2() {
        userService.list();
    }

}