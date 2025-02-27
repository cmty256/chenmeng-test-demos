package com.chenmeng.project;

import org.dromara.pdf.pdfbox.core.base.Document;
import org.dromara.pdf.pdfbox.core.base.Page;
import org.dromara.pdf.pdfbox.core.base.PageSize;
import org.dromara.pdf.pdfbox.core.component.Textarea;
import org.dromara.pdf.pdfbox.handler.PdfHandler;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

/**
 * pdf测试
 *
 * @author chenmeng
 */
public class PdfTest {

    String path1 = "src/main/resources/pdf/test1.pdf";
    String path2 = "src/main/resources/pdf/test2.pdf";
    String path3 = "src/main/resources/pdf/test3.pdf";
    String path4 = "src/main/resources/pdf/test4.pdf";

    @Test
    void createTest() {
        // 创建文档
        Document document = PdfHandler.getDocumentHandler().create();

        // 创建页面，并自定义尺寸
        Page page = new Page(document, PageSize.A4);
        // 设置上下左右边距
        page.setMargin(10F);
        // 设置背景颜色
        page.setBackgroundColor(Color.WHITE);

        // 创建文本
        Textarea textarea = new Textarea(page);
        // 设置坐标（左下角为原点）
        textarea.setBeginX(20F);
        textarea.setBeginY(800F);
        // 设置文本内容
        textarea.setText("12345678");
        // 渲染
        textarea.render();

        // 添加页面
        document.appendPage(page);
        // 保存文档
        document.save(path1);
        // 关闭文档
        document.close();
    }

    @Test
    void createTest2() {
        // 创建文档
        Document document = PdfHandler.getDocumentHandler().create();

        // 创建页面，并自定义尺寸
        Page page = new Page(document, PageSize.A4);
        // 设置上下左右边距
        page.setMargin(10F);
        // 设置背景颜色
        page.setBackgroundColor(Color.WHITE);

        // 添加页面
        document.appendPage(page);
        // 保存文档
        document.save(path2);
        // 关闭文档
        document.close();
    }

    /**
     * 读取并编辑测试（不能对原有内容编辑）
     */
    @Test
    void readTest() {
        // 读取文档
        Document document = PdfHandler.getDocumentHandler().load(path3);

        // 获取第一页
        Page page = document.getPage(0);

        // 创建文本
        Textarea textarea = new Textarea(page);
        // 设置坐标
        textarea.setBeginX(20F);
        textarea.setBeginY(800F);
        // 设置文本内容
        // textarea.setText("超兽武装12345");
        textarea.setText("火影忍者12333");
        // 渲染
        textarea.render();

        // 保存文档
        document.save(path4);
        // 关闭文档
        document.close();
    }
}
