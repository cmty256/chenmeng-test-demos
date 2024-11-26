package com.chenmeng.project.c6_bridge;

/**
 * 直立式手机类，继承 抽象类 Phone（抽象类的子类）
 *
 * @author chenmeng
 */
public class UpRightPhone extends Phone {

    // 构造器
    public UpRightPhone(Brand brand) {
        super(brand);
    }

    @Override
    public void open() {
        super.open();
        System.out.println(" 直立样式手机 ");
    }

    @Override
    public void close() {
        super.close();
        System.out.println(" 直立样式手机 ");
    }

    @Override
    public void call() {
        super.call();
        System.out.println(" 直立样式手机 ");
    }
}
