package com.chenmeng.project.qualityEstimate;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * @author chenmeng
 */
public class LensBlockDetect {

    /**
     * 检测图像中的遮挡程度
     *
     * @param srcImage 输入的Mat格式图片
     * @return 遮挡率（0到1之间，越大表示遮挡越严重）
     */
    public double detect(Mat srcImage) {
        // 第一步、将图像转换为灰度图像
        Mat grayImage = new Mat();
        // 如果原图是彩色图（不为1通道），则转化为灰度图
        if (srcImage.channels() != 1) {
            opencv_imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_RGB2GRAY);
        } else {
            // 如果已经是灰度图，直接克隆
            grayImage = srcImage.clone();
        }

        // 第二步、二值化处理
        Mat binImage = new Mat();
        // 使用otsu自动阈值法进行二值化，阈值分割得到黑白图像
        // 其中白色区域通常表示遮挡物
        opencv_imgproc.threshold(grayImage, binImage, 0, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);

        // 第三步、获取所有连通区域(原图像黑色连通区域)，及其面积
        Mat labelsImage = new Mat();
        Mat statsImage = new Mat();
        Mat centroids = new Mat();
        // 获取所有连通组件，并计算每个连通区域的属性（如面积、边界框等）
        int componentCount = opencv_imgproc.connectedComponentsWithStats(binImage, labelsImage, statsImage, centroids, 8, opencv_core.CV_32S);

        // 第四步、找到最大面积的连通区域
        int maxArea = 0;
        // 从1开始，因为0是背景
        for (int i = 1; i < componentCount; i++) {
            // 获取当前连通区域的面积（statsImage存储区域的相关信息，面积在第4列）
            int area = statsImage.ptr(i, 4).getInt();
            // 记录最大的面积
            maxArea = Math.max(maxArea, area);
        }

        // 第五步、计算遮挡率
        // 总像素数：图像的行数 * 列数
        double totalPixels = binImage.rows() * binImage.cols();
        // 遮挡率 = 最大连通区域面积 / 总像素数
        return maxArea / totalPixels;
    }
}
