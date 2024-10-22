package com.chenmeng.project.c3_factory.t2_factorymethod.pizzastore.order;

public class PizzaStore {

    public static void main(String[] args) {
        String loc = "bj";
        if ("bj".equals(loc)) {
            // 创建北京口味的各种Pizza
            new BJOrderPizza();
        } else {
            // 创建伦敦口味的各种Pizza
            new LDOrderPizza();
        }
        
    }

}
