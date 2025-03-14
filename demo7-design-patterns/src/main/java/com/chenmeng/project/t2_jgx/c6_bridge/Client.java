package com.chenmeng.project.t2_jgx.c6_bridge;

/**
 * 客户端（桥接模式的调用者）
 *
 * @author chenmeng
 */
public class Client {

	public static void main(String[] args) {
		
		// 获取折叠式手机 (样式 + 品牌 )
		
		Phone phone1 = new FoldedPhone(new XiaoMi());

		phone1.open();
		phone1.call();
		phone1.close();
		
		System.out.println("==============");
		
		Phone phone2 = new FoldedPhone(new Vivo());

		phone2.open();
		phone2.call();
		phone2.close();
		
		System.out.println("=======================");

		// 获取直立式手机 (样式 + 品牌 )

		UpRightPhone phone3 = new UpRightPhone(new XiaoMi());
		
		phone3.open();
		phone3.call();
		phone3.close();
		
		System.out.println("==============");
		
		UpRightPhone phone4 = new UpRightPhone(new Vivo());
		
		phone4.open();
		phone4.call();
		phone4.close();
	}

	//  小米手机开机
	//  折叠样式手机
	//  小米手机打电话
	//  折叠样式手机
	//  小米手机关机
	//  折叠样式手机
	// ==============
	//  Vivo手机开机
	//  折叠样式手机
	//  Vivo手机打电话
	//  折叠样式手机
	//  Vivo手机关机
	//  折叠样式手机
	// =======================
	//  小米手机开机
	//  直立样式手机
	//  小米手机打电话
	//  直立样式手机
	//  小米手机关机
	//  直立样式手机
	// ==============
	//  Vivo手机开机
	//  直立样式手机
	//  Vivo手机打电话
	//  直立样式手机
	//  Vivo手机关机
	//  直立样式手机
}
