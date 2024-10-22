package com.chenmeng.project.c1_singleton.type8;

/**
 * 枚举 -- 推荐使用
 *
 * @author chenmeng
 */
public class SingletonTest08 {
    public static void main(String[] args) {
        Singleton instance = Singleton.INSTANCE;
        Singleton instance2 = Singleton.INSTANCE;
        System.out.println(instance == instance2);

        System.out.println(instance.hashCode());
        System.out.println(instance2.hashCode());

        instance.sayOK();
    }
}

// 使用枚举，可以实现单例, 推荐
// 枚举类只会装载一次，而且只会装载一次，枚举类是单例的
enum Singleton {
    INSTANCE; // 属性

    public void sayOK() {
        System.out.println("ok~");
    }
}