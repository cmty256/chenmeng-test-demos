package com.chenmeng.project.t1_cjx.c1_singleton.type4;

/**
 * 懒汉式(线程安全，同步方法，效率太低) -- 不推荐
 *
 * @author chenmeng
 */
public class SingletonTest04 {

    public static void main(String[] args) {
        System.out.println("懒汉式 2，线程安全，同步方法~");
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance == instance2); // true
        System.out.println("instance.hashCode=" + instance.hashCode());
        System.out.println("instance2.hashCode=" + instance2.hashCode());
    }

}

// 懒汉式(线程安全，同步方法，效率太低)
class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    // 提供一个静态的公有方法，加入 同步处理 的代码，解决线程安全问题
    // 即懒汉式
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}