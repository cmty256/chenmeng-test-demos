package com.chenmeng.project.t1_cjx.c1_singleton.type7;

/**
 * 静态内部类完成， 推荐使用
 *
 * @author chenmeng
 */
public class SingletonTest07 {

    public static void main(String[] args) {
        System.out.println("使用静态内部类完成单例模式");
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance == instance2); // true
        System.out.println("instance.hashCode=" + instance.hashCode());
        System.out.println("instance2.hashCode=" + instance2.hashCode());

    }

}

// 静态内部类完成， 推荐使用
// 优点：延迟加载且线程安全。
// 缺点：稍微复杂一些。
class Singleton {

    // 构造器私有化
    private Singleton() {
    }

    // 写一个静态内部类,该类中有一个静态属性 SingletonI（不会立即实例化）
    private static class SingletonInstance {
        private static final Singleton INSTANCE = new Singleton();
    }

    // 提供一个静态的公有方法，直接返回 SingletonInstance.INSTANCE
    public static Singleton getInstance() {

        return SingletonInstance.INSTANCE;
    }
}