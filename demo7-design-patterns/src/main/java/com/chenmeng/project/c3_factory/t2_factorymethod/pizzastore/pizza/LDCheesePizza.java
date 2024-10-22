package com.chenmeng.project.c3_factory.t2_factorymethod.pizzastore.pizza;

/**
 * @author chenmeng
 */
public class LDCheesePizza extends Pizza {

    @Override
    public void prepare() {
        setName("伦敦的奶酪pizza");
        System.out.println(" 伦敦的奶酪pizza 准备原材料");
    }
}
