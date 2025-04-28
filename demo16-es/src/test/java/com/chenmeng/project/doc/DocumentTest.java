package com.chenmeng.project.doc;

import cn.hutool.json.JSONUtil;
import com.chenmeng.project.dto.UserDoc;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DocumentTest {

    String ES_URL = "http://192.168.207.129:9200";

    private RestHighLevelClient client;

    @BeforeEach
    void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create(ES_URL)
        ));
        System.out.println("setUp success");
    }

    @Test
    void testInsertDocument() throws IOException {
        // 1.准备Request对象
        IndexRequest request = new IndexRequest();
        request.index("user").id("1001");
        // 2.准备Json文档，向ES插入数据，必须将数据转换位JSON格式
        UserDoc userDoc = new UserDoc();
        userDoc.setUserName("zhangsan");
        userDoc.setGender(1);
        request.source(JSONUtil.toJsonStr(userDoc), XContentType.JSON);
        // 3.发送请求
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        System.out.println("res=" + response.getResult());
    }

    @Test
    void testGetDocumentById() throws IOException {
        // 1.准备Request对象
        GetRequest request = new GetRequest("user", "1001");
        // 2.发送请求
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 3.获取响应结果中的source
        String json = response.getSourceAsString();

        UserDoc userDoc = JSONUtil.toBean(json, UserDoc.class);
        System.out.println("userDoc= " + userDoc);
    }

    @Test
    void testDeleteDocument() throws IOException {
        // 1.准备Request，两个参数，第一个是索引库名，第二个是文档id
        DeleteRequest request = new DeleteRequest("user", "1001");
        // 2.发送请求
        client.delete(request, RequestOptions.DEFAULT);
    }

    @Test
    void testUpdateDocument() throws IOException {
        // 1.准备Request
        UpdateRequest request = new UpdateRequest("user", "1001");
        // 2.准备请求参数
        request.doc(
                "user_name", "王五",
                "gender", 0
        );
        // 3.发送请求
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);

        System.out.println("updateResponse.getResult() = " + updateResponse.getResult());
    }

    @Test
    void testBulk() throws IOException {
        ArrayList<UserDoc> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            UserDoc userDoc = new UserDoc();
            userDoc.setUserName("zhangsan_" + i);
            userDoc.setGender(1);
            list.add(userDoc);
        }

        // 1.创建Request
        BulkRequest request = new BulkRequest();
        // 2.准备请求参数
        for (int i = 0; i < list.size(); i++) {
            UserDoc userDoc = list.get(i);
            request.add(new IndexRequest("user")
                    .id("" + (i + 1))
                    .source(JSONUtil.toJsonStr(userDoc), XContentType.JSON));
        }
        // 3.发送请求
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println("bulkResponse.getTook() = " + bulkResponse.getTook());
        System.out.println(Arrays.toString(bulkResponse.getItems()));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
        System.out.println("tearDown success");
    }
}