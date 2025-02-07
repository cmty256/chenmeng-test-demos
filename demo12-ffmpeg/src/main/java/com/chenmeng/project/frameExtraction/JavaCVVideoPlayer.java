package com.chenmeng.project.frameExtraction;

import com.chenmeng.common.constants.enums.VideoConstant;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.CanvasFrame;

import javax.swing.WindowConstants;

/**
 * 逐帧读取视频，延时播放
 *
 * @author chenmeng
 */
public class JavaCVVideoPlayer {

    /**
     * 使用 JavaCV 中的 FFmpegFrameGrabber 逐帧读取视频文件并显示到窗口中
     */
    public static void main(String[] args) {
        // 指定视频文件的路径
        String videoFilePath = VideoConstant.VIDEO_FILE_PATH2;

        // 创建视频帧抓取器
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath);
        
        try {
            // 启动抓取器
            grabber.start();
            
            // 创建一个窗口来显示视频帧
            CanvasFrame canvas = new CanvasFrame("JavaCV Video Player");
            canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            
            Frame frame;
            // 按帧抓取视频，并在窗口中显示
            while ((frame = grabber.grab()) != null) {
                // 检查显示窗口是否仍处于打开状态。
                if (!canvas.isVisible()) {
                    break;
                }
                // 将当前帧显示在窗口中，实现视频的逐帧播放。
                canvas.showImage(frame);
                
                // 控制播放速度：根据视频的帧率计算每帧之间的延迟（单位为毫秒）。
                // 例如，帧率为 25 帧每秒，则每帧显示约 40 毫秒（1000/25）。这可以使播放速度接近原视频的实际速度，而不是瞬间显示所有帧。
                Thread.sleep((long)(1000 / grabber.getFrameRate()));
            }
            
            // 释放资源
            grabber.stop();
            canvas.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
