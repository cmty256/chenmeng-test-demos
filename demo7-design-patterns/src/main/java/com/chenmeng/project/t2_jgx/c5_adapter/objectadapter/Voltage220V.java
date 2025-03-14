package com.chenmeng.project.t2_jgx.c5_adapter.objectadapter;

/**
 * 被适配的类
 *
 * @author chenmeng
 */
public class Voltage220V {

    // 输出220V的电压，不变
    public int output220V() {
        int src = 220;
        System.out.println("电压=" + src + "伏");
        return src;
    }
}
