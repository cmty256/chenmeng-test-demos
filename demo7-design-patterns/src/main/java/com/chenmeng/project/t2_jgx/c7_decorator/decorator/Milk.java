package com.chenmeng.project.t2_jgx.c7_decorator.decorator;

import com.chenmeng.project.t2_jgx.c7_decorator.base.BaseDecorator;
import com.chenmeng.project.t2_jgx.c7_decorator.base.AbstractDrink;

/**
 * 牛奶装饰器类
 *
 * @author chenmeng
 */
public class Milk extends BaseDecorator {

	public Milk(AbstractDrink obj) {
		super(obj);
		// 设置装饰描述
		setDes("牛奶");
		// 设置装饰价格
		setPrice(2.0f); 
	}

}