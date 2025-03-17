package com.chenmeng.project.spring.bean;

import lombok.Data;

/**
 * 原型模式示例 bean
 *
 * @author chenmeng
 */
@Data
public class Monster {

    private Integer id = 10;
    private String nickname = "牛魔王";
    private String skill = "芭蕉扇";

    public Monster() {
        System.out.println("monster 创建..");
    }

    public Monster(Integer id, String nickname, String skill) {
        System.out.println("Integer id, String nickname, String skill被调用");
        this.id = id;
        this.nickname = nickname;
        this.skill = skill;
    }
}