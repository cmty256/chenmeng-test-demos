package com.chenmeng.project.t2_jgx.c5_adapter.classadapter;

/**
 * 类适配器模式
 *
 * @author chenmeng
 */
public class Client {

    public static void main(String[] args) {
        System.out.println(" === 类适配器模式 ====");
        Phone phone = new Phone();
        phone.charging(new VoltageAdapter());
    }

}
