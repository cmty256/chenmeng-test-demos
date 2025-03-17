package com.chenmeng.project.t2_jgx.c9_facade.dev;

import lombok.Getter;

/**
 * @author chenmeng
 */
public class Popcorn {
	@Getter
	private static Popcorn instance = new Popcorn();

	public void on() {
		System.out.println("Popcorn machine turned on");
	}

	public void off() {
		System.out.println("Popcorn machine turned off");
	}

	public void pop() {
		System.out.println("Popcorn is popping");
	}
}
