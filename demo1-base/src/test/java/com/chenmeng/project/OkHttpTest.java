package com.chenmeng.project;

import com.chenmeng.common.utils.OkHttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chenmeng
 */
@SpringBootTest
public class OkHttpTest {

    public static final String url = "http://localhost:7529/api/user";

    @Test
    void test() {
        String getJson = OkHttpUtil.sendGet(url);
        System.out.println("getJson = " + getJson);
    }
}
