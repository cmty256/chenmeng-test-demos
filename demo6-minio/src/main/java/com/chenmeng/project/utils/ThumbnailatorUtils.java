package com.chenmeng.project.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 官网：<a href="https://github.com/coobird/thumbnailator/blob/master/pom.xml">...</a>
 *
 * @author chenmeng
 */
public class ThumbnailatorUtils {

    /**
     * 将上传的图片进行压缩，并返回压缩后的字节数组。
     *
     * @param file 待压缩的图片文件
     * @return 压缩后的字节数组
     * @throws IOException 读取文件或写入输出流时发生的异常
     */
    public static byte[] compressImage(MultipartFile file) throws IOException {
        return compressImage(file.getInputStream(), 0, 0);
    }


    /**
     * 将上传的图片按照指定质量进行压缩，并返回压缩后的字节数组。
     *
     * @param file    待压缩的图片文件
     * @param quality JPEG压缩质量，取值范围为0.0-1.0
     * @return 压缩后的字节数组
     * @throws IOException 读取文件或写入输出流时发生的异常
     */
    public byte[] compressImage(MultipartFile file, double quality) throws IOException {
        return compressImage(file.getInputStream(), 0, quality);
    }

    public static byte[] compressImage(InputStream fileInputStream, double scale, double quality) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (scale > 0 && scale <= 1) {
            Thumbnails.of(fileInputStream)
                    // 保持原始比例
                    .scale(scale)
                    // 设置JPEG质量
                    .outputQuality(quality)
                    // 写入到输出流
                    .toOutputStream(outputStream);
        } else if (quality > 0 && quality <= 1) {
            Thumbnails.of(fileInputStream)
                    // 保持原始比例
                    .scale(1.0)
                    // 设置JPEG质量
                    .outputQuality(quality)
                    // 写入到输出流
                    .toOutputStream(outputStream);
        } else {
            Thumbnails.of(fileInputStream)
                    // 设置最大宽度和高度
                    .size(1024, 768)
                    // 设置输出质量
                    .outputQuality(0.85f)
                    // 写入到输出流
                    .toOutputStream(outputStream);
        }
        return outputStream.toByteArray();
    }
}
