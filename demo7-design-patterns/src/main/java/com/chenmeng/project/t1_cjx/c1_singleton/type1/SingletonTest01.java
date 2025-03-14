package com.chenmeng.project.t1_cjx.c1_singleton.type1;

/**
 * 饿汉式(静态变量) -- 常用（需保证实例会使用到，否则会造成内存浪费）
 *
 * @author chenmeng
 */
public class SingletonTest01 {

    public static void main(String[] args) {
        // 测试
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance == instance2); // true
        System.out.println("instance.hashCode=" + instance.hashCode());
        System.out.println("instance2.hashCode=" + instance2.hashCode());
    }

}

// 饿汉式(静态变量) -- 常用（需保证实例会使用到，否则会造成内存浪费）
// 优点：简单且线程安全。
// 缺点：即使不需要也会立即加载实例，可能会浪费资源。
class Singleton {

    // 1. 构造器私有化, 防止直接new
    private Singleton() {

    }

    // 2.本类内部创建对象实例
    private final static Singleton instance = new Singleton();

    // 3. 提供一个公有的静态方法，返回实例对象
    public static Singleton getInstance() {
        return instance;
    }

}