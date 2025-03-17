package com.chenmeng.project.t2_jgx.c9_facade.dev;

import lombok.Getter;

/**
 * @author chenmeng
 */
public class Screen {

    @Getter
    private static Screen instance = new Screen();

    public void up() {
        System.out.println("Screen up ");
    }

    public void down() {
        System.out.println("Screen down ");
    }
}
