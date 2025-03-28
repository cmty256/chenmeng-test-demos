package com.chenmeng.project.xfyun.image_recognition.service;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.chenmeng.common.utils.OkHttpUtil;
import com.chenmeng.project.constants.XfyunUrlConstant;
import com.chenmeng.project.xfyun.image_recognition.utils.FileUtil;
import com.google.gson.Gson;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import org.springframework.stereotype.Service;

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
 * 场所识别（根据 URL）
 * <p>
 * 场所识别接口地址 http(s)://api.xf-yun.com/v1/private/s5833e7f6；
 * appid、apiSecret、apiKey请到讯飞开放平台控制台获取；
 * 图像数据,base64编码后最不得大于4M；
 * </p>
 *
 * @author chenmeng
 */
@Service
public class Place {

    @Resource
    private MinioClient minioClient;

    private static String requestUrl = XfyunUrlConstant.PLACE_RECOGNITION_URL;
    // 控制台获取以下信息
    private static String appid = XfyunUrlConstant.APPID;
    private String apiSecret = XfyunUrlConstant.PLACE_RECOGNITION_API_SECRET;
    private String apiKey = XfyunUrlConstant.PLACE_RECOGNITION_API_KEY;
    // 图片存放位置
    private static String IMAGE_PATH = "D:\\codes\\ok\\chenmeng-test-demos\\demo10-third-api\\src\\main\\resources\\image\\教室.jpg";
    // 斑马图片url
    private static String IMAGE_URL = "http://scj.yuexiu.gov.cn:8082/monitoring-platform/a8fa255fe4ce37d9f0dc6f07fd99ecb61d517c68a9efbe117f3020336ad67ccc.jpg";


    // 解析Json
    private static Gson json = new Gson();

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
            JsonParse myJsonParse = json.fromJson(resp, JsonParse.class);
            String textBase64Decode = new String(Base64.getDecoder().decode(myJsonParse.payload.result.text), "UTF-8");
            JSONObject jsonObject = JSON.parseObject(textBase64Decode);
            System.out.println("text字段Base64解码后=>" + jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // {
    //   "header":{
    //     "code":0,
    //     "message":"success",
    //     "sid":"ase000f1da9@hu1938bafe74e05c2882"
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

    // {
    //   "place":[
    //     {
    //       "entity":[
    //         {
    //           "id":52,
    //           "name":"classroom",
    //           "score":0.9988877177238464
    //         }
    //       ],
    //       "frameID":0,
    //       "startTimeOffset":0.0
    //     }
    //   ]
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

    /**
     * 组装请求参数
     * 直接使用示例参数，
     * 替换部分值
     *
     * @return 参数字符串
     */
    private String buildParam() throws Exception {
        // String base64 = Base64.getEncoder().encodeToString(read(IMAGE_PATH));
        String base64 = FileUtil.fileUrlToBase64(IMAGE_URL);
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
        System.out.println("base64: " + base64);
        return param;
    }


    /**
     * 读取流数据
     *
     * @param is 流
     * @return 字符串
     * @throws IOException 异常
     */
    private String readAllBytes(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        return br.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    public static byte[] read(String filePath) throws IOException {
        InputStream in = new FileInputStream(filePath);
        byte[] data = inputStream2ByteArray(in);
        in.close();
        return data;
    }

    private static byte[] inputStream2ByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    // Json解析
    class JsonParse {
        public Header header;
        public Payload payload;
    }

    class Header {
        public int code;
        public String message;
        public String sid;
    }

    class Payload {
        public Result result;
    }

    class Result {
        public String compress;
        public String encoding;
        public String format;
        public String text;
    }
}


