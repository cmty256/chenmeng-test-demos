package com.chenmeng.project.server;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 服务器相关测试
 *
 * @author chenmeng
 */
@Slf4j
public class ServerTest {

    String host = "192.168.2.129";
    String user = "root";
    String password = "123456";
    int port = 22;

    /**
     * 测试检查服务器状态
     */
    @Test
    void testCheckDeviceStatus() {
        // Integer status = checkDeviceStatus("127.0.0.1");
        Integer status = SSHClient.checkDeviceStatus(host);
        System.out.println(status);
    }

    /**
     * 测试获取服务器信息
     */
    @Test
    void testGetServerInfo() {
        String[] commands = {
                "top -bn1 | grep 'Cpu(s)' | awk '{print $2 + $4}'", // CPU 使用率
                "free | grep Mem | awk '{print $3/$2 * 100.0}'",    // 内存使用率
                "iostat -x",                                        // 磁盘 IO 队列长度
                "df -h | grep '/$' | awk '{print $5}'",             // 磁盘使用率
                "iostat -d | awk 'NR==4 {print $2}'"                // 磁盘吞吐量
        };

        for (String command : commands) {
            System.out.println("Executing command: " + command);
            String result = SSHClient.executeCommand(host, port, user, password, command);
            System.out.println(result);
        }
    }

    /**
     * 测试服务器开关机功能
     */
    @Test
    void testSwitchServerStatus() {
        // todo 待完善
        String s1 = "shutdown -h now"; // 关机
        String s2 = ""; // 开机
        String s3 = ""; // 重启
        String result;
        result = SSHClient.executeCommand(host, port, user, password, s1);
        // result = SSHClient.executeCommand(host, port, user, password, s2);
        // result = SSHClient.executeCommand(host, port, user, password, s3);
        System.out.println("result = " + result);
    }
}
