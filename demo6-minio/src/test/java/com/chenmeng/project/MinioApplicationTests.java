package com.chenmeng.project;

import cn.hutool.core.io.IoUtil;
import com.chenmeng.project.service.MinioService;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class MinioApplicationTests {

    @Resource
    private MinioService minioService;

    @Resource
    private MinioClient minioClient;

    public static final String BUCKET_NAME = "my-file";
    public static final String YOUR_OBJECT_KEY = "your-object-key";

    @Test
    void contextLoads() {
        minioService.testMinioClient();
    }

    //-----------------------------------Bucket(文件夹)----------------------------------------

    /**
     * 判断桶是否存在
     */
    @Test
    void test01() throws Exception {
        boolean isBucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket("my-file")
                .build()
        );
        System.out.println("myfile目录是否存在：" + isBucketExists);
    }

    /**
     * 判断桶是否存在，不存在则创建桶，并且配置桶的访问策略
     */
    @Test
    void test02() throws Exception {
        String bucketName = "my-file";
        // 判断桶是否存在
        boolean isBucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build()
        );

        if (!isBucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build()
            );
        } else {
            System.out.println("bucket已经存在，不需要创建.");
        }

        // 配置桶的访问策略
        // 访问策略配置 json 串，版本号需根据官方的来（web 管理后台有）
        String policyJsonString = "{\"Version\" : \"2012-10-17\",\"Statement\":[{\"Sid\":\"PublicRead\",\"Effect\":\"Allow\",\"Principal\":{\"AWS\":\"*\"},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]}]}";
        // 创建存储桶的时候，设置该存储桶里面的文件的访问策略，运行公开的读；
        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                .bucket(bucketName)
                .config(policyJsonString) // json串，里面是访问策略
                .build()
        );
    }

    /**
     * 列出所有桶
     */
    @Test
    void test03() throws Exception {
        List<Bucket> bucketList = minioClient.listBuckets();
        bucketList.forEach(bucket -> {
            System.out.println(bucket.name() + " -- " + bucket.creationDate());
        });
    }

    /**
     * 删除桶
     */
    @Test
    void test04() throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder()
                .bucket("my-file")
                .build()
        );
    }

    //-----------------------------------Object(文件)----------------------------------------

    /**
     * 上传文件
     */
    @Test
    void test05() throws Exception {
        String fileName = "D:\\IDEA-code\\ok\\chenmeng-test-demos\\minio-demo\\src\\main\\resources\\file\\test2.jpg";

        // 方法一
        // File file = new File(fileName);
        // ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
        //         .bucket("my-file")
        //         .object("test.jpg")
        //         // .stream(new FileInputStream(file), file.length(), -1)
        //         .stream(Files.newInputStream(file.toPath()), file.length(), -1)
        //         .build()
        // );
        // System.out.println(objectWriteResponse);

        // 方法二
        ObjectWriteResponse objectWriteResponse2 = minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket("my-file")
                .object("test2.jpg")
                .filename(fileName)
                .build()
        );
        System.out.println(objectWriteResponse2);
    }

    /**
     * 检查指定文件状态
     */
    @Test
    void test06() throws Exception {
        StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder()
                .bucket("my-file")
                .object("test.jpg")
                .build()
        );
        System.out.println(statObjectResponse);
    }

    /**
     * 获取文件下载链接
     */
    @Test
    void test07() throws Exception {
        String presignedObjectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket("my-file")
                .object("test.jpg")
                // 设置过期时间为3分钟
                .expiry(3, TimeUnit.MINUTES)
                .method(Method.GET)
                .build()
        );
        System.out.println(presignedObjectUrl);
    }

    /**
     * 下载文件
     */
    @Test
    void test08() throws Exception {
        // 访问链接下载
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket("my-file")
                        .object("test.jpg")
                        .filename("test.jpg")
                        // .ssec() // 加密
                        .build()
        );
    }

    /**
     * 下载文件到指定目录下
     */
    @Test
    void test08_1() throws Exception {
        // 将数据传输到指定的文件输出流中
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                .bucket("my-file")
                .object("test.jpg")
                .build());
             FileOutputStream fileOutputStream = new FileOutputStream("D:\\MinIO\\123.jpg")) {
            IoUtil.copy(stream, fileOutputStream);
        }
    }

    /**
     * 获取文件列表
     */
    @Test
    void test09() {
        Iterable<Result<Item>> listObjects = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket("my-file")
                .build()
        );

        listObjects.forEach(itemResult -> {
            try {
                Item item = itemResult.get();
                System.out.println(item.objectName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 删除文件
     */
    @Test
    void test10() throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket("my-file")
                .object("test.jpg")
                .build()
        );
    }

    /**
     * 上传字符串
     */
    @Test
    void test11() throws Exception {
        String stringData = "This is a string data to be uploaded.";
        byte[] bytes = stringData.getBytes();

        try {
            // 创建一个字节数组输入流
            InputStream inputStream = new ByteArrayInputStream(bytes);

            // 上传对象
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(YOUR_OBJECT_KEY)
                    .contentType("application/json")
                    .stream(inputStream, bytes.length, -1)
                    .build();

            minioClient.putObject(objectArgs);
        } catch (MinioException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取字符串文件数据
     */
    @Test
    void test12() {
        try {
            // 获取对象
            InputStream object = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(YOUR_OBJECT_KEY)
                    .build());

            // 读取数据
            StringBuilder stringBuilder = new StringBuilder();
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = object.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, bytesRead));
            }

            // 关闭流
            object.close();

            // 输出字符串数据
            System.out.println(stringBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
