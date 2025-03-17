package com.chenmeng.project.t2_jgx.c9_facade;

/**
 * @author chenmeng
 */
public class Client {

	public static void main(String[] args) {
		// 创建外观对象，简化客户端操作
		HomeTheaterFacade facade = new HomeTheaterFacade();

		System.out.println("准备观影...");
		facade.ready();

		System.out.println("\n开始播放电影...");
		facade.play();

		System.out.println("\n暂停观影...");
		facade.pause();

		System.out.println("\n结束观影...");
		facade.end();
	}

}