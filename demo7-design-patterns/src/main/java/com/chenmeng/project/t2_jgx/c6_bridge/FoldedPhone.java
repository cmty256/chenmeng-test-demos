package com.chenmeng.project.t2_jgx.c6_bridge;

/**
 * 折叠式手机类，继承 抽象类 Phone（抽象类的子类）
 *
 * @author chenmeng
 */
public class FoldedPhone extends Phone {

    // 构造器
    public FoldedPhone(Brand brand) {
        super(brand);
    }

    @Override
    public void open() {
        super.open();
        System.out.println(" 折叠样式手机 ");
    }

    @Override
    public void close() {
        super.close();
        System.out.println(" 折叠样式手机 ");
    }

    @Override
    public void call() {
        super.call();
        System.out.println(" 折叠样式手机 ");
    }
}
