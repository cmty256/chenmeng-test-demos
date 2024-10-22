package com.chenmeng.project.c3_factory.t2_factorymethod.pizzastore.order;

import com.chenmeng.project.c3_factory.t2_factorymethod.pizzastore.pizza.LDCheesePizza;
import com.chenmeng.project.c3_factory.t2_factorymethod.pizzastore.pizza.LDPepperPizza;
import com.chenmeng.project.c3_factory.t2_factorymethod.pizzastore.pizza.Pizza;

public class LDOrderPizza extends OrderPizza {

    @Override
    Pizza createPizza(String orderType) {

        Pizza pizza = null;
        if (orderType.equals("cheese")) {
            pizza = new LDCheesePizza();
        } else if (orderType.equals("pepper")) {
            pizza = new LDPepperPizza();
        }
        return pizza;
    }

}
