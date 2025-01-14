package com.chenmeng.project;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 服务器相关测试
 *
 * @author chenmeng
 */
@Slf4j
public class ServerTest {

    /**
     * 测试检查服务器状态
     */
    @Test
    void testCheckDeviceStatus() {
        // Integer status = checkDeviceStatus("127.0.0.1");
        Integer status = checkDeviceStatus("192.168.238.65");
        System.out.println(status);
    }

    private Integer checkDeviceStatus(String managementIp) {
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
}
