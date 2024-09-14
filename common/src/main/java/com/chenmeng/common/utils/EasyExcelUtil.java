package com.chenmeng.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * EasyExcel工具类
 *
 * @author chenmeng
 */
@Slf4j
@Component
public class EasyExcelUtil extends EasyExcelFactory {

    public static final String EXCEL_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String CHARACTER_UTF_8 = "UTF-8";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-disposition";
    public static final String EXPORT_FAIL_MSG = "导出Excel失败";

    public EasyExcelUtil() {
    }

    /**
     * 将列表以 Excel 响应给前端
     *
     * @param response 响应
     * @param title    文件标题名
     * @param head     Excel head 头
     * @param data     数据列表哦
     * @param <T>      泛型，保证 head 和 data 类型的一致性
     * @throws IOException 写入失败的情况
     */
    public static <T> void write(HttpServletResponse response, String title, Class<T> head, List<T> data) throws IOException {
        setResponseInfo(response, title);
        // 输出 Excel
        EasyExcel.write(response.getOutputStream(), head)
                // 不要自动关闭，交给 Servlet 自己处理
                .autoCloseStream(false)
                // 基于 column 长度，自动适配。最大 255 宽度
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 避免 Long 类型丢失精度
                .registerConverter(new LongStringConverter())
                .sheet()
                .doWrite(data);
    }

    /**
     * 将列表以 Excel 响应给前端
     *
     * @param response  响应
     * @param title     文件标题名
     * @param sheetName Excel sheet 名
     * @param head      Excel head 头
     * @param data      数据列表哦
     * @param <T>       泛型，保证 head 和 data 类型的一致性
     * @throws IOException 写入失败的情况
     */
    public static <T> void writeWithSheetName(HttpServletResponse response, String title, String sheetName,
                                              Class<T> head, List<T> data) throws IOException {
        setResponseInfo(response, title);
        // 输出 Excel
        EasyExcel.write(response.getOutputStream(), head)
                // 不要自动关闭，交给 Servlet 自己处理
                .autoCloseStream(false)
                // 基于 column 长度，自动适配。最大 255 宽度
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 避免 Long 类型丢失精度
                .registerConverter(new LongStringConverter())
                .sheet(sheetName)
                .doWrite(data);
    }

    @SneakyThrows
    public static <T> List<T> doReadAllSync(MultipartFile file, Class<T> head) {
        return EasyExcel.read(file.getInputStream(), head, null)
                // 不要自动关闭，交给 Servlet 自己处理
                .autoCloseStream(false)
                // 同步读取所有数据
                .doReadAllSync();
    }

    public static void setResponseInfo(HttpServletResponse response, String title) {
        // 设置内容类型
        response.setContentType(EXCEL_CONTENT_TYPE);
        // 设置字符编码
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easy excel没有关系
        String fileName;
        try {
            fileName = URLEncoder.encode(title, CHARACTER_UTF_8).replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        // 设置响应头
        response.setHeader(
                HEADER_CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }

    public static void returnResult(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding(CHARACTER_UTF_8);
        if (StrUtil.isBlank(message)) {
            message = EXPORT_FAIL_MSG;
        }
        Map<String, Object> map = MapUtils.newLinkedHashMap();
        map.put("code", 1);
        map.put("msg", message);
        response.getWriter().println(JSONUtil.toJsonStr(map));
        log.error(message);
    }
}
