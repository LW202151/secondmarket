package com.Secondgood.secondhang.good.util;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @program: backend
 * @description: COSUtil COS工具
 * @author: Vaskka
 * @create: 2019/7/3 11:07 AM
 **/
public class COSUtil {
    private static final String SECRET_ID = "AKID2mKl172jANL6i8lDtKYaRIxOaaorwE5h";

    private static final String SECRET_KEY = "WnpcBasdASVryqUt359sQjCUbS5ZWIp7";

    private static final String BUCKET_NAME = "edu-1255976561";

    private static final String REGION = "ap-chengdu";

    private static COSClient client;

    public static COSClient getInstance() {
        if (client == null) {
            COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
            Region region = new Region(REGION);
            ClientConfig clientConfig = new ClientConfig(region);
            client =  new COSClient(cred, clientConfig);
        }

        return client;
    }

    /**
     * 上传file输入流和对应的key
     * @param inputStream 输入流
     * @param key key
     */
    public static void uploadWithInputStream(InputStream inputStream, String key) throws IOException {

        ObjectMetadata objectMetadata = new ObjectMetadata();

        getInstance().putObject(BUCKET_NAME, key, inputStream, objectMetadata);

        inputStream.close();
    }

    /**
     * 上传本地file和对应的objectid
     * @param localFile File file
     * @param objectKey oid
     */
    public static void uploadWithLocalFile(File localFile, String objectKey) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, objectKey, localFile);
        getInstance().putObject(putObjectRequest);
    }

    /**
     * 从objectid换取url
     * @param key objectid
     * @return String url
     */
    public static String getUrlFromObjectKey(String key) {

        COSCredentials cred = new AnonymousCOSCredentials();

        ClientConfig clientConfig = new ClientConfig(new Region(REGION));

        COSClient cosClient = new COSClient(cred, clientConfig);

        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(BUCKET_NAME, key, HttpMethodName.GET);
        URL url = cosClient.generatePresignedUrl(req);

        cosClient.shutdown();

        return url.toString().replace("http", "https");
    }

}