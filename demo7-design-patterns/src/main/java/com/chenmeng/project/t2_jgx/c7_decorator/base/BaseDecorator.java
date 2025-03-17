package com.chenmeng.project.t2_jgx.c7_decorator.base;

/**
 * 装饰器基类，用于动态地为Drink对象添加功能
 *
 * @author chenmeng
 */
public class BaseDecorator extends AbstractDrink {

    //  Decorator类是装饰者模式中的核心抽象类，它通过组合的方式将装饰功能与被装饰对象解耦，
    //  使得可以在不修改原有类的情况下动态地扩展功能。这种设计模式非常适合需要灵活扩展功能的场景。

    /**
     * 被装饰的对象（即需要增强功能的具体饮品）
     */
    private final AbstractDrink obj;

    public BaseDecorator(AbstractDrink obj) { // 组合
        this.obj = obj;
    }

    @Override
    public float cost() {
        // getPrice 自己价格
        return super.getPrice() + obj.cost();
    }

    @Override
    public String getDes() {
        // 自身描述 + 被装饰对象描述，obj.getDes() 输出被装饰者的信息
        return des + "+" + getPrice() + " 和 " + obj.getDes();
    }
}
