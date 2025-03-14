package com.chenmeng.project.t1_cjx.c1_singleton.type5;

/**
 * 懒汉式(线程不安全，同步代码块) -- 不推荐
 *
 * @author chenmeng
 */
public class SingletonTest05 {

    public static void main(String[] args) {
        System.out.println("懒汉式 3，线程安全，同步代码块~");
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance == instance2); // true
        System.out.println("instance.hashCode=" + instance.hashCode());
        System.out.println("instance2.hashCode=" + instance2.hashCode());
    }

}

// 懒汉式(线程不安全，同步代码块)
class Singleton {
    private static Singleton instance;

    // 构造器私有化, 防止直接new
    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            // 同步代码块（可能会创建多个实例）
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }
}