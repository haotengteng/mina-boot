package cn.mina.boot.oss.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by haoteng on 2022/5/27.
 */
@Slf4j
public class MinaOssMinioUtil {

    protected static String bucketName;

    protected static MinioClient minioClient;

    /**
     * description: 文件流上传文件
     */
    public static void upload(String fileName, InputStream in, String contentType) {

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(in, in.available(), -1)
                    .contentType(contentType)
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("mina oss minio upload failed", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("mina oss minio upload closed inputStream failed!!!");
                }
            }
        }
    }

    /**
     * description: 下载文件
     * 推荐设置桶为公共策略，直接请求localhost:9000/bucket/fileName下载
     * 此方法应用场景针对于不对外开放的敏感数据认证下载
     */
    public static byte[] download(String fileName) {
        try (
                InputStream in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            IOUtils.copy(in, out);
            //封装返回值
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("mina oss minio download failed", e);
        }
    }


    /**
     * 删除一个对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     */

    public static boolean removeObject(String bucketName, String objectName) {
        boolean exist = bucketExist(bucketName);
        if (exist) {
            try {
                minioClient.removeObject(
                        RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
            } catch (Exception e) {
                log.error("minio 删除对象失败");
                return false;
            }
            return true;
        }
        return false;
    }


    /**
     * description: 获取文件临时下载地址，最长7天
     */
    public static String getUrl(String fileName, int time, TimeUnit timeUnit) {

        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileName)
                    .expiry(time, timeUnit).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return
     */
    private static boolean bucketExist(String bucketName) {
        boolean exist ;
        try {
            exist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("连接minio失败", e);
        }
        if (!exist) {
            throw new RuntimeException("minio 中 桶不存在:" + bucketName);
        }
        return exist;
    }
}
