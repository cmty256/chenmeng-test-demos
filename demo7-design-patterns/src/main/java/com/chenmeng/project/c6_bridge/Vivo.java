package com.chenmeng.project.c6_bridge;

/**
 * Brand品牌类（行为的具体实现类）
 *
 * @author chenmeng
 */
public class Vivo implements Brand {

    @Override
    public void open() {
        System.out.println(" Vivo手机开机 ");
    }

    @Override
    public void close() {
        System.out.println(" Vivo手机关机 ");
    }

    @Override
    public void call() {
        System.out.println(" Vivo手机打电话 ");
    }

}
