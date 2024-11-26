package com.chenmeng.project.c6_bridge;

/**
 * 抽象类（桥接类）
 *
 * @author chenmeng
 */
public abstract class Phone {

    // 组合品牌
    private final Brand brand;

    // 构造器，接收 品牌接口类 作为参数
    public Phone(Brand brand) {
        super();
        this.brand = brand;
    }

    protected void open() {
        this.brand.open();
    }

    protected void close() {
        brand.close();
    }

    protected void call() {
        brand.call();
    }

}
