package com.chenmeng.project.process;

import cn.hutool.core.date.DateUtil;
import com.chenmeng.common.constants.enums.VideoConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * @author chenmeng
 */
@Slf4j
public class FfmpegProcess {

    static String inputFile = VideoConstant.VIDEO_FILE_PATH1;
    static String inputFile2 = VideoConstant.VIDEO_INPUT_PATH;

    static String outputFile = VideoConstant.OUTPUT_FILE_PREFIX + "\\clip.mp4";

    static String transcodeFile = VideoConstant.OUTPUT_FILE_PREFIX + "\\transcode.avi";

    static String startTime = "00:00:10";
    static int durationSecond = 10;

    static String rtspUrl = VideoConstant.RTSP_URL_1;
    static String streamOutputFile = VideoConstant.FRAME_FILE_PREFIX + File.separator + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss") + ".jpg";

    public static void main(String[] args) throws Exception {

        // String command = getVideoClipCommand(inputFile, outputFile, startTime, durationSecond);
        // String command = getVideoTranscodeCommand(inputFile2, transcodeFile);
        String command = getStreamFrameExtractionCommand(rtspUrl, streamOutputFile);

        System.out.println("执行命令: " + command);
        // 创建操作系统进程
        ProcessBuilder builder = new ProcessBuilder();

        // 执行命令
        executeCommand(builder, command, "windows");

        // 合并标准输出和标准错误输出流
        builder.redirectErrorStream(true);
        Process process = builder.start();

        // 异步读取输出流，避免阻塞
        CompletableFuture<Void> future = readOutputAsync(process.getInputStream());

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("执行成功");
        } else {
            System.err.println("执行过程中出现错误，退出代码：" + exitCode);
        }
    }

    /**
     * 获取实时视频流抽帧的命令（只抓取一张图片）
     *
     * @param url        视频流 url，例如 rtsp 流地址
     * @param outputFile 输出文件路径
     * @return 实时视频流抽帧的命令
     */
    private static String getStreamFrameExtractionCommand(String url, String outputFile) {
        return String.format("%s -y -i %s -ss 1 -frames:v 1 %s", VideoConstant.FFMPEG_PATH, url, outputFile);
    }

    /**
     * 获取视频转码的命令
     *
     * @param inputFile     输入文件路径
     * @param transcodeFile 转码文件路径
     * @return 视频转码的命令
     */
    private static String getVideoTranscodeCommand(String inputFile, String transcodeFile) {
        return String.format("%s -y -i %s %s", VideoConstant.FFMPEG_PATH, inputFile, transcodeFile);
    }

    /**
     * 获取剪辑视频的命令
     *
     * @param inputFile      输入文件路径
     * @param outputFile     输出文件路径
     * @param startTime      视频的起始位置
     * @param durationSecond 截取的时长（秒）
     * @return 剪辑视频的命令
     */
    private static String getVideoClipCommand(String inputFile, String outputFile, String startTime, Integer durationSecond) {
        // 检查输出文件是否存在（可利用 ffmpeg 的 -y 参数实现 覆盖功能）
        // deleteFileIfExists(outputFile);

        // return String.format("ffmpeg -y -ss %s -t %d -i %s -c copy %s", startTime, durationSecond, inputFile, outputFile);
        return String.format("%s -y -ss %s -t %d -i %s -c copy %s", VideoConstant.FFMPEG_PATH, startTime, durationSecond, inputFile, outputFile);
    }

    private static void deleteFileIfExists(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            System.out.println("文件已存在，删除旧文件：" + filePath);
            if (!file.delete()) {
                System.err.println("无法删除文件: " + filePath);
            }
        }
    }

    /**
     * 异步读取 ffmpeg 输出流
     */
    private static CompletableFuture<Void> readOutputAsync(InputStream inputStream) {
        return CompletableFuture.runAsync(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                log.error("读取 ffmpeg 输出流异常", e);
            }
        });
    }

    /**
     * 执行命令
     *
     * @param builder 进程构建器
     * @param command 命令
     * @param osType  操作系统类型
     */
    private static void executeCommand(ProcessBuilder builder, String command, String osType) {
        switch (osType) {
            case "windows":
                builder.command("cmd", "/c", command);
                break;
            case "Linux":
            case "macOS":
                builder.command("bash", "-c", command);
                break;
            default:
                throw new RuntimeException("不支持的操作系统类型");
        }
    }
}