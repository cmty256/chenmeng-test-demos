package com.chenmeng.project.server;

import cn.hutool.core.util.StrUtil;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class SSHClient {

    public static Integer checkDeviceStatus(String managementIp) {
        if (StrUtil.isBlank(managementIp)) {
            throw new IllegalArgumentException("管理IP不能为空");
        }

        try {
            InetAddress address = InetAddress.getByName(managementIp);
            if (!address.isReachable(5000)) { // 等待5秒（5000毫秒）
                return 0;
            }
            return 1;

        } catch (UnknownHostException e) {
            log.error("未知主机: {}", managementIp, e);
            return 0;
        } catch (Exception e) {
            log.error("发生错误: {}", e.getMessage(), e);
            return 0;
        }
    }

    public static String executeCommand(String host, int port, String user, String password, String command) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setInputStream(null);
            channel.connect();

            InputStream in = channel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            channel.disconnect();
            session.disconnect();
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String host = "192.168.2.129";
        String user = "root";
        String password = "123456";
        int port = 22;

        String[] commands = {
                "top -bn1 | grep 'Cpu(s)' | awk '{print $2 + $4}'", // CPU 使用率
                "free | grep Mem | awk '{print $3/$2 * 100.0}'",    // 内存使用率
                "iostat -x",                                        // 磁盘 IO 队列长度
                "df -h | grep '/$' | awk '{print $5}'",             // 磁盘使用率
                "iostat -d | awk 'NR==4 {print $2}'"                // 磁盘吞吐量
        };

        for (String command : commands) {
            System.out.println("Executing command: " + command);
            String result = executeCommand(host, port, user, password, command);
            System.out.println(result);
        }

        String res = executeCommand(host, port, user, password, "ls -l");
        System.out.println(res);
    }
}
