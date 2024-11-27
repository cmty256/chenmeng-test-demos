package com.chenmeng.project.xfyun.image_understand;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chenmeng.project.constants.XfyunUrlConstant;
import com.chenmeng.project.xfyun.image_understand.pojo.JsonParse;
import com.chenmeng.project.xfyun.image_understand.pojo.RoleContent;
import com.google.gson.Gson;
import okhttp3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 图片理解 WebApi 测试
 *
 * @author chenmeng
 */
public class BigModelNew extends WebSocketListener {
    // 地址与鉴权信息  https://spark-api.xf-yun.com/v1.1/chat   1.5地址  domain参数为general
    // 地址与鉴权信息  https://spark-api.xf-yun.com/v2.1/chat   2.0地址  domain参数为generalv2
    public static final String hostUrl = XfyunUrlConstant.IMAGE_UNDERSTAND_URL;
    public static final String appid = XfyunUrlConstant.APPID;
    public static final String apiSecret = XfyunUrlConstant.MODEL_API_SECRET;
    public static final String apiKey = XfyunUrlConstant.MODEL_API_KEY;

    String img1 = "D:\\codes\\ok\\chenmeng-test-demos\\demo10-third-api\\src\\main\\resources\\image\\2.png";

    // 对话历史存储集合
    public static List<RoleContent> historyList = new ArrayList<>();

    // 大模型的答案汇总
    public static String totalAnswer = "";

    // 环境治理的重要性  环保  人口老龄化  我爱我的祖国
    public static String NewQuestion = "";
    // 判断是否添加了图片信息
    public static Boolean ImageAddFlag = false;

    public static final Gson gson = new Gson();

    // 个性化参数
    private String userId;
    private Boolean wsCloseFlag;

    // 控制提示用户是否输入
    private static Boolean totalFlag = true;

    // 构造函数
    public BigModelNew(String userId, Boolean wsCloseFlag) {
        this.userId = userId;
        this.wsCloseFlag = wsCloseFlag;
    }

    // 主函数
    public static void main(String[] args) throws Exception {
        // 个性化参数入口，如果是并发使用，可以在这里模拟
        while (true) {
            if (totalFlag) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("我：");
                totalFlag = false;
                NewQuestion = scanner.nextLine();
                // 构建鉴权url
                String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
                OkHttpClient client = new OkHttpClient.Builder().build();
                String url = authUrl
                        .replace("http://", "ws://")
                        .replace("https://", "wss://");
                // System.err.println(url);
                Request request = new Request
                        .Builder()
                        .url(url)
                        .build();
                for (int i = 0; i < 1; i++) {
                    totalAnswer = "";
                    WebSocket webSocket = client.newWebSocket(request, new BigModelNew(i + "", false));
                }
            } else {
                Thread.sleep(200);
            }
        }
    }

    // 线程来发送音频与参数
    class MyThread extends Thread {
        private WebSocket webSocket;

        public MyThread(WebSocket webSocket) {
            this.webSocket = webSocket;
        }

        @Override
        public void run() {
            try {
                JSONObject requestJson = new JSONObject();

                JSONObject header = new JSONObject();  // header参数
                header.putOnce("app_id", appid);
                header.putOnce("uid", UUID.randomUUID().toString().substring(0, 10));

                JSONObject parameter = new JSONObject(); // parameter参数
                JSONObject chat = new JSONObject();
                chat.putOnce("domain", "image");
                chat.putOnce("temperature", 0.5);
                chat.putOnce("max_tokens", 4096);
                chat.putOnce("auditing", "default");
                parameter.putOnce("chat", chat);

                JSONObject payload = new JSONObject(); // payload参数
                JSONObject message = new JSONObject();
                JSONArray text = new JSONArray();

                // 历史问题获取
                if (historyList.size() > 0) { // 保证首个添加的是图片
                    for (RoleContent tempRoleContent : historyList) {
                        if (tempRoleContent.getContent_type().equals("image")) { // 保证首个添加的是图片
                            text.add(JSONUtil.parse(tempRoleContent));
                            ImageAddFlag = true;
                        }
                    }
                }
                if (historyList.size() > 0) {
                    for (RoleContent tempRoleContent : historyList) {
                        if (!tempRoleContent.getContent_type().equals("image")) { // 添加费图片类型
                            text.add(JSONUtil.parse(tempRoleContent));
                        }
                    }
                }

                // 最新问题
                RoleContent roleContent = new RoleContent();
                // 添加图片信息
                if (!ImageAddFlag) {
                    roleContent.setRole("user");
                    roleContent.setContent(Base64.getEncoder().encodeToString(ImageUtil.read(img1)));
                    roleContent.setContent_type("image");
                    text.add(JSONUtil.parse(roleContent));
                    historyList.add(roleContent);
                }
                // 添加对图片提出要求的信息
                RoleContent roleContent1 = new RoleContent();
                roleContent1.setRole("user");
                roleContent1.setContent(NewQuestion);
                roleContent1.setContent_type("text");
                text.add(JSONUtil.parse(roleContent1));
                historyList.add(roleContent1);


                message.put("text", text);
                payload.put("message", message);


                requestJson.put("header", header);
                requestJson.put("parameter", parameter);
                requestJson.put("payload", payload);
                // System.err.println(requestJson); // 可以打印看每次的传参明细
                webSocket.send(requestJson.toString());
                // 等待服务端返回完毕后关闭
                while (true) {
                    // System.err.println(wsCloseFlag + "---");
                    Thread.sleep(200);
                    if (wsCloseFlag) {
                        break;
                    }
                }
                webSocket.close(1000, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        System.out.print("大模型：");
        MyThread myThread = new MyThread(webSocket);
        myThread.start();
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        // System.out.println(userId + "用来区分那个用户的结果" + text);
        JsonParse myJsonParse = gson.fromJson(text, JsonParse.class);
        if (myJsonParse.getHeader().getCode() != 0) {
            System.out.println("发生错误，错误码为：" + myJsonParse.getHeader());
            System.out.println("本次请求的sid为：" + myJsonParse.getHeader().getSid());
            webSocket.close(1000, "");
        }
        List<JsonParse.Text> textList = myJsonParse.getPayload().getChoices().getText();
        for (JsonParse.Text temp : textList) {
            System.out.print(temp.getContent());
            totalAnswer = totalAnswer + temp.getContent();
        }
        if (myJsonParse.getHeader().getStatus() == 2) {
            // 可以关闭连接，释放资源
            System.out.println();
            System.out.println("*************************************************************************************");
            if (canAddHistory()) {
                RoleContent roleContent = new RoleContent();
                roleContent.setRole("assistant");
                roleContent.setContent(totalAnswer);
                roleContent.setContent_type("text");
                historyList.add(roleContent);
            } else {
                historyList.remove(0);
                RoleContent roleContent = new RoleContent();
                roleContent.setRole("assistant");
                roleContent.setContent(totalAnswer);
                roleContent.setContent_type("text");
                historyList.add(roleContent);
            }
            wsCloseFlag = true;
            totalFlag = true;
        }
    }

    public static boolean canAddHistory() {  // 由于历史记录最大上线1.2W左右，需要判断是能能加入历史
        int historyLength = 0;
        for (RoleContent temp : historyList) {
            historyLength = historyLength + temp.getContent().length();
        }
        // System.out.println(historyLength);
        // 这里限制了总上下文携带，图片理解注意放大 ！！！
        if (historyLength > 1200000000) {
            historyList.remove(0);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        try {
            if (null != response) {
                int code = response.code();
                System.out.println("onFailure code:" + code);
                System.out.println("onFailure body:" + response.body().string());
                if (101 != code) {
                    System.out.println("connection failed");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 鉴权方法
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());

        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // host: spark-api.cn-huabei-1.xf-yun.com
        // date: Wed, 27 Nov 2024 06:50:05 GMT
        // GET /v2.1/image HTTP/1.1

        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);

        // 拼接 -- api_key="xxxxx", algorithm="hmac-sha256", headers="host date request-line", signature="xxxxx"
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // System.err.println(authorization);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath()))
                .newBuilder()
                .addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8)))
                .addQueryParameter("date", date)
                .addQueryParameter("host", url.getHost())
                .build();

        // System.err.println(httpUrl);
        return httpUrl.toString();
    }

    public static void main2(String[] args) throws Exception {
        // 构建鉴权url
        String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
    }
}