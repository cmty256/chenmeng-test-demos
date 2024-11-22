package com.chenmeng.project.c6_adapter.interfaceadapter;

/**
 * 接口适配器模式
 *
 * @author chenmeng
 */
public class Client {
	public static void main(String[] args) {
		
		AbsAdapter absAdapter = new AbsAdapter() {
			// 只需要去覆盖我们 需要使用的 接口方法
			@Override
			public void m1() {
				System.out.println("使用了m1的方法");
			}
		};
		
		absAdapter.m1();
	}

}