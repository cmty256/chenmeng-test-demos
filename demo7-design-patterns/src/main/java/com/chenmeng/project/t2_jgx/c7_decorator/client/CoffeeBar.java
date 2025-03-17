package com.chenmeng.project.t2_jgx.c7_decorator.client;

import com.chenmeng.project.t2_jgx.c7_decorator.base.AbstractDrink;
import com.chenmeng.project.t2_jgx.c7_decorator.decorator.Chocolate;
import com.chenmeng.project.t2_jgx.c7_decorator.decorator.Milk;
import com.chenmeng.project.t2_jgx.c7_decorator.obj.DeCaf;
import com.chenmeng.project.t2_jgx.c7_decorator.obj.LongBlack;

/**
 * 装饰者模式的客户端测试
 *
 * @author chenmeng
 */
public class CoffeeBar {

	public static void main(String[] args) {
		// 测试案例1：LongBlack + 牛奶 + 巧克力
		testLongBlackWithAdditions();

		System.out.println("======================================================");

		// 测试案例2：无因咖啡 + 牛奶
		testDecafWithMilk();
	}

	// 初始订单（LongBlack），费用 = 5.0
	// 初始订单（LongBlack），描述 = LongBlack
	// 添加牛奶后，费用 = 7.0
	// 添加牛奶后，描述 = 牛奶+2.0 和 LongBlack
	// 添加巧克力后，费用 = 10.0
	// 添加巧克力后，描述 = 巧克力+3.0 和 牛奶+2.0 和 LongBlack
	// 再添加巧克力后，费用 = 13.0
	// 再添加巧克力后，描述 = 巧克力+3.0 和 巧克力+3.0 和 牛奶+2.0 和 LongBlack
	// ======================================================
	// 初始订单（无因咖啡），费用 = 1.0
	// 初始订单（无因咖啡），描述 = 无因咖啡
	// 添加牛奶后，费用 = 3.0
	// 添加牛奶后，描述 = 牛奶+2.0 和 无因咖啡

	/**
	 * 测试基础咖啡添加多种调料
	 */
	private static void testLongBlackWithAdditions() {
		AbstractDrink order = new LongBlack();
		printOrderStatus(order, "初始订单（LongBlack）");

		// 添加牛奶
		order = new Milk(order);
		printOrderStatus(order, "添加牛奶后");

		// 添加第一份巧克力
		order = new Chocolate(order);
		printOrderStatus(order, "添加巧克力后");

		// 再添加一份巧克力
		order = new Chocolate(order);
		printOrderStatus(order, "再添加巧克力后");
	}

	/**
	 * 测试无因咖啡添加牛奶
	 */
	private static void testDecafWithMilk() {
		AbstractDrink order = new DeCaf();
		printOrderStatus(order, "初始订单（无因咖啡）");

		// 添加牛奶
		order = new Milk(order);
		printOrderStatus(order, "添加牛奶后");
	}

	/**
	 * 打印当前订单状态
	 */
	private static void printOrderStatus(AbstractDrink drink, String step) {
		System.out.println(step + "，费用 = " + drink.cost());
		System.out.println(step + "，描述 = " + drink.getDes());
	}
}