package com.chenmeng.project.c2_factory.t1_simplefactory.pizzastore.order;

/**
 * 相当于一个客户端，发出订购
 *
 * @author chenmeng
 */
public class PizzaStore {

    public static void main(String[] args) {

        // new OrderPizza();

        // 使用简单工厂模式
        // new OrderPizza(new SimpleFactory());
        // System.out.println("~~退出程序~~");

        new OrderPizza2();
    }

}
