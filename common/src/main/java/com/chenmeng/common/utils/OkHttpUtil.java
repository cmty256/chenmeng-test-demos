package com.chenmeng.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author chenmeng
 */
@SuppressWarnings("all")
public class OkHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger("okHttpReq");

    private static final long DEFAULT_TIMEOUT = 5000;
    private static final int MAX_IDLE_CONNECTION = 5;
    private static final long KEEP_ALIVE_DURATION = 1;
    public static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";
    public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

    private OkHttpUtil() {
        // 单例模式，防止外部实例化
    }

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
            .callTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectionPool(new ConnectionPool(MAX_IDLE_CONNECTION, KEEP_ALIVE_DURATION, TimeUnit.MINUTES))
            .build();

    /**
     * 发送GET请求
     *
     * @param url 请求URL
     * @return 响应结果字符串
     */
    public static String sendGet(String url) {
        return sendGetWithHeaders(url, null, null);
    }

    /**
     * 携带参数发送GET请求
     *
     * @param url   请求URL
     * @param param 请求参数
     * @return 响应结果字符串
     */
    public static String sendGetWithParam(String url, String param) {
        return sendGetWithHeaders(url, param, null);
    }

    /**
     * 携带请求头发送GET请求
     *
     * @param url     请求URL
     * @param param   请求参数
     * @param headers 请求头（可选）
     * @return 响应结果字符串
     */
    public static String sendGetWithHeaders(String url, String param, Map<String, String> headers) {
        String realUrl = url;
        if (StrUtil.isNotBlank(param)) {
            realUrl = url + "?" + param;
        }
        Request.Builder builder = new Request.Builder()
                .url(realUrl)
                .header("accept", "*/*")
                .header("connection", "Keep-Alive")
                .get();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // 处理响应数据
                long time = response.receivedResponseAtMillis() - response.sentRequestAtMillis();
                String result = response.body() != null ? response.body().string() : null;
                printRequestLog(url, null, param, time, result);
                return result;
            } else {
                // 处理错误响应
                // return response.toString();
                throw new IOException(String.valueOf(response));
            }
        } catch (Exception e) {
            // 处理网络异常
            logger.error("invoke Remote 【GET】 Method exception！== url【{}】，param【{}】", url, param);
            return returnErrorResult("远程请求失败：" + e.getMessage()).toString();
        }
    }

    /**
     * 发送POST请求（application/json; charset=utf-8）
     *
     * @param url   请求URL
     * @param param JSON字符串请求体
     * @return 响应结果字符串
     */
    public static String sendPostJson(String url, String param) {
        return sendPostJsonWithHeaders(url, param, null);
    }

    /**
     * 携带请求头发送POST请求（application/json）
     *
     * @param url     请求URL
     * @param param   JSON字符串请求体
     * @param headers 请求头（可选）
     * @return 响应结果字符串
     */
    public static String sendPostJsonWithHeaders(String url, String param, Map<String, String> headers) {
        MediaType mediaType = MediaType.parse(JSON_CONTENT_TYPE);
        RequestBody requestBody = RequestBody.create(param, mediaType);

        Request.Builder builder = new Request.Builder()
                .url(url)
                .header("accept", "*/*")
                .header("connection", "Keep-Alive")
                .header("Content-Type", JSON_CONTENT_TYPE)
                .post(requestBody);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // 处理响应数据
                long time = response.receivedResponseAtMillis() - response.sentRequestAtMillis();
                String result = response.body() != null ? response.body().string() : null;
                printRequestLog(url, JSON_CONTENT_TYPE, param, time, result);
                return result;
            } else {
                // 处理错误响应
                throw new IOException(String.valueOf(response));
            }
        } catch (Exception e) {
            // 处理网络异常
            logger.error("invoke Remote 【POST】 Method exception！== url【{}】，param【{}】", url, param, e);
            return returnErrorResult("Remote Request Fail--" + e.getMessage()).toString();
        }
    }

    /**
     * 发送POST请求（application/x-www-form-urlencoded）
     *
     * @param url    请求URL
     * @param params 请求参数（可选）
     * @return 响应结果字符串
     */
    public static String sendPostForm(String url, Map<String, Object> params) {
        return sendPostFormWithHeaders(url, params, null);
    }

    /**
     * 携带请求头发送POST请求（application/x-www-form-urlencoded）
     *
     * @param url     请求URL
     * @param params  请求参数（可选）
     * @param headers 请求头（可选）
     * @return 响应结果字符串
     */
    public static String sendPostFormWithHeaders(String url, Map<String, Object> params,
                                                 Map<String, String> headers) {
        RequestBody formBody;
        StringBuilder strParamsBuilder = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue().toString());
            }
            formBody = formBuilder.build();
            // 打印参数
            for (Map.Entry<String, Object> e : params.entrySet()) {
                strParamsBuilder.append(e.getKey()).append("=").append(e.getValue()).append("&");
            }
        } else {
            // 若无参数，创建一个空的FormBody
            formBody = new FormBody.Builder().build();
        }
        String strParams = strParamsBuilder.toString();

        Request.Builder builder = new Request.Builder()
                .url(url)
                .header("accept", "*/*")
                .header("connection", "Keep-Alive")
                .header("Content-Type", FORM_CONTENT_TYPE)
                .post(formBody);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.build();

        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // 处理响应数据
                long time = response.receivedResponseAtMillis() - response.sentRequestAtMillis();
                String result = response.body() != null ? response.body().string() : null;
                printRequestLog(url, FORM_CONTENT_TYPE, strParams, time, result);
                return result;
            } else {
                // 处理错误响应
                throw new IOException(String.valueOf(response));
            }
        } catch (Exception e) {
            // 处理网络异常
            logger.error("invoke Remote 【POST】 Method exception！== url【{}】，param【{}】", url, strParams);
            return returnErrorResult("Remote Request Fail :" + e.getMessage()).toString();
        }
    }

    public static void printRequestLog(
            String url, String contentType, String data, Long time, String result) {
        String log =
                "\n================== Remote Request info ==================\n"
                        + String.format("Request URL: %s \n", url)
                        + String.format("Request Content-Type: %s\n", contentType)
                        + String.format("Request Parameter: %s \n", data)
                        + String.format("Request Time: %s ms \n", time)
                        + String.format("Response Result: %s \n", result)
                        + "================== Remote Request info ==================\n";
        logger.info(log);
    }

    public static JSONObject returnErrorResult(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("code", 1);
        jsonObject.putOpt("msg", msg);
        jsonObject.putOpt("data", null);
        return jsonObject;
    }
}
