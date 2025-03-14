package com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.order;

import com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.pizza.CheesePizza;
import com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.pizza.GreekPizza;
import com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.pizza.PepperPizza;
import com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @author chengmeng
 */
public class OrderPizza {

    // 构造器（传统方式）
    public OrderPizza() {
        Pizza pizza = null;
        String orderType; // 订购披萨的类型
        do {
            orderType = getType();
            if ("greek".equals(orderType)) {
                pizza = new GreekPizza();
                pizza.setName(" 希腊披萨 ");
            } else if ("cheese".equals(orderType)) {
                pizza = new CheesePizza();
                pizza.setName(" 奶酪披萨 ");
            } else if ("pepper".equals(orderType)) {
                pizza = new PepperPizza();
                pizza.setName("胡椒披萨");
            } else {
                break;
            }
            // 输出pizza 制作过程
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();

        } while (true);
    }

    // 定义一个简单工厂对象
    SimpleFactory simpleFactory;
    Pizza pizza = null;

    // 构造器
    public OrderPizza(SimpleFactory simpleFactory) {
        setFactory(simpleFactory);
    }

    public void setFactory(SimpleFactory simpleFactory) {
        // 用户输入的
        String orderType = "";
        // 设置简单工厂对象
        this.simpleFactory = simpleFactory;

        do {
            orderType = getType();
            pizza = Objects.requireNonNull(this.simpleFactory).createPizza(orderType);

            // 输出pizza
            if (pizza != null) {
                // 订购成功
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            } else {
                System.out.println(" 订购披萨失败 ");
                break;
            }
        } while (true);
    }

    // 写一个方法，可以获取客户希望订购的披萨种类
    private String getType() {
        try {
            BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza 种类:");
            return strin.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
