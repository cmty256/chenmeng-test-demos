package com.chenmeng.project.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 参考：<a href="https://juejin.cn/post/6854573215528796168?searchId=202409231556179F71AADCA088F215BA7A">...</a>
 *
 * @author chenmeng
 */
@Slf4j
public class ImageUtils {

    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @param imageId     影像编号
     * @param quality     压缩质量（0-1之间可选）
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(
            byte[] imageBytes, long desFileSize, String imageId, Double quality) {
        if (imageBytes == null || imageBytes.length == 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }

        long srcSize = imageBytes.length;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);

            Thumbnails.of(inputStream)
                    .scale(1f)
                    .outputQuality(quality)
                    .toOutputStream(outputStream);

            imageBytes = outputStream.toByteArray();
            log.info(
                    "【图片压缩】imageId={} | 图片原大小={}kb | 压缩后大小={}kb",
                    imageId,
                    srcSize / 1024,
                    imageBytes.length / 1024);
        } catch (Exception e) {
            log.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }
}
