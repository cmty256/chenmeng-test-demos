package com.chenmeng.project.t2_jgx.c7_decorator.obj;

import com.chenmeng.project.t2_jgx.c7_decorator.base.BaseCoffee;

/**
 * 美式咖啡（咖啡种类，具体饮品） -- 被装饰对象
 *
 * @author chenmeng
 */
public class LongBlack extends BaseCoffee {

	public LongBlack() {
		setDes("LongBlack");
		setPrice(5.0f);
	}
}
