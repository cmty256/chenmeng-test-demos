package com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.order;

import com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.pizza.LDCheesePizza;
import com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.pizza.LDPepperPizza;
import com.chenmeng.project.t1_cjx.c2_factory.t3_absfactory.pizzastore.pizza.Pizza;

public class LDFactory implements AbsFactory {

	@Override
	public Pizza createPizza(String orderType) {
		System.out.println("~使用的是抽象工厂模式~");
		Pizza pizza = null;
		if ("cheese".equals(orderType)) {
			pizza = new LDCheesePizza();
		} else if ("pepper".equals(orderType)) {
			pizza = new LDPepperPizza();
		}
		return pizza;
	}

}

