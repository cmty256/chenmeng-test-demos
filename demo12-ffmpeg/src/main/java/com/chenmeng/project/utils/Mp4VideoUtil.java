package com.chenmeng.project.utils;

import com.chenmeng.common.constants.enums.VideoConstant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mp4VideoUtil extends VideoUtil {

    private String ffmpegPath;
    private String videoPath;
    private String outputFilePath;
    private List<String> ffmpegOptions;

    public Mp4VideoUtil(String ffmpegPath, String videoPath, String outputFilePath) {
        super(ffmpegPath);
        this.videoPath = videoPath;
        this.outputFilePath = outputFilePath;
        this.ffmpegOptions = new ArrayList<>();
        // 设置默认的 ffmpeg 参数
        setDefaultFfmpegOptions();
    }

    private void setDefaultFfmpegOptions() {
        ffmpegOptions.add("-c:v");
        ffmpegOptions.add("libx264");
        ffmpegOptions.add("-s");
        ffmpegOptions.add("1280x720");
        ffmpegOptions.add("-pix_fmt");
        ffmpegOptions.add("yuv420p");
        ffmpegOptions.add("-b:a");
        ffmpegOptions.add("63k");
        ffmpegOptions.add("-b:v");
        ffmpegOptions.add("753k");
        ffmpegOptions.add("-r");
        ffmpegOptions.add("18");
        ffmpegOptions.add("-y"); // 覆盖输出文件
    }

    public void setFfmpegOptions(List<String> options) {
        if (options != null && !options.isEmpty()) {
            this.ffmpegOptions = options;
        }
    }

    private void deleteFileIfExists(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            if (!file.delete()) {
                System.err.println("无法删除文件: " + filePath);
            }
        }
    }

    public String generateMp4() {
        // 删除已存在的输出文件
        deleteFileIfExists(outputFilePath);

        List<String> command = new ArrayList<>();
        command.add(ffmpegPath);
        command.add("-i");
        command.add(videoPath);
        command.addAll(ffmpegOptions);
        command.add(outputFilePath);

        String outputLog = null;
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            outputLog = waitFor(process);
        } catch (IOException ex) {
            ex.printStackTrace();
            return "视频转换失败: " + ex.getMessage();
        }

        if (!isVideoDurationEqual(videoPath, outputFilePath)) {
            return "视频转换失败，时长不一致。FFmpeg 输出日志:\n" + outputLog;
        }

        return "success";
    }

    public static void main(String[] args) {
        String ffmpegPath = VideoConstant.FFMPEG_PATH;
        String videoPath = VideoConstant.VIDEO_INPUT_PATH;
        String outputFilePath = VideoConstant.OUTPUT_FILE_PREFIX + "\\test1.avi";

        Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpegPath, videoPath, outputFilePath);
        String result = videoUtil.generateMp4();
        System.out.println(result);
    }
}