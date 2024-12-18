package com.chenmeng.common.utils;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class EasyExcelUtil {

    /**
     * JSON 内容类型的 MIME 类型，通常用于 RESTful API 响应。
     */
    private static final String CONTENT_TYPE = "application/json";

    /**
     * Excel 文件的内容类型 MIME 类型，指定了文件为 Office Open XML 格式的电子表格。
     */
    private static final String EXCEL_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * UTF-8 字符编码，用于确保 Excel 文件名正确显示，特别是在国际化场景中。
     */
    private static final String CHARACTER_UTF_8 = "UTF-8";

    /**
     * HTTP 响应头中的 Content-disposition 字段，用于控制响应的显示方式。
     */
    private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * Content-disposition 响应头的值，用于指示浏览器将响应作为文件下载，并指定文件名。
     * 该值使用 RFC 5987 语法进行国际化，确保非 ASCII 字符正确显示。
     */
    private static final String HEADER_CONTENT_DISPOSITION_VALUE = "attachment;filename*=utf-8''";

    /**
     * Excel 文件的默认后缀名，表示 Office Open XML 电子表格文件。
     */
    private static final String FILE_SUFFIX = ".xlsx";

    /**
     * 添加错误码常量
     */
    private static final int FAIL_CODE = -1;

    public EasyExcelUtil() {
    }

    /**
     * Excel 写入
     *
     * @param response  响应
     * @param filename  文件名
     * @param sheetName Excel sheet 名
     * @param head      Excel head 头
     * @param data      数据列表哦
     * @param <T>       泛型，保证 head 和 data 类型的一致性
     * @throws IOException 写入失败的情况
     */
    public static <T> void write(HttpServletResponse response, String filename, String sheetName,
                                 Class<T> head, List<T> data) throws IOException {
        setResponseInfo(response, filename);
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

    /**
     * Excel 同步读取所有数据
     *
     * @param file 文件
     * @param head Excel head 头
     * @param <T>  泛型，保证 head 和 data 类型的一致性
     * @return 数据列表
     * @throws IOException 读取失败的情况
     */
    public static <T> List<T> doReadAllSync(MultipartFile file, Class<T> head) throws IOException {
        return EasyExcel.read(file.getInputStream(), head, null)
                // 不要自动关闭，交给 Servlet 自己处理
                .autoCloseStream(false)
                // 同步读取所有数据
                .doReadAllSync();
    }

    /**
     * 设置响应头信息
     *
     * @param response 响应
     * @param title    文件名
     */
    public static void setResponseInfo(HttpServletResponse response, String title) {
        // 设置内容类型为 Excel 文件类型
        response.setContentType(EXCEL_CONTENT_TYPE);
        // 设置字符编码为 UTF-8
        response.setCharacterEncoding(CHARACTER_UTF_8);
        // 这里URLEncoder.encode可以防止中文乱码（和easy excel没有关系）
        String fileName;
        fileName = URLEncoder.encode(title, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        // 设置响应头，指示浏览器以附件形式下载文件
        response.setHeader(
                HEADER_CONTENT_DISPOSITION, HEADER_CONTENT_DISPOSITION_VALUE + fileName + FILE_SUFFIX);
    }

    /**
     * 发送结果信息给客户端。
     *
     * @param response 当前的 HTTP 响应对象
     * @param errorMsg 要返回给客户端的异常消息内容
     * @throws IOException 抛出响应时发生的 I/O 异常
     */
    public static void returnResult(HttpServletResponse response, String errorMsg) throws IOException {
        // 设置内容类型为 JSON 类型
        response.setContentType(CONTENT_TYPE);
        // 设置字符编码为 UTF-8
        response.setCharacterEncoding(CHARACTER_UTF_8);
        // 创建一个包含状态码和消息的映射
        Map<String, Object> map = MapUtils.newLinkedHashMap();
        map.put("code", FAIL_CODE);
        map.put("msg", errorMsg);
        // 将映射转换为 JSON 字符串，并写入响应
        response.getWriter().println(JSONUtil.toJsonStr(map));
        // 记录错误日志
        log.error(errorMsg);
    }
}
