package com.chenmeng.project.lang;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author chenmeng
 */
@SpringBootTest
public class StringTest {

    @Test
    void test1() {
        String url = "cmty256.github.io/3f2b53d84c7eb722b38e59029e045504824dc5c4b2fc0afb6c38cd.png";

        // 获取第一个字符
        char firstChar = url.charAt(0);
        System.out.println("firstChar = " + firstChar); // firstChar = c

        // 获取前四个字符
        String prefix = url.substring(0, 4);
        System.out.println("prefix = " + prefix); // prefix = cmty

        String url2 = "http://127.0.0.1:8080/bucket-name/3f2b53d84c7eb722b38e59029e045504824dc5c4b2fc0afb6c38cd.png";

        // 截取文件名 fileName
        String fileName = url2.substring(url2.lastIndexOf("/") + 1);
        System.out.println("fileName = " + fileName); // fileName = 3f2b53d84c7eb722b38e59029e045504824dc5c4b2fc0afb6c38cd.png

        // 获取文件路径（除去ip端口前缀）
        try {
            URL realUrl = new URL(url2);
            System.out.println("realUrl.getPath() = " + realUrl.getPath()); // realUrl.getPath() = /bucket-name/3f2b53d84c7eb722b38e59029e045504824dc5c4b2fc0afb6c38cd.png
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        // 截取 url 后缀
        String suffix = url.substring(url.lastIndexOf(".") + 1);
        System.out.println("suffix = " + suffix); // suffix = png
    }
}
