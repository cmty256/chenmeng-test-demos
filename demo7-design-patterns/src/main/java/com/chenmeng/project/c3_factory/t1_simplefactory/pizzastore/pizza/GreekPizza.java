package com.chenmeng.project.c3_factory.t1_simplefactory.pizzastore.pizza;

/**
 * @author chenmeng
 */
public class GreekPizza extends Pizza {

    @Override
    public void prepare() {
        System.out.println(" 给希腊披萨 准备原材料 ");
    }

}
