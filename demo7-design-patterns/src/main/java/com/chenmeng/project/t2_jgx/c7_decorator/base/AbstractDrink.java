package com.chenmeng.project.t2_jgx.c7_decorator.base;

import lombok.Data;

/**
 * 饮品抽象基类
 * 装饰者模式的基础类定义，提供了基本的属性和方法（如des、price、cost()等）
 *
 * @author chenmeng
 */
@Data
public abstract class AbstractDrink {

	// 描述
	public String des;

	// 单价
	private float price = 0.0f;

	// 计算费用的抽象方法
	// 子类来实现
	public abstract float cost();
}
