package com.chenmeng.project.t2_jgx.c7_decorator.decorator;

import com.chenmeng.project.t2_jgx.c7_decorator.base.BaseDecorator;
import com.chenmeng.project.t2_jgx.c7_decorator.base.AbstractDrink;

/**
 * 调料
 * 具体的装饰器类，继承自Decorator，并实现特定的功能（如添加牛奶、豆浆或巧克力）。
 *
 * @author chenmeng
 */
public class Chocolate extends BaseDecorator {

    public Chocolate(AbstractDrink obj) {
        super(obj);
        // 设置装饰描述
        setDes("巧克力");
        // 设置装饰价格
        setPrice(3.0f);
    }

}
