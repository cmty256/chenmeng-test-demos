package com.chenmeng.project.mybatis.test;

/**
 * JDBC -- 桥接模式示例
 *
 * @author chenmeng
 */
public class Bridge {

    public static void main(String[] args) {
        // 1、Driver 接口 -- 定义了所有【JDBC 驱动程序】必须实现的方法。每个具体的驱动程序都会提供自己的 Driver 实现。

        // 2、DriverManager 类 -- 负责桥接

        // 3、Connection 接口 -- 所有【数据库连接】的通用接口

        // 3.1、JdbcConnection 接口 -- Mysql 驱动相关
        // 3.2、MysqlConnection 接口 -- Mysql 驱动相关
        // 3.3、ConnectionImpl -- 某个数据库驱动的具体实现
    }

}
