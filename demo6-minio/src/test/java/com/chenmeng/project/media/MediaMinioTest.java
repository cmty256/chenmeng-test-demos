package com.chenmeng.project.media;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 大文件上传，不分块
 * statObject 获取元数据属性的校验示例不适合于 大文件分块上传的场景
 *
 * @author chenmeng
 */
@SpringBootTest
public class MediaMinioTest {

    @Resource
    private MinioClient minioClient;

    // 待上传的文件路径
    String filePath = "D:\\develop\\data\\1.mp4";
    // MinIO存储桶名称
    String bucketName = "test_bucket";
    // 存储桶中的对象名称
    String objectName = "1.mp4";

    /**
     * 上传文件
     */
    @Test
    public void testUpload() throws Exception {

        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".mp4");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;  // 通用 mimeType 字节流


        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }

        try {

            // 检查文件桶是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // 不存在，则创建文件桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("文件桶已存在！！！");
            }

            // 上传文件
            uploadFile(minioClient, filePath, bucketName, objectName, mimeType);

            // 上传文件完整性校验
            boolean flag = checkFileIntegrity(minioClient, filePath, bucketName, objectName);

            System.out.println(flag ? "上传文件成功了！！！" : "上传文件失败了！！！");

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }


    }


    /**
     * 删除文件
     */
    @Test
    public void testDelete() throws Exception {

        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object("1.MP4").build();

        minioClient.removeObject(removeObjectArgs);

    }


    /**
     * 查询文件，下载到本地
     */
    @Test
    public void testGetObject() throws Exception {

        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();

        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);

        String localFileName = filePath;

        // 指定输出流
        FileOutputStream outputStream = new FileOutputStream(localFileName);
        IOUtils.copy(inputStream, outputStream);

        // md5完整性校验
        boolean flag = checkFileIntegrity(minioClient, localFileName, bucketName, objectName);


        if (flag) {
            System.out.println("下载成功了！！！");
        } else {
            System.out.println("下载失败了！！！");
        }


    }


    /**
     * 上传文件
     */
    public void uploadFile(MinioClient minioClient, String filePath, String bucketName, String objectName, String contentType) throws Exception {

        // 计算上传前本地文件MD5
        String uploadLocalFileMD5 = calculateMD5(filePath);
        System.out.println("上传前本地文件MD5: uploadLocalFileMD5=" + uploadLocalFileMD5);
        Map<String, String> md5Map = new HashMap<>();
        md5Map.put("md5", uploadLocalFileMD5);


        // 上传文件到 MinIO
        File file = new File(filePath);
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(new FileInputStream(file), file.length(), -1)
                        .userMetadata(md5Map)
                        .contentType(contentType)
                        .build()
        );
        System.out.println("File uploaded successfully: " + objectName);

    }


    /**
     * 计算md5
     */
    public String calculateMD5(String filePath) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return DigestUtils.md5Hex(fileInputStream);
    }


    /**
     * 对比本地文件和minio文件的MD5值是否一致，校验文件完整性
     */

    public boolean checkFileIntegrity(MinioClient minioClient, String filePath, String bucketName, String objectName) throws Exception {


        // 计算本地文件的MD5
        String localFileMD5 = calculateMD5(filePath);
        System.out.println("Local file MD5: " + localFileMD5);

        // 获取MinIO中对象的MD5
        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());

        String minioFileMD5 = stat.userMetadata().get("md5");
        System.out.println("MinIO file MD5: " + minioFileMD5);

        // 比较MD5值
        return localFileMD5.equals(minioFileMD5);
    }
}

