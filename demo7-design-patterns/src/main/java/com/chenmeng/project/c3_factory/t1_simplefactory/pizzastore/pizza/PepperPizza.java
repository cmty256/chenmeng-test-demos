package com.chenmeng.project.c3_factory.t1_simplefactory.pizzastore.pizza;

/**
 * @author chenmeng
 */
public class PepperPizza extends Pizza {

    @Override
    public void prepare() {
        System.out.println(" 给胡椒披萨准备原材料 ");
    }

}
