package com.chenmeng.project.process;

import com.chenmeng.common.constants.enums.VideoConstant;

import java.io.File;

/**
 * @author chenmeng
 */
public class FfmpegProcess {

    static String inputFile = VideoConstant.VIDEO_FILE_PATH1;
    static String inputFile2 = VideoConstant.VIDEO_INPUT_PATH;

    static String outputFile = VideoConstant.OUTPUT_FILE_PREFIX + "\\clip.mp4";

    static String transcodeFile = VideoConstant.OUTPUT_FILE_PREFIX + "\\transcode.avi";

    static String startTime = "00:00:10";
    static int durationSecond = 10;

    public static void main(String[] args) throws Exception {

        // String command = getVideoClipCommand(inputFile, outputFile, startTime, durationSecond);
        String command = getVideoTranscodeCommand(inputFile2, transcodeFile);

        System.out.println("执行命令: " + command);
        ProcessBuilder builder = new ProcessBuilder();

        // Linux/macOS
        // builder.command("bash", "-c", command);

        // Windows
        builder.command("cmd", "/c", command);

        Process process = builder.start();

        // todo 解决缓冲区填满，进程阻塞的情况
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("执行成功");
        } else {
            System.err.println("执行过程中出现错误，退出代码：" + exitCode);
        }
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
}