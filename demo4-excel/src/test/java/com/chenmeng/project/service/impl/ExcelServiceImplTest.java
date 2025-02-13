package com.chenmeng.project.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenmeng.project.utils.ZipUtil;
import com.chenmeng.project.handler.CustomColumnWidthStyleStrategy;
import com.chenmeng.project.model.entity.ExcelDO;
import com.chenmeng.project.model.entity.Test3;
import com.chenmeng.project.model.vo.ExcelExportVO;
import com.chenmeng.project.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
class ExcelServiceImplTest {

    @Resource
    private ExcelService excelService;

    public static final String path = "C:\\Users\\乔\\Desktop\\excel.xlsx";

    /**
     * Thumbnails 压缩图片导出 -- 手动删除资源版
     */
    @Test
    void export() {

        // 1、获取导出列表
        List<ExcelDO> list = excelService.list();
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

    /**
     * Thumbnails 压缩图片导出 -- 使用 try-with-resources 自动关闭资源版
     * 压缩效果较差
     */
    @Test
    void export11() {

        // 1、获取导出列表
        List<ExcelDO> list = excelService.list();
        List<ExcelExportVO> list2 = list.stream().map(item -> {
            ExcelExportVO exportVO = new ExcelExportVO();
            exportVO.setName(item.getName());

            try {
                // 压缩图像并保存到临时文件
                File compressedFile;
                try (OutputStream outputStream = Files.newOutputStream((compressedFile = File.createTempFile("compressed_image", ".jpg")).toPath())) {
                    Thumbnails.of(new URL(item.getFile()))
                            .scale(0.5) // 设置压缩比例
                            .toOutputStream(outputStream);
                }
                // 将临时文件路径设置到ExcelExportVO对象中
                exportVO.setFile(compressedFile.toURI().toURL());
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
    }

    /**
     * 根据用户传入字段,指定导出的列
     */
    @Test
    void export2() {

        LambdaQueryWrapper<ExcelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExcelDO::getName, "孙子异");

        // 1、获取导出列表
        List<ExcelDO> list = excelService.list(wrapper);
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
        Set<String> includeColumnFiledNames = new HashSet<>();
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

    /**
     * 动态表头
     */
    @Test
    void export3() {

        String day = "10月28日-11月3日";

        List<List<String>> headList = new ArrayList<>();
        ArrayList<String> headColumn1 = new ArrayList<>();
        headColumn1.add("序号");
        headList.add(headColumn1);

        ArrayList<String> headColumn2 = new ArrayList<>();
        headColumn2.add("镇街");
        headList.add(headColumn2);

        ArrayList<String> headColumn3 = new ArrayList<>();
        headColumn3.add("辖区所");
        headList.add(headColumn3);

        ArrayList<String> headColumn4 = new ArrayList<>();
        headColumn4.add(day);
        headColumn4.add("本周入驻（户）");
        headList.add(headColumn4);

        ArrayList<Test3> list2 = new ArrayList<>();
        Test3 test3 = new Test3();
        test3.setId(1);
        test3.setTownName("测试测试测试");
        test3.setCityName("测试测试测试测试测试");
        test3.setCount(10);
        list2.add(test3);

        EasyExcel.write(path)
                .sheet()
                .head(headList)
                // 自动列宽
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(list2);
    }

    /**
     * 内容表格合并
     */
    @Test
    void export4() {

        String day = "10月28日-11月3日";

        List<List<String>> headList = new ArrayList<>();
        ArrayList<String> headColumn1 = new ArrayList<>();
        headColumn1.add("序号");
        headList.add(headColumn1);

        ArrayList<String> headColumn2 = new ArrayList<>();
        headColumn2.add("镇街");
        headList.add(headColumn2);

        ArrayList<String> headColumn3 = new ArrayList<>();
        headColumn3.add("辖区所");
        headList.add(headColumn3);

        ArrayList<String> headColumn4 = new ArrayList<>();
        headColumn4.add(day);
        headColumn4.add("本周入驻（户）");
        headList.add(headColumn4);

        ArrayList<Test3> list2 = new ArrayList<>();
        Test3 test3 = new Test3();
        test3.setId(1);
        test3.setTownName("测试测试测试");
        test3.setCityName("测试测试测试测试测试");
        test3.setCount(10);
        list2.add(test3);
        list2.add(test3);
        Test3 newTest3 = new Test3();
        newTest3.setId(2);
        newTest3.setTownName("测试测试测试2");
        newTest3.setCityName("测试测试测试测试测试2");
        newTest3.setCount(100);
        list2.add(newTest3);

        EasyExcel.write(path)
                .sheet()
                .head(headList)
                // 自定义自动列宽
                .registerWriteHandler(new CustomColumnWidthStyleStrategy())
                .doWrite(list2);
    }

    /**
     * 压缩包导出
     */
    @Test
    void export5() throws IOException {
        LambdaQueryWrapper<ExcelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExcelDO::getName, "孙子异");
        List<ExcelDO> list = excelService.list(wrapper);
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

        List<InputStream> ins = new ArrayList<>();
        OutputStream out1 = new ByteArrayOutputStream();
        OutputStream out2 = new ByteArrayOutputStream();

        // 2、导出
        EasyExcel.write(out1)
                .sheet("第一个")
                .head(ExcelExportVO.class)
                .doWrite(list2);
        ins.add(outputStream2InputStream(out1));

        EasyExcel.write(out2)
                .sheet("第二个")
                .head(ExcelExportVO.class)
                .doWrite(list2);
        ins.add(outputStream2InputStream(out2));

        File zipFile = new File("C:\\Users\\乔\\Desktop\\noModelWrite.zip");

        // 压缩包内流的文件名
        List<String> paths = Arrays.asList("1.xlsx", "2.xlsx");

        ZipUtil.zip(zipFile, paths, ins);

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

    /**
     * 压缩包导出 -- hutool
     */
    @Test
    void export6() {

        List<ExcelDO> list = new ArrayList<>();
        list.add(new ExcelDO("张三", "abc"));

        List<ByteArrayInputStream> ins = new ArrayList<>();

        // 导出第一个Excel
        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        EasyExcel.write(out1, ExcelDO.class).sheet("第一个").doWrite(list);
        ins.add(new ByteArrayInputStream(out1.toByteArray()));

        // 导出第二个Excel
        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        EasyExcel.write(out2, ExcelDO.class).sheet("第二个").doWrite(list);
        ins.add(new ByteArrayInputStream(out2.toByteArray()));

        // 将多个 InputStream 压缩到一个 zip 文件
        File zipFile = new File("C:\\Users\\乔\\Desktop\\noModelWrite.zip");
        String[] fileNames = {"1.xlsx", "2.xlsx"};
        InputStream[] inputStreams = ins.toArray(new InputStream[0]);

        cn.hutool.core.util.ZipUtil.zip(zipFile, fileNames, inputStreams);

    }

    /**
     * 输出流转输入流；数据量过大请使用其他方法
     *
     * @param out
     * @return
     */
    private ByteArrayInputStream outputStream2InputStream(OutputStream out) {
        Objects.requireNonNull(out);
        ByteArrayOutputStream bos;
        bos = (ByteArrayOutputStream) out;
        return new ByteArrayInputStream(bos.toByteArray());
    }


}