package com.chenmeng.project.c0_principle.demeter.improve;

import java.util.ArrayList;
import java.util.List;

/**
 * 遵循 最少知道原则，即迪米特法则
 * 核心：降低类之间的耦合，每个类都减少不必要的依赖
 * 注意：只是要求降低类之间（对象间）耦合关系，并不是完全没有依赖关系
 */
public class Demeter1 {

    public static void main(String[] args) {

        System.out.println("~~~使用迪米特法则的改进~~~");
        // 创建了一个 SchoolManager 对象
        SchoolManager schoolManager = new SchoolManager();
        // 输出学院的员工id 和  学校总部的员工信息
        schoolManager.printAllEmployee(new CollegeManager());
    }
}


// 学校总部员工类
class Employee {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}


// 学院的员工类
class CollegeEmployee {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}


// 管理学院员工的管理类
@SuppressWarnings("all")
class CollegeManager {

    // 返回学院的所有员工
    public List<CollegeEmployee> getAllEmployee() {
        List<CollegeEmployee> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) { // 这里我们增加了10个员工到 list
            CollegeEmployee emp = new CollegeEmployee();
            emp.setId("学院员工id= " + i);
            list.add(emp);
        }
        return list;
    }

    // 输出学院员工的信息
    public void printEmployee() {
        // 获取到学院员工
        List<CollegeEmployee> list1 = getAllEmployee();
        System.out.println("------------学院员工------------");
        for (CollegeEmployee e : list1) {
            System.out.println(e.getId());
        }
    }
}

// 学校管理类
// 将输出学院的员工方法，封装到CollegeManager。从而没有违背 迪米特法则
@SuppressWarnings("all")
class SchoolManager {

    // 返回学校总部的员工
    public List<Employee> getAllEmployee() {
        List<Employee> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) { // 这里我们增加了5个员工到 list
            Employee emp = new Employee();
            emp.setId("学校总部员工id= " + i);
            list.add(emp);
        }
        return list;
    }

    // 该方法完成输出学校总部和学院员工信息(id)
    void printAllEmployee(CollegeManager sub) {

        // 分析问题
        // 1. 将输出学院的员工方法，封装到CollegeManager
        sub.printEmployee();

        // 获取到学校总部员工
        List<Employee> list2 = this.getAllEmployee();
        System.out.println("------------学校总部员工------------");
        for (Employee e : list2) {
            System.out.println(e.getId());
        }
    }
}
