package com.chenmeng.project.c2_factory.t2_factorymethod.pizzastore.pizza;

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
