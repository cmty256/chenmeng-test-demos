package com.chenmeng.project.c1_singleton.type3;

/**
 * 懒汉式(线程不安全) -- 不推荐
 *
 * @author chenmeng
 */
public class SingletonTest03 {

    public static void main(String[] args) {
        System.out.println("懒汉式 1，线程不安全~");
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance == instance2); // true
        System.out.println("instance.hashCode=" + instance.hashCode());
        System.out.println("instance2.hashCode=" + instance2.hashCode());
    }

}

// 懒汉式(线程不安全)
class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    // 提供一个静态的公有方法，当使用到该方法时，才去创建 instance
    // 即懒汉式
    public static Singleton getInstance() {
        // 判断 instance 是否为空，为空则创建（线程不安全）
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}