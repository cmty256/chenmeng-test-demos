package com.chenmeng.project.t1_cjx.c2_factory.t2_factorymethod.pizzastore.pizza;

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

