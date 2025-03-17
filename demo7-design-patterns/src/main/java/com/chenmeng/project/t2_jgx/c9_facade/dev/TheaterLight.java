package com.chenmeng.project.t2_jgx.c9_facade.dev;

import lombok.Getter;

/**
 * @author chenmeng
 */
public class TheaterLight {

	@Getter
    private static TheaterLight instance = new TheaterLight();

	public void on() {
		System.out.println("Theater lights turned on");
	}

	public void off() {
		System.out.println("Theater lights turned off");
	}

	public void dim() {
		System.out.println("Theater lights dimmed to 10%");
	}

	public void bright() {
		System.out.println("Theater lights brightened to 100%");
	}
}
