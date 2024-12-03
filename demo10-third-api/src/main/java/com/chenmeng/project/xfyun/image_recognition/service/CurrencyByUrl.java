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
 * 物体识别（根据 URL）
 * <p>
 * 图片数据可以通过两种方式上传，第一种在请求头设置image_url参数，第二种将图片二进制数据写入请求体中。若同时设置，以第一种为准。
 * 1.使用二进制数据写入请求体时，不需要在header中传递image_url参数
 * 2.使用传递url参数时，请求体为空即可
 * 具体请参考接口文档：<a href="https://doc.xfyun.cn/rest_api/">...</a>
 * </p>
 *
 * @author chenmeng
 */
public class CurrencyByUrl {

    // 图片名称
    private static final String IMAGE_NAME = "img.jpg";
    // 斑马图片url
    private static final String IMAGE_URL_1 = "http://scj.yuexiu.gov.cn:8082/monitoring-platform/dcda1429c1b45367f8d1d1de035c60bd33a60e2c01465b32ff83dfb8e37955ec.jpg";

    /**
     * WebAPI 调用示例程序
     */
    public static void main(String[] args) {
        Map<String, String> header = buildHttpHeader();
        String result = HttpUtil.doPost1(XfyunUrlConstant.CURRENCY_RECOGNITION_URL, header, new byte[1]);
        System.out.println("接口调用结果：" + JSONUtil.toJsonPrettyStr(result));
    }

    /**
     * 组装http请求头（授权认证）
     */
    private static Map<String, String> buildHttpHeader() {
        String curTime = System.currentTimeMillis() / 1000L + "";
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("image_name", IMAGE_NAME);
        hashMap.put("image_url", IMAGE_URL_1);
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

    // 接口调用结果：{
    //     "code": 0,
    //     "data": {
    //         "fileList": [
    //             {
    //                 "code": 0,
    //                 "file_name": "/cloud-api/storage-weed233/2024-12-03/16-7/5ba9e2178906cca4027dddf5/17332140357930.4248849296330288.jpeg",
    //                 "label": 15765,
    //                 "labels": [
    //                     15765,
    //                     19896,
    //                     4,
    //                     11797,
    //                     3923
    //                 ],
    //                 "name": "http://scj.yuexiu.gov.cn:8082/monitoring-platform/dcda1429c1b45367f8d1d1de035c60bd33a60e2c01465b32ff83dfb8e37955ec.jpg",
    //                 "rate": 0.32277753949165344,
    //                 "rates": [
    //                     0.32277753949165344,
    //                     0.28426995873451233,
    //                     0.17071975767612457,
    //                     0.1364746242761612,
    //                     0.02689996175467968
    //                 ],
    //                 "review": true,
    //                 "tag": "Using url"
    //             }
    //         ],
    //         "reviewCount": 1,
    //         "topNStatistic": [
    //             {
    //                 "count": 1,
    //                 "label": 15765
    //             }
    //         ]
    //     },
    //     "desc": "success",
    //     "sid": "tup00017c17@dx435f1aaa0b53a00100"
    // }
}
