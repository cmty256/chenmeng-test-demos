package com.chenmeng.project.t1_cjx.c4_builder.improve;

public class Client {
	public static void main(String[] args) {
		
		// 1、指定产品的【具体建造者】 -- 盖普通房子
		CommonHouse commonHouse = new CommonHouse();
		// 2、将具体建造者传给【指挥者】 -- 准备创建房子的指挥者
		HouseDirector houseDirector = new HouseDirector(commonHouse);
		// 3、指挥者调用方法执行构建流程，最终返回产品 -- 完成盖房子，返回产品(普通房子)
		House house = houseDirector.constructHouse();
		
		// System.out.println("输出流程");
		
		System.out.println("--------------------------");

		// 盖高楼
		HighBuilding highBuilding = new HighBuilding();
		// 重置建造者
		houseDirector.setHouseBuilder(highBuilding);
		// 完成盖房子，返回产品(高楼)
		houseDirector.constructHouse();
	}
}