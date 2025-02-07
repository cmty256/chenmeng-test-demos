package com.chenmeng.project.qualityEstimate;

import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;

/**
 * 抽象类 QualityEstimateAction
 * 提供用于质量评估相关操作的工具方法，例如将 BufferedImage 转换为 OpenCV 的 Mat 格式。
 *
 * @author chenmeng
 */
public abstract class QualityEstimateAction {

    // 将 OpenCV 框架的 Frame 转换为 Mat 格式的转换器实例
    private static final OpenCVFrameConverter.ToMat MAT_CONV = new OpenCVFrameConverter.ToMat();
    // 将 Java2D 的 BufferedImage 转换为 Frame 格式的转换器实例
    private static final Java2DFrameConverter BI_CONV = new Java2DFrameConverter();

    /**
     * 将 BufferedImage 对象转换为 OpenCV 的 Mat 格式。
     *
     * @param src BufferedImage 类型的图像源
     * @return 转换后的 Mat 类型对象
     * <p>
     * 使用流程：
     * 1. 首先通过 Java2DFrameConverter 将 BufferedImage 转换为 Frame 格式。
     * 2. 再通过 OpenCVFrameConverter 将 Frame 转换为 Mat 格式。
     * 3. 返回一个克隆的 Mat 对象，确保线程安全和独立性。
     */
    public synchronized static Mat toMat(BufferedImage src) {
        return MAT_CONV.convertToMat(BI_CONV.convert(src)).clone();
    }

}
