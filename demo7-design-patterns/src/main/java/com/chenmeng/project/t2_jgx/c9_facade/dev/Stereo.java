package com.chenmeng.project.t2_jgx.c9_facade.dev;

import lombok.Getter;

/**
 * @author chenmeng
 */
public class Stereo {

    @Getter
    private static Stereo instance = new Stereo();


    public void on() {
        System.out.println("Stereo turned on");
    }

    public void off() {
        System.out.println("Stereo turned off");
    }

    public void setVolume(int volume) {
        System.out.println("Stereo volume set to " + volume);
    }
}