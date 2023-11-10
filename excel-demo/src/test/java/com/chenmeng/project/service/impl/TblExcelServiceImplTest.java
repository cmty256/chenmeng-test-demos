package com.chenmeng.project.service.impl;

import cn.hutool.core.img.Img;
import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenmeng.project.convert.AlertLevelConvert;
import com.chenmeng.project.model.entity.Alarm;
import com.chenmeng.project.model.entity.TblExcel;
import com.chenmeng.project.model.vo.AlarmExportVO;
import com.chenmeng.project.model.vo.ExcelExportVO;
import com.chenmeng.project.service.AlarmService;
import com.chenmeng.project.service.TblExcelService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
class TblExcelServiceImplTest {

    @Resource
    private TblExcelService excelService;

    @Resource
    private AlarmService alarmService;

    @Test
    void export() {

        // 1、获取导出列表
        List<TblExcel> list = excelService.list();
        List<ExcelExportVO> list2 = list.stream().map(item -> {
            ExcelExportVO exportVO = new ExcelExportVO();
            exportVO.setName(item.getName());
            try {
                // 1.1 压缩图片并保存到临时文件
                File compressedFile = File.createTempFile("compressed_image", ".jpg");
                // 1.2 压缩图片
                Thumbnails.of(new URL(item.getFile()))
                        .scale(0.5) // 设置压缩比例
                        .toFile(compressedFile);

                // 1.3 将临时文件路径设置到ExcelExportVO对象中
                exportVO.setFile(compressedFile.toURI().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException("压缩图片失败!!!", e);
            }
            return exportVO;
        }).collect(Collectors.toList());

        // 2、导出
        EasyExcel.write("D:\\TblExcel.xls")
                .sheet("模板")
                .head(ExcelExportVO.class)
                .doWrite(list2);

        // 3、使用完毕后手动删除临时文件
        for (ExcelExportVO exportVO : list2) {
            try {
                File compressedFile = new File(exportVO.getFile().toURI());
                if (!compressedFile.delete()) {
                    // 删除操作失败，记录日志或进行其他错误处理
                    log.error("删除临时文件失败: " + compressedFile.getAbsolutePath());
                }
            } catch (Exception e) {
                // 处理删除临时文件的异常
                e.printStackTrace();
            }
        }

    }

    @Test
    void exportAlarm() throws InstantiationException, IllegalAccessException {

        // 1、获取导出列表
        List<Alarm> list = alarmService.list();
        List<AlarmExportVO> list2 = list.stream().map(item -> {
            AlarmExportVO exportVO = new AlarmExportVO();
            exportVO.setEnterpriseTown(item.getEnterpriseTown());
            exportVO.setMarketSupervision(item.getMarketSupervision());
            exportVO.setDeviceName(item.getDeviceName());
            exportVO.setAlarmType(item.getAlarmType());
            exportVO.setAlarmLevel(item.getAlarmLevel());
            exportVO.setAlarmTime(item.getAlarmTime());

            try {
                // 1.1 压缩图片并保存到临时文件
                File compressedFile = File.createTempFile("compressed_image", ".jpg");
                // 1.2 压缩图片
                Thumbnails.of(new URL(item.getAlarmImage()))
                        .scale(0.5) // 设置压缩比例
                        .toFile(compressedFile);

                // 1.3 将临时文件路径设置到ExcelExportVO对象中
                exportVO.setAlarmImage(compressedFile.toURI().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException("压缩图片失败!!!", e);
            }
            return exportVO;
        }).collect(Collectors.toList());

        // 2、导出
        EasyExcel.write("D:\\AlarmExcel.xls", AlarmExportVO.class)
                .sheet("模板")
                .doWrite(list2);

        // 3、使用完毕后手动删除临时文件
        for (AlarmExportVO exportVO : list2) {
            try {
                File compressedFile = new File(exportVO.getAlarmImage().toURI());
                if (!compressedFile.delete()) {
                    // 删除操作失败，记录日志或进行其他错误处理
                    log.error("删除临时文件失败: " + compressedFile.getAbsolutePath());
                }
            } catch (Exception e) {
                // 处理删除临时文件的异常
                e.printStackTrace();
            }
        }

    }

    @Test
    void export2() {

        LambdaQueryWrapper<TblExcel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblExcel::getName, "孙子异");

        // 1、获取导出列表
        List<TblExcel> list = excelService.list(wrapper);
        List<ExcelExportVO> list2 = list.stream().map(item -> {
            ExcelExportVO exportVO = new ExcelExportVO();
            exportVO.setName(item.getName());
            try {
                // 1.1 压缩图片并保存到临时文件
                File compressedFile = File.createTempFile("compressed_image", ".jpg");
                // 1.2 压缩图片
                Thumbnails.of(new URL(item.getFile()))
                        .scale(0.5) // 设置压缩比例
                        .toFile(compressedFile);

                // 1.3 将临时文件路径设置到ExcelExportVO对象中
                exportVO.setFile(compressedFile.toURI().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException("压缩图片失败!!!", e);
            }
            return exportVO;
        }).collect(Collectors.toList());

        // 根据用户传入字段 假设我们只要导出 file
        Set<String> includeColumnFiledNames = new HashSet<String>();
        includeColumnFiledNames.add("file");

        // 2、导出
        EasyExcel.write("D:\\TblExcel.xls")
                .sheet("模板")
                .head(ExcelExportVO.class)
                .includeColumnFieldNames(includeColumnFiledNames)
                // .includeColumnIndexes(Collections.singleton(1))
                .doWrite(list2);

        // 3、使用完毕后手动删除临时文件
        for (ExcelExportVO exportVO : list2) {
            try {
                File compressedFile = new File(exportVO.getFile().toURI());
                if (!compressedFile.delete()) {
                    // 删除操作失败，记录日志或进行其他错误处理
                    log.error("删除临时文件失败: " + compressedFile.getAbsolutePath());
                }
            } catch (Exception e) {
                // 处理删除临时文件的异常
                e.printStackTrace();
            }
        }

    }

}