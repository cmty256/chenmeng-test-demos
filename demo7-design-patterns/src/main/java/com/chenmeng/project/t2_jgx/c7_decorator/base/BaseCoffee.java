package com.chenmeng.project.t2_jgx.c7_decorator.base;

/**
 * @author chenmeng
 */
public class BaseCoffee extends AbstractDrink {

	@Override
	public float cost() {
		return super.getPrice();
	}
}
