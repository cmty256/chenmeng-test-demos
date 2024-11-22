package com.chenmeng.project.c5_adapter.classadapter;

/**
 * 被适配的类
 *
 * @author chenmeng
 */
public class Voltage220V {

    // 输出220V的电压
    public int output220V() {
        int src = 220;
        System.out.println("电压=" + src + "伏");
        return src;
    }
}
