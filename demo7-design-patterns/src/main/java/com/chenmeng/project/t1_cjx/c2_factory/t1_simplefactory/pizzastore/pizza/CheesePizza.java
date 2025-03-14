package com.chenmeng.project.t1_cjx.c2_factory.t1_simplefactory.pizzastore.pizza;

/**
 * @author chenmeng
 */
public class CheesePizza extends Pizza {

    @Override
    public void prepare() {
        System.out.println(" 给制作奶酪披萨 准备原材料 ");
    }

}

