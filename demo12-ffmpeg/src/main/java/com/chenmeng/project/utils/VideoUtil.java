package com.chenmeng.project.utils;

import com.chenmeng.common.constants.enums.VideoConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 此文件作为视频文件处理父类，提供：
 * 1、查看视频时长
 * 2、校验两个视频的时长是否相等
 */
public class VideoUtil {

    private final String ffmpegPath;

    public VideoUtil(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }

    // 检查两个视频的时长是否一致
    public boolean isVideoDurationEqual(String source, String target) {
        String sourceDuration = getVideoDuration(source);
        String targetDuration = getVideoDuration(target);
        return sourceDuration != null && sourceDuration.equals(targetDuration);
    }

    // 获取视频时长（格式：时:分:秒）
    public String getVideoDuration(String videoPath) {
        ProcessBuilder builder = new ProcessBuilder(
                ffmpegPath, "-i", videoPath
        );
        builder.redirectErrorStream(true);

        try {
            Process process = builder.start();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("Duration:")) {
                        return line.substring(line.indexOf("Duration:") + 10, line.indexOf(", start:")).trim();
                    }
                }
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String waitFor(Process p) {
        InputStream in = null;
        InputStream error = null;
        String result = "error";
        int exitValue = -1;
        StringBuffer outputString = new StringBuffer();
        try {
            in = p.getInputStream();
            error = p.getErrorStream();
            boolean finished = false;
            int maxRetry = 600;// 每次休眠1秒，最长执行时间10分种
            int retry = 0;
            while (!finished) {
                if (retry > maxRetry) {
                    return "error";
                }
                try {
                    while (in.available() > 0) {
                        Character c = new Character((char) in.read());
                        outputString.append(c);
                        System.out.print(c);
                    }
                    while (error.available() > 0) {
                        Character c = new Character((char) in.read());
                        outputString.append(c);
                        System.out.print(c);
                    }
                    // 进程未结束时调用exitValue将抛出异常
                    exitValue = p.exitValue();
                    finished = true;

                } catch (IllegalThreadStateException e) {
                    Thread.currentThread().sleep(1000);// 休眠1秒
                    retry++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return outputString.toString();

    }

    public static void main(String[] args) {
        String ffmpegPath = VideoConstant.FFMPEG_PATH;
        VideoUtil videoUtil = new VideoUtil(ffmpegPath);

        String videoDuration = videoUtil.getVideoDuration(VideoConstant.VIDEO_INPUT_PATH);
        System.out.println("视频时长: " + videoDuration);

        String videoDuration2 = videoUtil.getVideoDuration(VideoConstant.OUTPUT_FILE_PREFIX + "\\2.mp4");
        System.out.println("视频时长2: " + videoDuration2);
    }
}