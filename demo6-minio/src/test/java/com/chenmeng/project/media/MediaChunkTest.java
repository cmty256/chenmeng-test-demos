package com.chenmeng.project.media;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 大文件上传，分块
 *
 * @author chenmeng
 */
@Slf4j
@SpringBootTest
public class MediaChunkTest {

    @Resource
    private MinioClient minioClient;

    String bucketName = "test_bucket";
    String localPath = "D:\\develop\\data\\1.mp4";
    String outPath = "D:\\develop\\data\\1a.mp4";
    String chunkPath = "D:\\develop\\data\\chunk\\";

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\upload\\1.mp4";
        System.out.println(path);
    }

    @Test
    public void test_upload() throws Exception {
        // 通过扩展名得到媒体资源类型 mimeType
        // 根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".mp4");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;// 通用mimeType，字节流
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }

        // 上传文件的参数信息
        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                .bucket(bucketName)// 桶
                .filename(localPath) // 指定本地文件路径
//                .object("1.mp4")//对象名 在桶下存储该文件
                .object("test/01/1.mp4")// 对象名 放在子目录下
                .contentType(mimeType)// 设置媒体文件类型
                .build();

        // 上传文件
        minioClient.uploadObject(uploadObjectArgs);
    }

    // 删除文件
    @Test
    public void test_delete() throws Exception {

        // RemoveObjectArgs
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object("test/01/1.mp4").build();

        // 删除文件
        minioClient.removeObject(removeObjectArgs);


    }

    // 查询文件 从minio中下载
    @Test
    public void test_getFile() throws Exception {

        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(bucketName).object("test/01/1.mp4").build();
        // 查询远程服务获取到一个流对象
        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
        // 指定输出流
        FileOutputStream outputStream = new FileOutputStream(outPath);
        IOUtils.copy(inputStream, outputStream);

        // 校验文件的完整性对文件的内容进行md5
        FileInputStream fileInputStream1 = new FileInputStream(localPath);
        String source_md5 = DigestUtils.md5Hex(fileInputStream1);
        FileInputStream fileInputStream = new FileInputStream(outPath);
        String local_md5 = DigestUtils.md5Hex(fileInputStream);
        if (source_md5.equals(local_md5)) {
            System.out.println("下载成功");
        }

    }


    // 将分块文件上传到minio
    @SneakyThrows
    @Test
    public void uploadChunk() {

        for (int i = 0; i < 6; i++) {
            // 上传文件的参数信息
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(bucketName)// 桶
                    .filename(chunkPath + i) // 指定本地文件路径
                    .object("chunk/" + i)// 对象名 放在子目录下
                    .build();

            // 上传文件
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传分块" + i + "成功");
        }

    }

    // 调用minio接口合并分块
    @Test
    public void testMerge() throws Exception {
        int chunkTotal = 6;

       // List<ComposeSource> sources = new ArrayList<>();
       // for (int i = 0; i < 6; i++) {
       //     //指定分块文件的信息
       //     ComposeSource composeSource = ComposeSource.builder().bucket(bucketName).object("chunk/" + i).build();
       //     sources.add(composeSource);
       // }

        List<ComposeSource> sources = Stream.iterate(0, i -> ++i)
                .limit(chunkTotal)
                .map(i -> ComposeSource.builder()
                        .bucket(bucketName)
                        .object("chunk/" + i)
                        .build())
                .collect(Collectors.toList());

        // 指定合并后的objectName等信息
        ComposeObjectArgs composeObjectArgs = ComposeObjectArgs.builder()
                .bucket(bucketName)
                .object("merge01.mp4")
                .sources(sources)// 指定源文件
                .build();
        // 合并文件,
        // 报错size 1048576 must be greater than 5242880，minio默认的分块文件大小为5M
        minioClient.composeObject(composeObjectArgs);


        // 下载合并后的文件到本地临时文件用于校验
        File mergedFile = File.createTempFile("merged", ".mp4");
        try (InputStream in = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object("merge01.mp4")
                        .build());
             FileOutputStream fos = new FileOutputStream(mergedFile)) {
            IOUtils.copy(in, fos);
        }

        // 计算合并后文件的MD5值
        String mergedFileMd5;
        try (FileInputStream fis = new FileInputStream(mergedFile)) {
            mergedFileMd5 = DigestUtils.md5Hex(fis);
        }
        System.out.println("合并后文件的MD5：" + mergedFileMd5);

        // 此处假设originalMd5为原始文件的MD5，实际中应从上传环节获得并保存
        String originalMd5 = "expectedMd5Value"; // 请替换成真实的MD5值

        // 比较合并后的MD5与原始MD5
        if (mergedFileMd5.equalsIgnoreCase(originalMd5)) {
            System.out.println("文件合并成功，MD5校验通过。");
        } else {
            System.err.println("文件合并后MD5校验失败！原始MD5：" + originalMd5 + ", 合并后MD5：" + mergedFileMd5);
            // 根据业务需求可以抛出异常或进行重试处理
            throw new RuntimeException("文件合并后MD5校验失败");
        }

        // 清理临时下载的文件
        if (mergedFile.exists()) {
            mergedFile.delete();
            System.out.println("清理临时下载的文件成功。");
        }

        // 批量清理分块文件
        clearChunkFiles(chunkPath, chunkTotal);
    }

    /**
     * 批量清理分块文件
     *
     * @param chunkFileFolderPath 分块文件所在目录，例如："a/b/文件MD5/chunk/"
     * @param chunkTotal          分块文件总数
     */
    private void clearChunkFiles(String chunkFileFolderPath, int chunkTotal) {
        try {
            // 构造待删除对象列表（假设分块索引从1开始，如果分块索引从0开始，请相应修改）
            List<DeleteObject> deleteObjects = Stream.iterate(1, i -> ++i)
                    .limit(chunkTotal)
                    .map(i -> new DeleteObject(chunkFileFolderPath.concat(Integer.toString(i))))
                    .collect(Collectors.toList());
            // 调用 removeObjects 批量删除
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                    RemoveObjectsArgs.builder()
                            .bucket(bucketName)  // 分块文件所在桶
                            .objects(deleteObjects)
                            .build());
            // 遍历结果，处理可能的删除错误
            results.forEach(result -> {
                try {
                    DeleteError error = result.get();
                    // 如果有删除错误，可以记录日志
                    log.error("删除分块文件出错：objectName={}, message={}", error.objectName(), error.message());
                } catch (Exception e) {
                    log.error("清理分块文件异常", e);
                }
            });
        } catch (Exception e) {
            log.error("清理分块文件失败, chunkFileFolderPath:{}", chunkFileFolderPath, e);
        }
    }
}
