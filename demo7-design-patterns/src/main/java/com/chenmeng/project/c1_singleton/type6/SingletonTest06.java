package com.chenmeng.project.c1_singleton.type6;

/**
 * 双重检查锁定 -- 推荐使用
 *
 * @author chenmeng
 */
public class SingletonTest06 {

    public static void main(String[] args) {
        System.out.println("双重检查");
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance == instance2); // true
        System.out.println("instance.hashCode=" + instance.hashCode());
        System.out.println("instance2.hashCode=" + instance2.hashCode());

    }

}

// 懒汉式(线程安全，同步方法)
// 双重检查锁定（Double-Checked Locking）
// 优点：线程安全；延迟加载，效率较高
// 缺点：实现稍微复杂一些，需要使用 volatile 关键字来防止指令重排序，确保可见性。
class Singleton {

    // 使用 volatile 关键字来防止指令重排序，确保可见性
    private static volatile Singleton instance;

    private Singleton() {
    }

    // 提供一个静态的公有方法，加入双重检查代码，解决线程安全问题, 同时解决懒加载问题
    // 同时保证了效率, 推荐使用

    public static synchronized Singleton getInstance() {
        if (instance == null) { // 第一次检查：避免不必要地同步。
            synchronized (Singleton.class) {
                if (instance == null) { // 第二次检查：确保只创建一个实例。
                    instance = new Singleton();
                }
            }

        }
        return instance;
    }
}