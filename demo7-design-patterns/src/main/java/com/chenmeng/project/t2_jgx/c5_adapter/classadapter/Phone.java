package com.chenmeng.project.t2_jgx.c5_adapter.classadapter;

/**
 * @author chenmeng
 */
public class Phone {

    // 充电
    public void charging(IVoltage5V iVoltage5V) {
        if (iVoltage5V.output5V() == 5) {
            System.out.println("电压为5V, 可以充电~~");
        } else if (iVoltage5V.output5V() > 5) {
            System.out.println("电压大于5V, 不能充电~~");
        }
    }
}
