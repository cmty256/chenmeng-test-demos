package com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.pizza;

/**
 * @author chenmeng
 */
public class BJCheesePizza extends Pizza {

    @Override
    public void prepare() {
        setName("北京的奶酪pizza");
        System.out.println(" 北京的奶酪pizza 准备原材料");
    }

}
