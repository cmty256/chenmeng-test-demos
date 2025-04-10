package com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.order;

import com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.pizza.BJCheesePizza;
import com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.pizza.BJPepperPizza;
import com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.pizza.Pizza;

// 这是工厂子类
public class BJFactory implements AbsFactory {

    @Override
    public Pizza createPizza(String orderType) {
        System.out.println("~使用的是抽象工厂模式~");
        
        Pizza pizza = null;
        if ("cheese".equals(orderType)) {
            pizza = new BJCheesePizza();
        } else if ("pepper".equals(orderType)) {
            pizza = new BJPepperPizza();
        }
        return pizza;
    }

}
