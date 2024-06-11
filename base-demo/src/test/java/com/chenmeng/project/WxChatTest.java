package com.chenmeng.project;

import cn.hutool.json.JSONObject;
import com.chenmeng.common.utils.OkHttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenmeng
 */
@SpringBootTest
public class WxChatTest {

    /**
     * 消息模板ID
     */
    public final static String JKGJ_MSG_TEMPLATE_ID = "1ZKITnxcxWPNY50DlPn4SO8E8gCJPBdEqIXbLCQfXIs";
    /**
     * 消息跳转页面
     */
    public final static String JKGJ_MSG_PAGE = "https://cmty256.github.io/";
    /**
     * 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
     */
    public final static String MINIPROGRAM_STATE = "trial";
    /**
     * 语言类型-简体中文
     */
    public final static String LANG_ZH_CN = "zh_CN";

    /**
     * 订阅消息推送测试
     */
    @Test
    void testSubMsg() {
        // 写法一
        Map<String, Map<String, Object>> dataMap = new HashMap<>(4);
        dataMap.put("thing10", createDataItem("value", "您有新的提醒"));
        dataMap.put("thing1", createDataItem("value", "早安午安晚安"));
        dataMap.put("time2", createDataItem("value", "2024-06-11"));
        dataMap.put("thing4", createDataItem("value", "张三"));

        // 写法二
        // JSONObject messageData = new JSONObject();
        // messageData.set("thing10", createDataItem("提醒内容", "您有新的提醒"));
        // messageData.set("thing1", createDataItem("作业名称", "Java作业"));
        // messageData.set("time2", createDataItem("截至日期", "2023-06-30"));
        // messageData.set("thing4", createDataItem("发布人员", "张三"));

        // 请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=??";
        // 构造请求体
        JSONObject requestBody = new JSONObject() {{
            set("touser", "openid");
            set("template_id", JKGJ_MSG_TEMPLATE_ID);
            set("page", JKGJ_MSG_PAGE);
            set("miniprogram_state", MINIPROGRAM_STATE);
            set("lang", LANG_ZH_CN);
            set("data", dataMap);
        }};
        // 发送请求
        OkHttpUtil.sendPostJson(requestUrl, requestBody.toString());
    }

    private static Map<String, Object> createDataItem(String name, String value) {
        Map<String, Object> item = new HashMap<>();
        item.put("value", value);
        return item;
    }
}
