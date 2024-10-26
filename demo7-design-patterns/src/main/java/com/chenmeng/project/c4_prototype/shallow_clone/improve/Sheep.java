package com.chenmeng.project.c4_prototype.shallow_clone.improve;

public class Sheep implements Cloneable {

    private String name;
    private Integer age;
    private String color;
    private String address = "蒙古羊";
    public Sheep friend; // 引用类型是对象, 克隆时会如何处理？（默认浅拷贝）

    public Sheep(String name, int age, String color) {
        super();
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Sheep [name=" + name + ", age=" + age + ", color=" + color + ", address=" + address + "]";
    }

    /**
     * 克隆该实例，使用默认的clone方法来完成（浅拷贝）
     */
    @Override
    protected Object clone() {
        Sheep sheep = null;
        try {
            sheep = (Sheep) super.clone();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sheep;
    }
}
