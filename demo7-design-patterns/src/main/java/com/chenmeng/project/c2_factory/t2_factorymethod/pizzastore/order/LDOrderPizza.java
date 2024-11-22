package com.chenmeng.project.c2_factory.t2_factorymethod.pizzastore.order;

import com.chenmeng.project.c2_factory.t2_factorymethod.pizzastore.pizza.LDCheesePizza;
import com.chenmeng.project.c2_factory.t2_factorymethod.pizzastore.pizza.LDPepperPizza;
import com.chenmeng.project.c2_factory.t2_factorymethod.pizzastore.pizza.Pizza;

public class LDOrderPizza extends OrderPizza {

    @Override
    Pizza createPizza(String orderType) {

        Pizza pizza = null;
        if ("cheese".equals(orderType)) {
            pizza = new LDCheesePizza();
        } else if ("pepper".equals(orderType)) {
            pizza = new LDPepperPizza();
        }
        return pizza;
    }

}
