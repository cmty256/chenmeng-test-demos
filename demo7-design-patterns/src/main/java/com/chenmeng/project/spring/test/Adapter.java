package com.chenmeng.project.spring.test;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * Spring 适配器模式示例
 *
 * @author chenmeng
 */
public class Adapter {

    public static void main(String[] args) {
        // 调度类：DispatcherServlet
		// 适配接口：HandlerAdapter
		new DispatcherServlet();
	}

}
