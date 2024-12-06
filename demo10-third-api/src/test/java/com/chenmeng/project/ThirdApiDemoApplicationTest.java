package com.chenmeng.project;


import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.chenmeng.common.utils.OkHttpUtil;
import com.chenmeng.project.constants.XfyunUrlConstant;
import com.chenmeng.project.xfyun.image_recognition.service.Place;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * @author chenmeng
 */
@SpringBootTest(classes = ThirdApiDemoApplication.class)
class ThirdApiDemoApplicationTest {

    @Resource
    private MinioClient minioClient2;

    private static String requestUrl = XfyunUrlConstant.PLACE_RECOGNITION_URL;
    private static String appid = XfyunUrlConstant.APPID;
    private String apiSecret = XfyunUrlConstant.PLACE_RECOGNITION_API_SECRET;
    private String apiKey = XfyunUrlConstant.PLACE_RECOGNITION_API_KEY;

    public static final String BUCKET_NAME = "my-file";
    public static final String YOUR_OBJECT_KEY = "test2.jpg"; // "your-object-key"

    /**
     * 读取文件 base64 数据
     */
    @Test
    void test1() {
        System.out.println("getBase64() = " + getBase64());
    }

    @Test
    void test2() {
        try {
            String resp = doRequest();
            System.out.println("resp=>" + resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // {
    //   "header":{
    //     "code":0,
    //     "message":"success",
    //     "sid":"ase000d7ec7@hu193963c765a0427882"
    //   },
    //   "payload":{
    //     "result":{
    //       "compress":"raw",
    //       "encoding":"utf8",
    //       "format":"json",
    //       "text":"eyJwbGFjZSI6W3siZW50aXR5IjpbeyJpZCI6NTIsIm5hbWUiOiJjbGFzc3Jvb20iLCJzY29yZSI6MC45OTg4ODc3MTc3MjM4NDY0NH1dLCJmcmFtZUlEIjowLCJzdGFydFRpbWVPZmZzZXQiOjAuMH1dfQ=="
    //     }
    //   }
    // }

    /**
     * 请求主方法
     *
     * @return 返回服务结果
     * @throws Exception 异常
     */
    public String doRequest() throws Exception {
        URL realUrl = new URL(buildRequetUrl());
        URLConnection connection = realUrl.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type", "application/json");

        OutputStream out = httpURLConnection.getOutputStream();
        String params = buildParam();
        // System.out.println("params=>"+params);
        out.write(params.getBytes());
        out.flush();
        InputStream is = null;
        try {
            is = httpURLConnection.getInputStream();
        } catch (Exception e) {
            is = httpURLConnection.getErrorStream();
            throw new Exception("make request error:" + "code is " + httpURLConnection.getResponseMessage() + readAllBytes(is));
        }
        return readAllBytes(is);
    }

    private String readAllBytes(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * 处理请求URL
     * 封装鉴权参数等
     *
     * @return 处理后的URL
     */
    public String buildRequetUrl() {
        URL url = null;
        // 替换调schema前缀 ，原因是URL库不支持解析包含ws,wss schema的url
        String httpRequestUrl = requestUrl.replace("ws://", "http://").replace("wss://", "https://");
        try {
            url = new URL(httpRequestUrl);
            // 获取当前日期并格式化
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());
            String host = url.getHost();
			/*if (url.getPort()!=80 && url.getPort() !=443){
				host = host +":"+String.valueOf(url.getPort());
			}*/
            StringBuilder builder = new StringBuilder("host: ").append(host).append("\n").//
                    append("date: ").append(date).append("\n").//
                    append("POST ").append(url.getPath()).append(" HTTP/1.1");
            // System.out.println("host原始字段："+builder);
            Charset charset = Charset.forName("UTF-8");
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
            String sha = Base64.getEncoder().encodeToString(hexDigits);
            // System.out.println("加密后的signature为："+sha);

            String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
            String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));
            // System.out.println("authorization base64加密后:"+authBase);
            return String.format("%s?authorization=%s&host=%s&date=%s", requestUrl, URLEncoder.encode(authBase), URLEncoder.encode(host), URLEncoder.encode(date));

        } catch (Exception e) {
            throw new RuntimeException("assemble requestUrl error:" + e.getMessage());
        }
    }

    private String buildParam() throws Exception {
        // String base64 = Base64.getEncoder().encodeToString(read(IMAGE_PATH));
        // String base64 = FileUtil.fileToBase64(IMAGE_URL);
        String base64 = getBase64();


        String param = "{" +
                "    \"header\": {" +
                "        \"app_id\": \"" + appid + "\"," +
                "        \"status\": 3" +
                "    }," +
                "    \"parameter\": {" +
                "        \"s5833e7f6\": {" +
                "            \"func\": \"image/place\"," +
                "            \"result\": {" +
                "                \"encoding\": \"utf8\"," +
                "                \"compress\": \"raw\"," +
                "                \"format\": \"json\"" +
                "            }" +
                "        }" +
                "    }," +
                "    \"payload\": {" +
                "        \"data1\": {" +
                "            \"encoding\": \"jpg\"," +
                "            \"image\": \"" + base64 + "\"," +
                "            \"status\": 3" +
                "        }" +
                "    }" +
                "}";
        return param;
    }

    public static void main(String[] args) throws Exception {
        Place demo = new Place();
        try {

            // String param = "{" +
            //         "    \"header\": {" +
            //         "        \"app_id\": \"" + appid + "\"," +
            //         "        \"status\": 3" +
            //         "    }," +
            //         "    \"parameter\": {" +
            //         "        \"s5833e7f6\": {" +
            //         "            \"func\": \"image/place\"," +
            //         "            \"result\": {" +
            //         "                \"encoding\": \"utf8\"," +
            //         "                \"compress\": \"raw\"," +
            //         "                \"format\": \"json\"" +
            //         "            }" +
            //         "        }" +
            //         "    }," +
            //         "    \"payload\": {" +
            //         "        \"data1\": {" +
            //         "            \"encoding\": \"jpg\"," +
            //         "            \"image\": \"" + base64 + "\"," +
            //         "            \"status\": 3" +
            //         "        }" +
            //         "    }" +
            //         "}";
            //
            // String string = OkHttpUtil.sendPostJson(requestUrl, param);
            // System.out.println("string=>" + string);


            String resp = demo.doRequest();
            System.out.println("resp=>" + resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getBase64() {
        try {
            // 获取对象
            InputStream object = minioClient2.getObject(GetObjectArgs.builder()
                    .bucket("my-file")
                    .object("test2.jpg")
                    .build());

            byte[] bytes = IoUtil.readBytes(object);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}