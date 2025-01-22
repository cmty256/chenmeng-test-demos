package com.chenmeng.project.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author chenmeng
 */
@Slf4j
public class OpencvMain {

    // static String imagePath = "D:\\codes\\ok\\chenmeng-test-demos\\demo10-third-api\\src\\main\\resources\\image\\健身房.png";
    static String imagePath = "C:\\Users\\26913\\Pictures\\Camera Roll\\1736323134938.jpg";
    // static String imagePath = "C:\\Users\\26913\\Pictures\\Camera Roll\\classroom.jpg";
    // static String imagePath = "C:\\Users\\26913\\Pictures\\Camera Roll\\1737528153728.jpg";


    public static void main(String[] args) throws UnsupportedEncodingException {
        // 解码图片路径并创建图片文件对象
        File imageFile = new File(URLDecoder.decode(imagePath, StandardCharsets.UTF_8));

        // 获取图片清晰度分数
        log.info("=================================== 清晰度检测 ===================================");
        double clarityScore = getImageClarity(imageFile);
        System.out.println("图片清晰度分数：" + clarityScore);
        if (clarityScore <= 30.0) {
            System.out.println("图片模糊");
        }
        // 获取图片遮挡比例
        log.info("=================================== 遮挡检测 ===================================");
        double obstructionScore = checkImageObstruction(imageFile);
        System.out.println("图片遮挡比例：" + obstructionScore);
        if (obstructionScore > 0.50) {
            System.out.println("图片遮挡");
        }
    }

    /**
     * 检测图片的遮挡程度
     * @param imageFile 图片文件
     * @return 遮挡比例
     */
    @SneakyThrows
    public static double checkImageObstruction(File imageFile) {
        // 读取图片文件并转换为Mat对象
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        Mat imageMat = QualityEstimateAction.toMat(bufferedImage);
        // 初始化遮挡检测工具
        LensBlockDetect lensBlockDetector = new LensBlockDetect();
        // 返回遮挡检测结果
        return lensBlockDetector.detect(imageMat);
    }

    /**
     * 计算图片的清晰度
     * @param imageFile 图片文件
     * @return 清晰度分数
     */
    public static double getImageClarity(File imageFile) {
        // 读取图片并转换为Mat对象
        Mat imageMat = opencv_imgcodecs.imread(imageFile.getAbsolutePath());

        // 将图片转换为灰度图
        Mat grayImage = new Mat();
        opencv_imgproc.cvtColor(imageMat, grayImage, opencv_imgproc.COLOR_BGR2GRAY);

        // 使用双边滤波器去噪
        Mat denoisedImage = new Mat();
        opencv_imgproc.bilateralFilter(grayImage, denoisedImage, 5, 5, 30);

        // 计算拉普拉斯变换后的图像，用于检测清晰度
        Mat laplacianImage = new Mat();
        opencv_imgproc.Laplacian(denoisedImage, laplacianImage, opencv_core.CV_64F);

        // 计算图像的标准差，用于衡量清晰度
        Mat stdDev = new Mat();
        opencv_core.meanStdDev(laplacianImage, new Mat(), stdDev);

        // 返回标准差的值作为清晰度分数
        return stdDev.createIndexer().getDouble();
    }
}
