package com.chenmeng.project.common;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具类
 *
 * @author wyy
 */
public class ZipUtil {
    
    /**
     * 默认编码，使用平台相关编码
     */
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
 
    /**
     * 将文件流压缩到目标流中
     *
     * @param out       目标流，压缩完成自动关闭
     * @param fileNames 流数据在压缩文件中的路径或文件名
     * @param ins       要压缩的源，添加完成后自动关闭流
     */
    public static void zip(OutputStream out, List<String> fileNames, List<InputStream> ins) throws IOException {
        zip(out, fileNames.toArray(new String[0]), ins.toArray(new InputStream[0]));
    }
 
    /**
     * 将文件流压缩到目标流中
     *
     * @param out       目标流，压缩完成自动关闭
     * @param fileNames 流数据在压缩文件中的路径或文件名
     * @param ins       要压缩的源，添加完成后自动关闭流
     */
    public static void zip(File out, List<String> fileNames, List<InputStream> ins) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(out);
        zip(outputStream, fileNames.toArray(new String[0]), ins.toArray(new InputStream[0]));
        outputStream.flush();
    }
 
    /**
     * 将文件流压缩到目标流中
     *
     * @param out       目标流，压缩完成自动关闭
     * @param fileNames 流数据在压缩文件中的路径或文件名
     * @param ins       要压缩的源，添加完成后自动关闭流
     */
    public static void zip(OutputStream out, String[] fileNames, InputStream[] ins) throws IOException {
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = getZipOutputStream(out, DEFAULT_CHARSET);
            zip(zipOutputStream, fileNames, ins);
        } catch (IOException e) {
            throw new IOException("压缩包导出失败！", e);
        } finally {
            IOUtils.closeQuietly(zipOutputStream);
        }
    }
 
    /**
     * 将文件流压缩到目标流中
     *
     * @param zipOutputStream 目标流，压缩完成不关闭
     * @param fileNames       流数据在压缩文件中的路径或文件名
     * @param ins             要压缩的源，添加完成后自动关闭流
     * @throws IOException IO异常
     */
    public static void zip(ZipOutputStream zipOutputStream, String[] fileNames, InputStream[] ins) throws IOException {
        if (ArrayUtils.isEmpty(fileNames) || ArrayUtils.isEmpty(ins)) {
            throw new IllegalArgumentException("文件名不能为空！");
        }
        if (fileNames.length != ins.length) {
            throw new IllegalArgumentException("文件名长度与输入流长度不一致！");
        }
        for (int i = 0; i < fileNames.length; i++) {
            add(ins[i], fileNames[i], zipOutputStream);
        }
    }
 
    /**
     * 添加文件流到压缩包，添加后关闭流
     *
     * @param in       需要压缩的输入流，使用完后自动关闭
     * @param fileName 压缩的路径
     * @param out      压缩文件存储对象
     * @throws IOException IO异常
     */
    private static void add(InputStream in, String fileName, ZipOutputStream out) throws IOException {
        if (null == in) {
            return;
        }
        try {
            out.putNextEntry(new ZipEntry(fileName));
            IOUtils.copy(in, out);
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            IOUtils.closeQuietly(in);
            closeEntry(out);
        }
    }
 
    /**
     * 获得 {@link ZipOutputStream}
     *
     * @param out     压缩文件流
     * @param charset 编码
     * @return {@link ZipOutputStream}
     */
    private static ZipOutputStream getZipOutputStream(OutputStream out, Charset charset) {
        if (out instanceof ZipOutputStream) {
            return (ZipOutputStream) out;
        }
        return new ZipOutputStream(out, DEFAULT_CHARSET);
    }
 
    /**
     * 关闭当前Entry，继续下一个Entry
     *
     * @param out ZipOutputStream
     */
    private static void closeEntry(ZipOutputStream out) {
        try {
            out.closeEntry();
        } catch (IOException e) {
            // ignore
        }
    }
}