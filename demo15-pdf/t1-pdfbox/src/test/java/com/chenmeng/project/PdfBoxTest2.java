package com.chenmeng.project;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

/**
 * @author chenmeng
 */
public class PdfBoxTest2 {

    @SneakyThrows
    @Test
    void testTemplate() {
        ClassPathResource resource = new ClassPathResource("/pdf/template/模板一.pdf");
        InputStream file = resource.getInputStream();

        PDDocument doc = PDDocument.load(file);
        PDPage page = doc.getPage(0);
        float pageWidth = page.getMediaBox().getWidth(); // 页面宽度
        float pageHeight = page.getMediaBox().getHeight(); // 页面高度

        // A4纸尺寸：pageWidth = 595.32 pageHeight = 841.92
        System.out.println("pageWidth = " + pageWidth);
        System.out.println("pageHeight = " + pageHeight);

        // 完全替换页面内容，会清空原有内容，适用于需要从头开始绘制的场景。
        // PDPageContentStream stream = new PDPageContentStream(doc, page);

        // 追加内容并且设置 true 表示允许压缩内容，减少文件大小，适用于需要保留原有页面内容的场景。
        PDPageContentStream stream =
                new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);

        // 确认坐标位置的方法
        // 1、利用 Adobe Acrobat 或其他工具测量页面坐标，并设置坐标位置。
        // 2、手动绘制辅助网格，调式出坐标位置。

        // 在PDF上绘制辅助网格并标注坐标（仅开发阶段使用）
        for (int i = 0; i < 595; i += 20) {
            // 绘制垂直网格线
            stream.drawLine(i, 0, i, 842);
            // 绘制水平网格线
            stream.drawLine(0, i, 595, i);

        }

        stream.close();
        doc.save("src/main/resources/pdf/normal2.pdf");
        doc.close();
    }

}
