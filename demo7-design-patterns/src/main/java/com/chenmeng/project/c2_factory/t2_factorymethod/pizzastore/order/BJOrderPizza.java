package com.chenmeng.project.c2_factory.t2_factorymethod.pizzastore.order;


import com.chenmeng.project.c2_factory.t2_factorymethod.pizzastore.pizza.BJCheesePizza;
import com.chenmeng.project.c2_factory.t2_factorymethod.pizzastore.pizza.BJPepperPizza;
import com.chenmeng.project.c2_factory.t2_factorymethod.pizzastore.pizza.Pizza;

public class BJOrderPizza extends OrderPizza {

    @Override
    Pizza createPizza(String orderType) {

        Pizza pizza = null;
        if ("cheese".equals(orderType)) {
            pizza = new BJCheesePizza();
        } else if ("pepper".equals(orderType)) {
            pizza = new BJPepperPizza();
        }
        
        return pizza;
    }

}
