package com.chenmeng.project.frameExtraction;

import com.chenmeng.common.constants.enums.VideoConstant;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * 实时抽帧
 *
 * @author chenmeng
 */
public class LiveStreamFrameExtractor {

    private static final Logger log = LoggerFactory.getLogger(LiveStreamFrameExtractor.class);

    /**
     * javacv实时抓取视频帧
     */
    public static void main(String[] args) {
        String streamUrl = VideoConstant.RTSP_URL_2;
        String outputDirPath = VideoConstant.FRAME_FILE_PREFIX;
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // 使用 try-with-resources 自动管理资源
        try (
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(streamUrl);
                Java2DFrameConverter converter = new Java2DFrameConverter()
        ) {
            // 启动抓取器，开始实时视频读取
            grabber.start();
            int frameCount = 0;
            Frame frame;
            // 无限循环抓取实时视频帧
            while ((frame = grabber.grabImage()) != null) {
                // 可根据需要添加实时显示、处理等操作
                BufferedImage image = converter.convert(frame);
                if (image != null) {
                    // 构造图片输出路径，例如 live_frame_0001.jpg
                    String outputFileName = outputDirPath + File.separator + String.format("live_frame_%04d.jpg", frameCount);
                    File outputFile = new File(outputFileName);

                    ImageIO.write(image, "jpg", outputFile);
                    System.out.println("保存帧：" + frameCount + " 到文件：" + outputFileName);
                }
                frameCount++;
                // 根据抓取帧率或其他需求设置适当延时，避免过快抽帧
                TimeUnit.MILLISECONDS.sleep(50);

                if (frameCount > 5) {
                    break;
                }
            }
            System.out.println("抓取结束，共抓取 " + frameCount + " 帧");
            // 停止抓取器
            grabber.stop();
        } catch (Exception e) {
            log.error("抓取视频帧出错：", e);
        }
    }
}
