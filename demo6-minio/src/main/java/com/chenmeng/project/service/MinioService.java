package com.chenmeng.project.service;

import io.minio.MinioClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenmeng
 */
@Service
public class MinioService {

    @Resource
    private MinioClient minioClient;

    public void testMinioClient() {
        System.out.println(minioClient);
    }
}
