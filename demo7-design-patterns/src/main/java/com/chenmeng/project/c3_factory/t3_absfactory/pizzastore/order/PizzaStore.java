package com.chenmeng.project.c3_factory.t3_absfactory.pizzastore.order;

public class PizzaStore {

    public static void main(String[] args) {
        // new OrderPizza(new BJFactory());
        new OrderPizza(new LDFactory());
    }

}
