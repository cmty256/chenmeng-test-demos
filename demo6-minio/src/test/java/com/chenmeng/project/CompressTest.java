package com.chenmeng.project;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import com.chenmeng.project.utils.ThumbnailatorUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 压缩图片测试
 *
 * @author chenmeng
 */
@SpringBootTest
public class CompressTest {

    String compressPath = "D:\\codes\\ok\\chenmeng-test-demos\\demo6-minio\\src\\main\\resources\\file\\compress.jpg";
    String srcPath = "D:\\codes\\ok\\chenmeng-test-demos\\demo6-minio\\src\\main\\resources\\file\\test.jpg";
    String outPath = "D:\\codes\\ok\\chenmeng-test-demos\\demo6-minio\\src\\main\\resources\\file\\out.jpg";

    @SneakyThrows
    @Test
    public void testByThumbnailator() {
        ClassPathResource resource = new ClassPathResource("file/test.jpg");
        try (InputStream fileStream = resource.getInputStream()) {
            byte[] bytes = fileStream.readAllBytes();
            System.out.println("原图: " + bytes.length);
            System.out.println("原图(KB): " + bytes.length / 1024);

            // 创建一个新的 ByteArrayInputStream，以便可以多次读取
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            byte[] compressImage = ThumbnailatorUtils.compressImage(byteArrayInputStream, 0.7, 0.5);
            System.out.println("压缩后: " + compressImage.length);
            System.out.println("压缩后(KB): " + compressImage.length / 1024);

            FileUtil.writeBytes(compressImage, compressPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testByHutool() {
        File srcFile = FileUtil.file(srcPath);
        System.out.println("原图: " + srcFile.length());
        System.out.println("原图(KB): " + srcFile.length() / 1024);
        ImgUtil.scale(
                srcFile,
                FileUtil.file(outPath),
                0.5f
        );
        System.out.println("压缩后 = " + FileUtil.file(outPath).length());
        System.out.println("压缩后(KB) = " + FileUtil.file(outPath).length() / 1024);
    }
}
