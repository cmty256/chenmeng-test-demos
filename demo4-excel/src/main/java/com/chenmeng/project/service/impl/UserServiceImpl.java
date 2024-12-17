package com.chenmeng.project.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenmeng.common.model.base.BasePageDTO;
import com.chenmeng.common.model.entity.UserDO;
import com.chenmeng.common.utils.EasyExcelUtil;
import com.chenmeng.project.mapper.UserMapper;
import com.chenmeng.project.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * @author chenmeng
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @SneakyThrows
    @Override
    public void exportByRepeat(BasePageDTO pageReqVO, HttpServletResponse response) {
        // 预热一下程序
        this.baseMapper.selectCount(null);

        // 测试数量：80w 条数据（有一个大字段）（正常情况下均 40s 左右）

        // long begin1 = System.currentTimeMillis();
        // // 【直接查询和写入】, 耗时：s
        // this.exportByRepeat1(pageReqVO, response);
        // log.info("exportByRepeat1，耗时：{}s", (System.currentTimeMillis() - begin1) / 1000L);
        //
        // long begin2 = System.currentTimeMillis();
        // // 【直接查询和分片写入】, 耗时：s
        // this.exportByRepeat2(pageReqVO, response);
        // log.info("exportByRepeat2，耗时：{}s", (System.currentTimeMillis() - begin2) / 1000L);
        //
        // long begin3 = System.currentTimeMillis();
        // // 【分页查询并写入】, 耗时：300s+（服务中断）
        // this.exportByRepeat3(pageReqVO, response);
        // log.info("exportByRepeat3，耗时：{}s", (System.currentTimeMillis() - begin3) / 1000L);
        //
        // long begin4 = System.currentTimeMillis();
        // // 【游标分页查询和分片写入】, 耗时：s
        // this.exportByRepeat4(pageReqVO, response);
        // log.info("exportByRepeat4，耗时：{}s", (System.currentTimeMillis() - begin4) / 1000L);

        long begin5 = System.currentTimeMillis();
        // （推荐）【游标分页查询并写入】, 耗时：126s
        this.exportByRepeat5(pageReqVO, response);
        log.info("exportByRepeat5，耗时：{}s", (System.currentTimeMillis() - begin5) / 1000L);

    }

    /**
     * 直接查询和写入
     */
    @SneakyThrows
    private void exportByRepeat1(BasePageDTO pageReqVO, HttpServletResponse response) {
        Page<UserDO> pageDto = new Page<>(1, -1);
        Page<UserDO> page = this.page(pageDto);
        List<UserDO> records = page.getRecords();
        EasyExcelUtil.write(response, "用户信息", "sheet1", UserDO.class, records);
    }

    /**
     * 直接查询和分片写入
     */
    @SneakyThrows
    private void exportByRepeat2(BasePageDTO pageReqVO, HttpServletResponse response) {
        Page<UserDO> pageDto = new Page<>(1, -1);
        Page<UserDO> page = this.page(pageDto);
        List<UserDO> records = page.getRecords();

        List<List<UserDO>> split = ListUtil.split(records, 10_0000);

        EasyExcelUtil.setResponseInfo(response, "用户信息");
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), UserDO.class).build()) {
            // 创建一个 WriteSheet 对象
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
            for (List<UserDO> list : split) {
                excelWriter.write(list, writeSheet);
            }
        }
    }

    /**
     * 分页查询并写入
     */
    @SneakyThrows
    private void exportByRepeat3(BasePageDTO pageReqVO, HttpServletResponse response) {
        long size = 10_0000;
        long total = this.count();
        long pages = (total + size - 1) / size;

        EasyExcelUtil.setResponseInfo(response, "用户信息");
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), UserDO.class).build()) {
            // 创建一个 WriteSheet 对象
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();

            if (total >= 10_0000) {
                // 大数据量分页处理
                for (int i = 0; i < pages; i++) {
                    // 分页查询数据
                    Page<UserDO> pageQuery = new Page<>(i + 1, size);
                    List<UserDO> records = this.page(pageQuery).getRecords();
                    if (records.isEmpty()) {
                        break;
                    }

                    // 写入数据到 Excel
                    excelWriter.write(records, writeSheet);
                }
            } else {
                // 小数据量一次性查询并写入
                Page<UserDO> pageDto = new Page<>(1, -1);
                Page<UserDO> page = this.page(pageDto);
                List<UserDO> records = page.getRecords();
                if (!records.isEmpty()) {
                    excelWriter.write(records, writeSheet);
                }
            }
        }
    }

    /**
     * 游标分页查询和分片写入
     */
    @SneakyThrows
    private void exportByRepeat4(BasePageDTO pageReqVO, HttpServletResponse response) {
        List<UserDO> exportList = new LinkedList<>();
        Long id;

        List<UserDO> list = this.lambdaQuery()
                .last("limit 100000")
                .list();
        if (!list.isEmpty()) {
            exportList.addAll(list);
            id = list.get(list.size() - 1).getId();
            while (true) {
                List<UserDO> list1 = this.lambdaQuery()
                        .gt(UserDO::getId, id)
                        .last("limit 100000")
                        .list();
                if (list1.isEmpty()) {
                    break;
                }
                exportList.addAll(list1);
                id = list1.get(list1.size() - 1).getId();
            }
        }

        List<List<UserDO>> split = ListUtil.split(exportList, 10_0000);

        EasyExcelUtil.setResponseInfo(response, "用户信息");
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), UserDO.class).build()) {
            // 创建一个 WriteSheet 对象
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
            for (List<UserDO> doList : split) {
                excelWriter.write(doList, writeSheet);
            }
        }
    }

    /**
     * 游标分页查询并写入
     */
    @SneakyThrows
    private void exportByRepeat5(BasePageDTO pageReqVO, HttpServletResponse response) {
        List<UserDO> exportList = new LinkedList<>();
        Long id;
        EasyExcelUtil.setResponseInfo(response, "用户信息");
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), UserDO.class).build()) {
            // 创建一个 WriteSheet 对象
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();

            List<UserDO> list = this.lambdaQuery()
                    .last("limit 100000")
                    .list();
            if (!list.isEmpty()) {
                exportList.addAll(list);
                excelWriter.write(exportList, writeSheet);
                exportList.clear();

                id = list.get(list.size() - 1).getId();
                while (true) {
                    List<UserDO> list1 = this.lambdaQuery()
                            .gt(UserDO::getId, id)
                            .last("limit 100000")
                            .list();
                    if (list1.isEmpty()) {
                        break;
                    }
                    exportList.addAll(list1);
                    excelWriter.write(exportList, writeSheet);
                    exportList.clear();

                    id = list1.get(list1.size() - 1).getId();
                }
            }
        }
    }
}
