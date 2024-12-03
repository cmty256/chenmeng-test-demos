package com.chenmeng.project.xfyun.image_recognition.service;

import cn.hutool.json.JSONUtil;
import com.chenmeng.project.constants.XfyunUrlConstant;
import com.chenmeng.project.xfyun.image_recognition.utils.HttpUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 场景识别（根据 URL）
 * <p>
 * 图片数据可以通过两种方式上传，第一种在请求头设置image_url参数，第二种将图片二进制数据写入请求体中。若同时设置，以第一种为准。
 * 1.使用二进制数据写入请求体时，不需要在header中传递image_url参数
 * 2.使用传递url参数时，请求体为空即可
 * 具体请参考接口文档：<a href="https://doc.xfyun.cn/rest_api/">...</a>
 * </p>
 *
 * @author chenmeng
 */
public class SceneByUrl {

    // 图片名称
    private static final String IMAGE_NAME = "img.jpg";
    // 教室图片url
    private static final String IMAGE_URL_1 = "http://scj.yuexiu.gov.cn:8082/monitoring-platform/a8fa255fe4ce37d9f0dc6f07fd99ecb61d517c68a9efbe117f3020336ad67ccc.jpg";
    // 健身房图片url
    private static final String IMAGE_URL_2 = "http://scj.yuexiu.gov.cn:8082/monitoring-platform/31702cb4615058c2f4c778978b9fb662171c95e8d667be4be2aad8c16a4cd550.png";

    /**
     * WebAPI 调用示例程序
     */
    public static void main(String[] args) {
        Map<String, String> header = buildHttpHeader();
        String result = HttpUtil.doPost1(XfyunUrlConstant.SCENE_RECOGNITION_URL, header, new byte[1]);
        System.out.println("接口调用结果：" + JSONUtil.toJsonPrettyStr(result));
    }

    /**
     * 组装http请求头（授权认证）
     */
    private static Map<String, String> buildHttpHeader() {
        String curTime = System.currentTimeMillis() / 1000L + "";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("image_name", IMAGE_NAME);
        // hashMap.put("image_url", IMAGE_URL_1);
        hashMap.put("image_url", IMAGE_URL_2);
        String param = JSONUtil.toJsonStr(hashMap);
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

    // 教室图片识别结果
    // 接口调用结果：{
    //     "code": 0,
    //     "data": {
    //         "fileList": [
    //             {
    //                 "code": 0,
    //                 "file_name": "/cloud-api/storage-weed233/2024-12-03/15-7/5ba9e1ecb80487a3fda9f0ec/17332102582560.35090569914633907.png",
    //                 "label": 1,
    //                 "labels": [
    //                     1,
    //                     0,
    //                     18,
    //                     17,
    //                     5
    //                 ],
    //                 "name": "http://scj.yuexiu.gov.cn:8082/monitoring-platform/a8fa255fe4ce37d9f0dc6f07fd99ecb61d517c68a9efbe117f3020336ad67ccc.jpg",
    //                 "rate": 0.999876856803894,
    //                 "rates": [
    //                     0.999876856803894,
    //                     0.000058655910834204406,
    //                     0.000058359622926218435,
    //                     0.0000028751621812261874,
    //                     0.0000015962067436703364
    //                 ],
    //                 "review": false,
    //                 "tag": "Using url"
    //             }
    //         ],
    //         "reviewCount": 0,
    //         "topNStatistic": [
    //             {
    //                 "count": 1,
    //                 "label": 1
    //             }
    //         ]
    //     },
    //     "desc": "success",
    //     "sid": "tup001d6570@dx40af1aa9fc911aba00"
    // }


    // 健身房图片识别结果
    // 接口调用结果：{
    //     "code": 0,
    //     "data": {
    //         "fileList": [
    //             {
    //                 "code": 0,
    //                 "file_name": "/cloud-api/storage-weed233/2024-12-03/15-7/5ba9e1ecb80487a3fda9f0ec/17332103169400.9706856305009097.webp",
    //                 "label": 7,
    //                 "labels": [
    //                     7,
    //                     18,
    //                     19,
    //                     12,
    //                     17
    //                 ],
    //                 "name": "http://scj.yuexiu.gov.cn:8082/monitoring-platform/31702cb4615058c2f4c778978b9fb662171c95e8d667be4be2aad8c16a4cd550.png",
    //                 "rate": 0.3870721161365509,
    //                 "rates": [
    //                     0.3870721161365509,
    //                     0.32270362973213196,
    //                     0.09575910866260529,
    //                     0.0900701954960823,
    //                     0.03398699313402176
    //                 ],
    //                 "review": true,
    //                 "tag": "Using url"
    //             }
    //         ],
    //         "reviewCount": 1,
    //         "topNStatistic": [
    //             {
    //                 "count": 1,
    //                 "label": 7
    //             }
    //         ]
    //     },
    //     "desc": "success",
    //     "sid": "tup00017c03@dx435f1aa9fccca00100"
    // }
}
