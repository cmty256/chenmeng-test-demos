package com.chenmeng.project.frameExtraction;

import com.chenmeng.common.constants.enums.VideoConstant;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

/**
 * @author chenmeng
 */
public class VideoFrameExtractor {

    public static void main(String[] args) {
        VideoFrameExtractor videoFrameExtractor = new VideoFrameExtractor();
        videoFrameExtractor.randomFrameExtractor();
    }

    /**
     * 循环遍历视频帧
     */
    public void loopThroughTheVideoFrame() {
        // 指定视频文件路径（请根据实际情况修改路径）
        String videoFilePath = VideoConstant.VIDEO_FILE_PATH2;
        // 指定输出图片保存的文件夹
        String outputDirPath = VideoConstant.OUTPUT_DIR_PATH;
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        
        // 创建视频帧抓取器
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath);
        // 创建转换器，将 Frame 转为 BufferedImage
        Java2DFrameConverter converter = new Java2DFrameConverter();
        
        try {
            // 启动抓取器，打开视频文件
            grabber.start();
            
            int frameNumber = 0;
            Frame frame;
            // 循环读取视频帧
            while ((frame = grabber.grab()) != null) {
                // 使用转换器将 Frame 转为 BufferedImage
                BufferedImage image = converter.convert(frame);
                if (image != null) {
                    // 构造图片输出路径，例如 frame_0.png, frame_1.png 等
                    String outputFileName = outputDirPath + File.separator + "frame_" + frameNumber + ".png";
                    File outputFile = new File(outputFileName);
                    // 保存图片（png 格式也可换成 jpeg，根据需要修改）
                    ImageIO.write(image, "png", outputFile);
                    System.out.println("已保存帧：" + outputFileName);
                    frameNumber++;
                }
                if (frameNumber >= 10) {
                    break;
                }
            }
            
            // 释放资源
            grabber.stop();
            System.out.println("视频抽帧完成，共保存 " + frameNumber + " 帧图片。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机抽取视频帧
     */
    public void randomFrameExtractor() {
        // 指定视频文件路径（请根据实际情况修改路径）
        String videoFilePath = VideoConstant.VIDEO_FILE_PATH1;
        // 指定输出图片保存的文件夹
        String outputDirPath = VideoConstant.OUTPUT_DIR_PATH;
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // 创建视频帧抓取器
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath);
        // 创建转换器，将 Frame 转为 BufferedImage
        Java2DFrameConverter converter = new Java2DFrameConverter();

        try {
            // 启动抓取器
            grabber.start();

            // 获取视频总帧数（包含了音频帧或其他非图像帧）
            int totalFrames = grabber.getLengthInFrames();
            System.out.println("视频总帧数：" + totalFrames);

            // 设定要抽取的帧数，比如随机抽取 10 帧
            int numFramesToExtract = 10;
            // 使用 Set 存储随机帧索引，保证不重复
            Set<Integer> randomFrameIndices = new HashSet<>();
            Random random = new Random();
            while (randomFrameIndices.size() < numFramesToExtract) {
                // 随机生成 0 到 totalFrames-1 的数
                int index = random.nextInt(totalFrames);
                randomFrameIndices.add(index);
            }
            // 将随机帧索引排序，方便顺序遍历视频时比对
            List<Integer> sortedIndices = new ArrayList<>(randomFrameIndices);
            Collections.sort(sortedIndices);
            System.out.println("随机抽取的帧索引：" + sortedIndices);

            int currentFrameIndex = 0;
            int targetIndexPointer = 0;
            Frame frame;
            // 遍历所有帧，直到抽取完所有目标帧或视频结束
            // 使用 grabImage() 只抓取视频图像帧
            while ((frame = grabber.grabImage()) != null && targetIndexPointer < sortedIndices.size()) {
                // 如果当前帧索引与目标帧索引匹配，则保存该帧
                if (currentFrameIndex == sortedIndices.get(targetIndexPointer)) {
                    BufferedImage image = converter.convert(frame);
                    if (image != null) {
                        String outputFileName = outputDirPath + File.separator + "frame_" + currentFrameIndex + ".jpg";
                        ImageIO.write(image, "jpg", new File(outputFileName));
                        System.out.println("保存帧：" + currentFrameIndex + " 到文件：" + outputFileName);
                    }
                    // 移动到下一个目标帧
                    targetIndexPointer++;
                }
                currentFrameIndex++;
            }

            // 释放资源
            grabber.stop();
            System.out.println("随机抽帧完成，共保存 " + randomFrameIndices.size() + " 帧图片。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
