package com.chenmeng.project.c2_factory.t3_absfactory.pizzastore.pizza;

/**
 * @author chenmeng
 */
public class LDPepperPizza extends Pizza {
    @Override
    public void prepare() {
        setName("伦敦的胡椒pizza");
        System.out.println(" 伦敦的胡椒pizza 准备原材料");
    }
}

