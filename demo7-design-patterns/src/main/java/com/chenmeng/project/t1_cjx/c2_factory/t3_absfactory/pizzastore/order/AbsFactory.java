package com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.order;

import com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.pizza.Pizza;

// 一个抽象工厂模式的抽象层(接口)
public interface AbsFactory {

    // 让下面的工厂子类来 具体实现
    public Pizza createPizza(String orderType);
}
