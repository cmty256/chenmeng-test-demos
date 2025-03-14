package com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.order;

import com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.pizza.CheesePizza;
import com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.pizza.GreekPizza;
import com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.pizza.PepperPizza;
import com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.pizza.Pizza;

// 简单工厂类
public class SimpleFactory {

    // 根据 orderType 返回对应的 Pizza 对象
    public Pizza createPizza(String orderType) {

        Pizza pizza = null;

        System.out.println("使用简单工厂模式");
        if ("greek".equals(orderType)) {
            pizza = new GreekPizza();
            pizza.setName(" 希腊披萨 ");
        } else if ("cheese".equals(orderType)) {
            pizza = new CheesePizza();
            pizza.setName(" 奶酪披萨 ");
        } else if ("pepper".equals(orderType)) {
            pizza = new PepperPizza();
            pizza.setName("胡椒披萨");
        }

        return pizza;
    }

    // 简单工厂模式 也叫 静态工厂模式
    public static Pizza createPizza2(String orderType) {

        Pizza pizza = null;

        System.out.println("使用简单工厂模式2");
        if ("greek".equals(orderType)) {
            pizza = new GreekPizza();
            pizza.setName(" 希腊披萨 ");
        } else if ("cheese".equals(orderType)) {
            pizza = new CheesePizza();
            pizza.setName(" 奶酪披萨 ");
        } else if ("pepper".equals(orderType)) {
            pizza = new PepperPizza();
            pizza.setName("胡椒披萨");
        }

        return pizza;
    }

}
