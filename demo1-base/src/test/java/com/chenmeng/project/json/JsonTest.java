package com.chenmeng.project.json;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chenmeng
 */
@SpringBootTest
public class JsonTest {

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

    @Test
    void test1() {
        JSON json = JSONUtil.parse(s1);

        String desc = String.valueOf(JSONUtil.getByPath(json, "$.desc"));
        // desc = success
        System.out.println("desc = " + desc);

        String topNStatistic = String.valueOf(JSONUtil.getByPath(json, "$.data.topNStatistic"));
        JSONArray jsonArray = JSONUtil.parseArray(topNStatistic);
        String label = String.valueOf(jsonArray.getByPath("[0].label"));
        // label = 15765
        System.out.println("label = " + label);

        String label2 = String.valueOf(JSONUtil.getByPath(json, "$.data.topNStatistic.[0].label"));
        // label2 = 15765
        System.out.println("label2 = " + label2);
    }

    @Test
    void test2() {
        JSON json = JSONUtil.parse(s2);

        String message = String.valueOf(JSONUtil.getByPath(json, "$.header.message"));
        if (!"success".equals(message)) {
            return;
        }
        // message = success
        System.out.println("message = " + message);

        String label = String.valueOf(JSONUtil.getByPath(json, "$.place.[0].entity.[0].id"));
        // label = 52
        System.out.println("label = " + label);
    }
}
