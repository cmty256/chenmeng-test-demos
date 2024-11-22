package com.chenmeng.project.c3_prototype.shallow_clone.improve;

public class Client {

    public static void main(String[] args) {
        System.out.println("【原型模式】完成对象的创建");

        Sheep sheep = new Sheep("tom", 1, "白色");

        sheep.friend = new Sheep("jack", 2, "黑色");

        Sheep sheep2 = (Sheep) sheep.clone(); // 克隆
        Sheep sheep3 = (Sheep) sheep.clone(); // 克隆
        Sheep sheep4 = (Sheep) sheep.clone(); // 克隆
        Sheep sheep5 = (Sheep) sheep.clone(); // 克隆

        // 输出的 hashCode 是一样的，说明是同一个对象，因为是浅拷贝。其中一个对象改变了，其他对象都会变
        System.out.println("sheep2 =" + sheep2 + "sheep2.friend=" + sheep2.friend.hashCode());
        System.out.println("sheep3 =" + sheep3 + "sheep3.friend=" + sheep3.friend.hashCode());
        System.out.println("sheep4 =" + sheep4 + "sheep4.friend=" + sheep4.friend.hashCode());
        System.out.println("sheep5 =" + sheep5 + "sheep5.friend=" + sheep5.friend.hashCode());
    }

}
