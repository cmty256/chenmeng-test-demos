package com.chenmeng.common.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author chenmeng
 */
public class FileUtil {

    private static final String FILE_DOT = ".";

    /**
     * 将 MultipartFile 保存到指定目录
     *
     * @param multipartFile 文件
     * @param targetDirectory 目标目录
     * @return 新文件的完整路径
     * @throws IOException
     */
    public static String saveFileToDirectory(MultipartFile multipartFile, String targetDirectory)
            throws IOException {
        // 生成随机文件名
        String randomFileName = UUID.randomUUID().toString();
        String fileExtension = getFileExtension(multipartFile.getOriginalFilename());
        if (StrUtil.isBlank(fileExtension)) {
            return "文件格式错误！";
        }
        // 构建目标文件路径（包括随机文件名）
        Path targetFilePath = Paths.get(targetDirectory, randomFileName + FILE_DOT + fileExtension);
        // 检查并创建目标目录（如果不存在）
        Files.createDirectories(targetFilePath.getParent());
        // 将 MultipartFile 保存到目标路径
        multipartFile.transferTo(targetFilePath.toFile());
        // 返回新文件的完整路径
        return targetFilePath.toString();
    }

    /**
     * 获取文件扩展名
     *
     * @param originalFilename 原始文件名
     * @return 小写扩展名
     */
    public static String getFileExtension(String originalFilename) {
        if (StrUtil.isBlank(originalFilename)) {
            return null;
        }
        int extensionIndex = originalFilename.lastIndexOf(".");
        if (extensionIndex == -1) {
            // 若没有找到点号（即无扩展名），返回空字符串
            return null;
        }
        // 提取并返回扩展名（转为小写）
        return originalFilename.substring(extensionIndex + 1).toLowerCase();
    }
}
