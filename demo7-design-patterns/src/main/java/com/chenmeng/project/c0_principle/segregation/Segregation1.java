package com.chenmeng.project.c0_principle.segregation;

/**
 * 传统方法版本
 *
 * <p>
 * 对于类A类通过接口 Interface1 依赖(使用) B类，但是只会用到 1,2,3 方法
 * 对于类C类通过接口 Interface1 依赖(使用) D类，但是只会用到 1,4,5 方法
 * 所以 Interface1 对于 类A 和 类C 来说，Interface1 不是最小接口
 * </P>
 *
 * @author chenmeng
 */
public class Segregation1 {

    public static void main(String[] args) {
        // A 类通过接口Interface1 依赖(使用) B类，但是只会用到1,2,3方法
        A a1 = new A();
        a1.depend1(new B());
        a1.depend2(new B());
        a1.depend3(new B());

        // C 类通过接口Interface1 依赖(使用) D类，但是只会用到1,4,5方法
        C c1 = new C();
        c1.depend1(new D());
        c1.depend4(new D());
        c1.depend5(new D());
    }
}

// 接口
interface Interface1 {
    void operation1();

    void operation2();

    void operation3();

    void operation4();

    void operation5();
}

class B implements Interface1 {

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

    @Override
    public void operation4() {
        System.out.println("B 实现了 operation4");
    }

    @Override
    public void operation5() {
        System.out.println("B 实现了 operation5");
    }
}

class D implements Interface1 {

    @Override
    public void operation1() {
        System.out.println("D 实现了 operation1");
    }

    @Override
    public void operation2() {
        System.out.println("D 实现了 operation2");
    }

    @Override
    public void operation3() {
        System.out.println("D 实现了 operation3");
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

// A 类通过接口Interface1 依赖(使用) B类，但是只会用到1,2,3方法
class A {

    public void depend1(Interface1 i) {
        i.operation1();
    }

    public void depend2(Interface1 i) {
        i.operation2();
    }

    public void depend3(Interface1 i) {
        i.operation3();
    }
}

// C 类通过接口Interface1 依赖(使用) D类，但是只会用到1,4,5方法
class C {
    public void depend1(Interface1 i) {
        i.operation1();
    }

    public void depend4(Interface1 i) {
        i.operation4();
    }

    public void depend5(Interface1 i) {
        i.operation5();
    }
}