package com.chenmeng.project.t2_jgx.c9_facade.dev;

import lombok.Getter;

/**
 * @author chenmeng
 */
public class Projector {

	@Getter
	private static Projector instance = new Projector();

	public void on() {
		System.out.println("Projector turned on");
	}

	public void off() {
		System.out.println("Projector turned off");
	}

	public void focus() {
		System.out.println("Projector focusing");
	}
}
