package com.chenmeng.project.c3_factory.t3_absfactory.pizzastore.pizza;

/**
 * @author chenmeng
 */
public class BJPepperPizza extends Pizza {

    @Override
    public void prepare() {
        setName("北京的胡椒pizza");
        System.out.println(" 北京的胡椒pizza 准备原材料");
    }
}
