package com.chenmeng.project.c6_adapter.objectadapter;

/**
 * 对象适配器模式
 *
 * @author chenmeng
 */
public class Client {

	public static void main(String[] args) {
		System.out.println(" === 对象适配器模式 ====");
		Phone phone = new Phone();
		phone.charging(new VoltageAdapter(new Voltage220V()));
	}

}
