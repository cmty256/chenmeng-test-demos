package com.chenmeng.project.t2_jgx.c7_decorator.obj;

import com.chenmeng.project.t2_jgx.c7_decorator.base.BaseCoffee;

/**
 * 无因咖啡（具体饮品） -- 被装饰对象
 *
 * @author chenmeng
 */
public class DeCaf extends BaseCoffee {

    public DeCaf() {
        setDes("无因咖啡");
        setPrice(1.0f);
    }
}