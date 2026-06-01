package com.chenmeng.project.json;

import com.alibaba.fastjson2.*;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Fastjson2 测试类
 * <a href="https://alibaba.github.io/fastjson2/">官方文档</a>
 * <a href="https://github.com/alibaba/fastjson2/blob/main/README_cn.md#14-spring-%E6%A1%86%E6%9E%B6%E9%9B%86%E6%88%90/">官方文档</a>
 *
 * @author chenmeng
 */
public class FastjsonTest {

    String s1 = "{\n" +
            "  \"code\":0,\n" +
            "  \"data\":{\n" +
            "    \"fileList\":[\n" +
            "      {\n" +
            "        \"code\":0,\n" +
            "        \"file_name\":\"/cloud-api/storage-weed233/2024-12-03/16-7/5ba9e2178906cca4027dddf5/17332140357930.4248849296330288.jpeg\",\n" +
            "        \"label\":15765,\n" +
            "        \"labels\":[\n" +
            "          15765,\n" +
            "          19896,\n" +
            "          4,\n" +
            "          11797,\n" +
            "          3923\n" +
            "        ],\n" +
            "        \"name\":\"http://scj.yuexiu.gov.cn:8082/monitoring-platform/dcda1429c1b45367f8d1d1de035c60bd33a60e2c01465b32ff83dfb8e37955ec.jpg\",\n" +
            "        \"rate\":0.32277753949165344,\n" +
            "        \"rates\":[\n" +
            "          0.32277753949165344,\n" +
            "          0.28426995873451233,\n" +
            "          0.17071975767612457,\n" +
            "          0.1364746242761612,\n" +
            "          0.02689996175467968\n" +
            "        ],\n" +
            "        \"review\":true,\n" +
            "        \"tag\":\"Using url\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"reviewCount\":1,\n" +
            "    \"topNStatistic\":[\n" +
            "      {\n" +
            "        \"count\":1,\n" +
            "        \"label\":15765\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"desc\":\"success\",\n" +
            "  \"sid\":\"tup00017c17@dx435f1aaa0b53a00100\"\n" +
            "}";

    String s2 = "{\n" +
            "  \"header\":{\n" +
            "    \"code\":0,\n" +
            "    \"message\":\"success\",\n" +
            "    \"sid\":\"ase000f1da9@hu1938bafe74e05c2882\"\n" +
            "  },\n" +
            "  \"payload\":{\n" +
            "    \"result\":{\n" +
            "      \"compress\":\"raw\",\n" +
            "      \"encoding\":\"utf8\",\n" +
            "      \"format\":\"json\",\n" +
            "      \"text\":\"eyJwbGFjZSI6W3siZW50aXR5IjpbeyJpZCI6NTIsIm5hbWUiOiJjbGFzc3Jvb20iLCJzY29yZSI6MC45OTg4ODc3MTc3MjM4NDY0NH1dLCJmcmFtZUlEIjowLCJzdGFydFRpbWVPZmZzZXQiOjAuMH1dfQ==\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"place\":[\n" +
            "    {\n" +
            "      \"entity\":[\n" +
            "        {\n" +
            "          \"id\":52,\n" +
            "          \"name\":\"classroom\",\n" +
            "          \"score\":0.9988877177238464\n" +
            "        }\n" +
            "      ],\n" +
            "      \"frameID\":0,\n" +
            "      \"startTimeOffset\":0.0\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    /**
     * 测试 s1：提取基础字段、遍历数组、获取数组中的特定元素
     */
    @Test
    void testS1_ParseFileList() {
        // 1. 将字符串解析为 JSONObject
        JSONObject json = JSONObject.parseObject(s1);

        // 2. 提取最外层的基础字段
        System.out.println("外层 code: " + json.getIntValue("code"));
        System.out.println("外层 desc: " + json.getString("desc"));
        System.out.println("外层 sid: " + json.getString("sid"));

        // 3. 提取嵌套的 data 对象
        JSONObject data = json.getJSONObject("data");
        if (data != null) {
            System.out.println("审核数量 reviewCount: " + data.getIntValue("reviewCount"));

            // 4. 提取 data 中的 fileList 数组
            JSONArray fileList = data.getJSONArray("fileList");
            if (fileList != null) {
                // 遍历数组
                for (int i = 0; i < fileList.size(); i++) {
                    JSONObject fileItem = fileList.getJSONObject(i);
                    System.out.println("--- 文件信息 ---");
                    System.out.println("文件名: " + fileItem.getString("file_name"));
                    System.out.println("访问地址: " + fileItem.getString("name"));
                    System.out.println("标签: " + fileItem.getInteger("label"));

                    // 提取数组中的数组 (labels)
                    JSONArray labels = fileItem.getJSONArray("labels");
                    System.out.println("所有标签列表: " + labels);
                }
            }
        }
    }

    /**
     * 测试 s2：提取嵌套对象、解析 Base64 嵌套数据、提取数组中的对象
     */
    @Test
    void testS2_ParsePayloadAndBase64() {
        // 1. 将字符串解析为 JSONObject
        JSONObject json = JSONObject.parseObject(s2);

        // 2. 提取 header 中的信息
        JSONObject header = json.getJSONObject("header");
        System.out.println("Header message: " + header.getString("message"));

        // 3. 使用 JSONPath 提取 payload.result 中的 Base64 文本
        String base64Text = (String) JSONPath.eval(s2, "$.payload.result.text");
        System.out.println("提取到的 Base64 文本: " + base64Text);

        // 4. 解码 Base64 并继续解析内部的 JSON
        String decodedJsonStr = new String(Base64.getDecoder().decode(base64Text));
        System.out.println("Base64 解码后的 JSON: " + decodedJsonStr);

        // 解析解码后的 JSON
        JSONObject decodedJson = JSONObject.parseObject(decodedJsonStr);
        JSONArray placeArray = decodedJson.getJSONArray("place");
        if (placeArray != null && !placeArray.isEmpty()) {
            JSONObject firstPlace = placeArray.getJSONObject(0);
            JSONArray entityArray = firstPlace.getJSONArray("entity");
            if (entityArray != null && !entityArray.isEmpty()) {
                System.out.println("解码后的实体名称: " + entityArray.getJSONObject(0).getString("name"));
                System.out.println("解码后的实体分数: " + entityArray.getJSONObject(0).getDouble("score"));
            }
        }

        // 5. 提取 s2 最外层的 place 数组
        JSONArray outerPlace = json.getJSONArray("place");
        if (outerPlace != null) {
            System.out.println("\n--- s2 外层 place 数组 ---");
            // 提取数组中第一个对象的 name 字段
            Object name = outerPlace.getJSONObject(0).getJSONArray("entity").getJSONObject(0).get("name");
            System.out.println("外层 place 中的实体名称: " + name);
        }
    }

    /**
     * 测试 s1：使用 JSONPath 快速提取 s1 中的第一个文件名
     */
    @Test
    void testS1_JsonPath() {
        // 使用 JSONPath 一行代码提取深层嵌套数组里的字段
        String fileName = (String) JSONPath.eval(s1, "$.data.fileList[0].file_name");
        System.out.println("JSONPath 提取的文件名: " + fileName);
    }

    /**
     * 测试：JSONWriter.Feature 的格式化与行为控制
     * 演示如何精细控制 JSON 序列化的输出规则
     */
    @Test
    void testJSONWriter_Features() {
        System.out.println("----------------------------------------------------------------------------------" + "\n");

        // 准备一个包含 null 值、日期、大数字的测试对象
        Map<String, Object> data = new HashMap<>();
        data.put("name", "张三");
        data.put("age", 30);
        data.put("email", null); // 值为 null
        data.put("salary", 9999999999999L); // 超大数字，JS 前端可能会精度丢失
        data.put("createTime", new Date());

        System.out.println("【1. 默认序列化】（忽略 null 字段，紧凑输出）：");
        String defaultJson = JSON.toJSONString(data);
        System.out.println(defaultJson + "\n");
        System.out.println("----------------------------------------------------------------------------------" + "\n");

        System.out.println("【2. PrettyFormat 格式化输出】（带缩进换行，方便调试）：");
        String prettyJson = JSON.toJSONString(data, JSONWriter.Feature.PrettyFormat);
        System.out.println(prettyJson + "\n");
        System.out.println("----------------------------------------------------------------------------------" + "\n");

        System.out.println("【3. WriteMapNullValue 输出 null 值】：");
        String withNullJson = JSON.toJSONString(data, JSONWriter.Feature.WriteMapNullValue);
        System.out.println(withNullJson + "\n");
        System.out.println("----------------------------------------------------------------------------------" + "\n");

        System.out.println("【4. BrowserCompatible 浏览器兼容】（Long 转 String，防止前端精度丢失）：");
        String browserJson = JSON.toJSONString(data, JSONWriter.Feature.BrowserCompatible);
        System.out.println(browserJson + "\n");
        System.out.println("----------------------------------------------------------------------------------" + "\n");

        System.out.println("【5. 组合多个 Feature】（既要格式化，又要输出 null）：");
        String combinedJson = JSON.toJSONString(data,
                JSONWriter.Feature.PrettyFormat,
                JSONWriter.Feature.WriteMapNullValue);
        System.out.println(combinedJson);

        System.out.println("----------------------------------------------------------------------------------" + "\n");
    }
}
