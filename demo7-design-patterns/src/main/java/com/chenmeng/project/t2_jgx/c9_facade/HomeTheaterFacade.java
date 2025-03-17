package com.chenmeng.project.t2_jgx.c9_facade;

import com.chenmeng.project.t2_jgx.c9_facade.dev.*;

/**
 * @author chenmeng
 */
public class HomeTheaterFacade {

	// 定义各个子系统对象
	private final TheaterLight theaterLight;
	private final Popcorn popcorn;
	private final Stereo stereo;
	private final Projector projector;
	private final Screen screen;
	private final DVDPlayer dvdPlayer;


	// 构造器：初始化所有子系统单例
	public HomeTheaterFacade() {
		super();
		this.theaterLight = TheaterLight.getInstance();
		this.popcorn = Popcorn.getInstance();
		this.stereo = Stereo.getInstance();
		this.projector = Projector.getInstance();
		this.screen = Screen.getInstance();
		this.dvdPlayer = DVDPlayer.getInstance();
	}

	// 操作分成 4 步

	/**
	 * 准备观影：初始化所有设备
	 */
	public void ready() {
		// 初始化爆米花机
		popcorn.on();
		popcorn.pop();

		// 降下屏幕
		screen.down();

		// 启动投影仪
		projector.on();
		projector.focus();

		// 开启音响
		stereo.on();
		stereo.setVolume(5);

		// 启动DVD播放器
		dvdPlayer.on();

		// 调暗灯光
		theaterLight.dim();
	}

	/**
	 * 开始播放电影
	 */
	public void play() {
		dvdPlayer.play();
	}

	/**
	 * 暂停播放
	 */
	public void pause() {
		dvdPlayer.pause();
	}

	/**
	 * 结束观影：关闭所有设备并恢复环境
	 */
	public void end() {
		popcorn.off();
		theaterLight.bright();
		screen.up();
		projector.off();
		stereo.off();
		dvdPlayer.off();
	}
}