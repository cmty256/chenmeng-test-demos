package com.chenmeng.project.c3_factory.t2_factorymethod.pizzastore.order;


import com.chenmeng.project.c3_factory.t2_factorymethod.pizzastore.pizza.BJCheesePizza;
import com.chenmeng.project.c3_factory.t2_factorymethod.pizzastore.pizza.BJPepperPizza;
import com.chenmeng.project.c3_factory.t2_factorymethod.pizzastore.pizza.Pizza;

public class BJOrderPizza extends OrderPizza {

    @Override
    Pizza createPizza(String orderType) {

        Pizza pizza = null;
        if (orderType.equals("cheese")) {
            pizza = new BJCheesePizza();
        } else if (orderType.equals("pepper")) {
            pizza = new BJPepperPizza();
        }
        
        return pizza;
    }

}
