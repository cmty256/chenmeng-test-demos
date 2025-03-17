package com.chenmeng.project.t2_jgx.c7_decorator.decorator;

import com.chenmeng.project.t2_jgx.c7_decorator.base.BaseDecorator;
import com.chenmeng.project.t2_jgx.c7_decorator.base.AbstractDrink;

/**
 * 豆浆装饰器类
 *
 * @author chenmeng
 */
public class Soy extends BaseDecorator {

    public Soy(AbstractDrink obj) {
        super(obj);
        setDes("豆浆");
        setPrice(1.5f);
    }

}