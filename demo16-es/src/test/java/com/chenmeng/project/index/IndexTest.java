package com.chenmeng.project.index;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class IndexTest {

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
    void testConnect() {
        System.out.println(client);
    }

    @Test
    void testCreateIndex() throws IOException {
        // 1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest("user");
        // 2.准备请求参数
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求
        client.indices()
                .create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testDeleteIndex() throws IOException {
        // 1.创建Request对象
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        // 2.发送请求
        AcknowledgedResponse response = client.indices()
                .delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    @Test
    void testGetIndex() throws IOException {
        // 1.创建Request对象
        GetIndexRequest request = new GetIndexRequest("user");
        // 2.发送请求
        GetIndexResponse getIndexResponse = client.indices()
                .get(request, RequestOptions.DEFAULT);
        // 3.输出
        System.err.println(getIndexResponse.getAliases());
        System.err.println(getIndexResponse.getMappings());
        System.err.println(getIndexResponse.getSettings());
    }

    @Test
    void testExistsIndex() throws IOException {
        // 1.创建Request对象
        GetIndexRequest request = new GetIndexRequest("user");
        // 2.发送请求
        boolean exists = client.indices()
                .exists(request, RequestOptions.DEFAULT);
        // 3.输出
        System.err.println(exists ? "索引库已经存在！" : "索引库不存在！");
    }

    static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"user_account\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"user_password\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"user_name\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\",\n" +
            "            \"ignore_above\": 256\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"user_avatar\": {\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"gender\": {\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"phone\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"email\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"user_profile\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      },\n" +
            "      \"user_role\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"create_user\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"create_time\": {\n" +
            "        \"type\": \"date\",\n" +
            "        \"format\": \"yyyy-MM-dd HH:mm:ss||epoch_millis\"\n" +
            "      },\n" +
            "      \"update_user\": {\n" +
            "        \"type\": \"long\"\n" +
            "      },\n" +
            "      \"update_time\": {\n" +
            "        \"type\": \"date\",\n" +
            "        \"format\": \"yyyy-MM-dd HH:mm:ss||epoch_millis\"\n" +
            "      },\n" +
            "      \"is_deleted\": {\n" +
            "        \"type\": \"byte\"\n" +
            "      },\n" +
            "      \"tenant_id\": {\n" +
            "        \"type\": \"long\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
        System.out.println("tearDown success");
    }
}