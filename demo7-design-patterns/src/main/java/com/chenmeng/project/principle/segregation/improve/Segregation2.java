package com.chenmeng.project.principle.segregation.improve;

/**
 * 接口隔离原则改进版本
 *
 * <p>
 * A 类通过接口 Interface1,Interface2 依赖(使用) B类
 * C 类通过接口 Interface1,Interface3 依赖(使用) D类
 * 对于 类A 来说，Interface1,Interface2 是最小接口
 * 对于 类C 来说，Interface1,Interface3 是最小接口
 * </P>
 *
 * @author chenmeng
 */
public class Segregation2 {
    public static void main(String[] args) {
        // A类通过接口 Interface1,Interface2 去依赖B类
        A a2 = new A();
        a2.depend1(new B());
        a2.depend2(new B());
        a2.depend3(new B());

        // C类通过接口 Interface1,Interface3 去依赖(使用)D类
        C c2 = new C();
        c2.depend1(new D());
        c2.depend4(new D());
        c2.depend5(new D());
    }
}

// 接口1 -- 方法1
interface Interface1 {
    void operation1();

}

// 接口2 -- 方法2，3
interface Interface2 {
    void operation2();

    void operation3();
}

// 接口3 -- 方法4，5
interface Interface3 {
    void operation4();

    void operation5();
}

// B类 -- 实现接口1，2 -- 重写方法1，2，3
class B implements Interface1, Interface2 {

    @Override
    public void operation1() {
        System.out.println("B 实现了 operation1");
    }

    @Override
    public void operation2() {
        System.out.println("B 实现了 operation2");
    }

    @Override
    public void operation3() {
        System.out.println("B 实现了 operation3");
    }

}

// D类 -- 实现接口1，3 -- 重写方法1，4，5
class D implements Interface1, Interface3 {

    @Override
    public void operation1() {
        System.out.println("D 实现了 operation1");
    }

    @Override
    public void operation4() {
        System.out.println("D 实现了 operation4");
    }

    @Override
    public void operation5() {
        System.out.println("D 实现了 operation5");
    }
}

// A 类通过接口Interface1,Interface2 依赖(使用) B类，但是只会用到1,2,3方法
class A {
    public void depend1(Interface1 i) {
        i.operation1();
    }

    public void depend2(Interface2 i) {
        i.operation2();
    }

    public void depend3(Interface2 i) {
        i.operation3();
    }
}

// C 类通过接口Interface1,Interface3 依赖(使用) D类，但是只会用到1,4,5方法
class C {
    public void depend1(Interface1 i) {
        i.operation1();
    }

    public void depend4(Interface3 i) {
        i.operation4();
    }

    public void depend5(Interface3 i) {
        i.operation5();
    }
}