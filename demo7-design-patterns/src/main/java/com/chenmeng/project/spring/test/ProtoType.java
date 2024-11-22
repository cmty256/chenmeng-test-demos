package com.chenmeng.project.spring.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring 原型模式示例
 *
 * @author chenmeng
 */
public class ProtoType {

    public static void main(String[] args) {
        // 通过配置文件,获取核心容器对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        // 获取monster[通过id获取monster], Spring中原型bean的创建，就是原型模式的应用
        Object bean = applicationContext.getBean("id01");
        System.out.println("bean" + bean); // 输出 "牛魔王" .....

        Object bean2 = applicationContext.getBean("id01");

        System.out.println("bean2" + bean2); // 输出 "牛魔王" .....

        System.out.println(bean == bean2); // false

        // ConfigurableApplicationContext
    }

}
