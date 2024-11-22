package com.chenmeng.project.c6_adapter.classadapter;

/**
 * 适配器类
 *
 * @author chenmeng
 */
public class VoltageAdapter extends Voltage220V implements IVoltage5V {

	@Override
	public int output5V() {

		// 获取到 220V 电压
		int srcV = output220V();

		// 转成 5v
		return srcV / 44;
	}

}
