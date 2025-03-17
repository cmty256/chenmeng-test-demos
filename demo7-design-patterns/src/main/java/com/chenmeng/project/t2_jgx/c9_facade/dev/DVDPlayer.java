package com.chenmeng.project.t2_jgx.c9_facade.dev;

import lombok.Getter;

/**
 * 子系统类
 *
 * @author chenmeng
 */
public class DVDPlayer {

    // 使用单例模式, 使用饿汉式(静态变量)
    @Getter
    private static DVDPlayer instance = new DVDPlayer();

    public void on() {
        System.out.println("DVD Player turned on");
    }

    public void off() {
        System.out.println("DVD Player turned off");
    }

    public void play() {
        System.out.println("DVD is playing");
    }

    public void pause() {
        System.out.println("DVD playback paused");
    }
}
