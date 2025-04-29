package com.chenmeng.project.dsl;

import cn.hutool.json.JSONUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.*;

/**
 * 分数查询
 *
 * @author chenmeng
 */
public class ScoreSearchTest {

    String ES_URL = "http://192.168.207.129:9200";

    private RestHighLevelClient client;

    @BeforeEach
    void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create(ES_URL)
        ));
        System.out.println("setUp success");
    }

    /**
     * 竞价排名
     * @throws IOException 如果发生 I/O 错误
     */
    @Test
    void testBiddingRanking() throws IOException {
        // 1. 构建基础查询（例如关键词搜索）
        // QueryBuilder mainQuery = QueryBuilders.matchQuery("name", "手机");
        MatchAllQueryBuilder mainQuery = QueryBuilders.matchAllQuery();

        // 2. 构建广告商品的权重函数
        FilterFunctionBuilder[] functions = new FilterFunctionBuilder[]{
                new FilterFunctionBuilder(
                        QueryBuilders.termQuery("isAD", true),  // 过滤广告商品
                        ScoreFunctionBuilders.weightFactorFunction(1000) // 设置权重为 1000
                )
        };

        // 3. 组合 FunctionScore 查询
        // FunctionScoreQueryBuilder functionScoreQueryBuilder = new FunctionScoreQueryBuilder(mainQuery, functions);
        // functionScoreQueryBuilder.boostMode(CombineFunction.SUM);
        FunctionScoreQueryBuilder functionScoreQuery = QueryBuilders.functionScoreQuery(mainQuery, functions)
                .boostMode(CombineFunction.SUM); // 权重分与原始分相加

        // 4. 构建完整的搜索请求
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(functionScoreQuery);
        sourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.DESC)); // 按总分降序

        // 打印生成的 DSL 查询 JSON（调试用途）
        System.out.println(JSONUtil.toJsonPrettyStr(sourceBuilder.toString()));

        // 5. 执行搜索
        SearchRequest searchRequest = new SearchRequest("items");
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
        System.out.println("tearDown success");
    }
}
