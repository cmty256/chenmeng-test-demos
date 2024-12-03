package com.chenmeng.project.xfyun.image_recognition.service;

import cn.hutool.json.JSONUtil;
import com.chenmeng.project.constants.XfyunUrlConstant;
import com.chenmeng.project.xfyun.image_recognition.utils.FileUtil;
import com.chenmeng.project.xfyun.image_recognition.utils.HttpUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 场景识别（根据图片二进制数据）
 * <p>
 * 图片数据可以通过两种方式上传，第一种在请求头设置image_url参数，第二种将图片二进制数据写入请求体中。若同时设置，以第一种为准。
 * 1.使用二进制数据写入请求体时，不需要在header中传递image_url参数
 * 2.使用传递url参数时，请求体为空即可
 * 本例采用将图片二进制数据写入请求体中的方式
 * 具体请参考接口文档：<a href="https://doc.xfyun.cn/rest_api/">...</a>
 * </p>
 *
 * @author chenmeng
 */
public class SceneByPath {

    // 图片名称
    private static final String IMAGE_NAME = "img.jpg";
    // 本地图片地址
    private static final String PATH = "D:\\codes\\ok\\chenmeng-test-demos\\demo10-third-api\\src\\main\\resources\\image\\测试图片.jpg";

    /**
     * WebAPI 调用示例程序
     */
    public static void main(String[] args) throws IOException {
        Map<String, String> header = buildHttpHeader();
        byte[] imageByteArray = FileUtil.read(PATH);
        String result = HttpUtil.doPost1(XfyunUrlConstant.SCENE_RECOGNITION_URL, header, imageByteArray);
        System.out.println("接口调用结果：" + JSONUtil.toJsonPrettyStr(result));
    }

    /**
     * 组装http请求头（授权认证）
     */
    private static Map<String, String> buildHttpHeader() {
        String curTime = System.currentTimeMillis() / 1000L + "";
        String param = "{\"image_name\":\"" + IMAGE_NAME + "\"}";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes(StandardCharsets.UTF_8)));
        String checkSum = DigestUtils.md5Hex(XfyunUrlConstant.IMAGE_RECOGNITION_API_KEY + curTime + paramBase64);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Appid", XfyunUrlConstant.APPID);
        header.put("X-CurTime", curTime);
        header.put("X-Param", paramBase64);
        header.put("X-CheckSum", checkSum);
        return header;
    }

    // 接口调用结果：{
    //     "code": 0,
    //     "data": {
    //         "fileList": [
    //             {
    //                 "code": 0,
    //                 "file_name": "/cloud-api/storage-weed233/2024-11-21/14-7/5ba9e1ecb80487a3fda9f0ec/17321705165430.6783047242555185.png",
    //                 "label": 17,
    //                 "labels": [
    //                     17,
    //                     14,
    //                     0,
    //                     18,
    //                     3
    //                 ],
    //                 "name": "img.jpg",
    //                 "rate": 0.9870044589042664,
    //                 "rates": [
    //                     0.9870044589042664,
    //                     0.007473748177289963,
    //                     0.0028219418600201607,
    //                     0.0014721076004207134,
    //                     0.00031781531288288534
    //                 ],
    //                 "review": false
    //             }
    //         ],
    //         "reviewCount": 0,
    //         "topNStatistic": [
    //             {
    //                 "count": 1,
    //                 "label": 17
    //             }
    //         ]
    //     },
    //     "desc": "success",
    //     "sid": "tup00016624@dx435f1a9a1f14a00100"
    // }
}
