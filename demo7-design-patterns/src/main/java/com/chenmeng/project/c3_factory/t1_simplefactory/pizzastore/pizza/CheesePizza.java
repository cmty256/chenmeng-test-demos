package com.chenmeng.project.c3_factory.t1_simplefactory.pizzastore.pizza;

/**
 * @author chenmeng
 */
public class CheesePizza extends Pizza {

    @Override
    public void prepare() {
        System.out.println(" 给制作奶酪披萨 准备原材料 ");
    }

}

