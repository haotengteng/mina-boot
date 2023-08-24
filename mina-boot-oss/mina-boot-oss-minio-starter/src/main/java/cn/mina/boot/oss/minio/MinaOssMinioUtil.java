package cn.mina.boot.oss.minio;

import cn.mina.boot.common.util.DateUtils;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by haoteng on 2022/5/27.
 */
@Slf4j
public class MinaOssMinioUtil {

    protected static String bucketName;

    protected static MinioClient minioClient;


    /**
     * 初始化桶，不存在则创建
     */
    protected static void initBucket() {
        log.info("bucket start create");
        try {
            boolean exists = bucketExist(bucketName);
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("bucket create success, bucket name >>> {} <<<", bucketName);
            }
        } catch (Exception e) {
            log.error("bucket create exception.", e);
        }
    }


    /**
     * description: 文件流上传文件
     * contentType: MultipartFile.getContentType 获取
     */
    public static String upload(String fileName, InputStream in, String contentType) {

        try {
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(getFileName(fileName))
                    .stream(in, in.available(), -1)
                    .contentType(contentType)
                    .build()
            );
            return objectWriteResponse.object();
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

    private static String getFileName(String fileName) {
        String dataStr = DateUtils.nowStr("yyyyMMdd") + "/";
        if (StringUtils.contains(fileName, "/")) {
            // 忽略filename携带的路径
            fileName = StringUtils.substringAfterLast(fileName, "/");
        }
        // 增加随机字符 防重复
        String[] split = StringUtils.split(fileName, ".");
        Boolean flag = true;
        String name = "";
        for (String s : split) {
            if (flag) {
                name = s + "-" + RandomStringUtils.randomNumeric(8);
                flag = false;
            } else {
                name = name + "." + s;
            }

        }
        return dataStr + "" + name;
    }

    public static void main(String[] args) {
        System.out.println(getFileName("name.jpg"));
    }

    /**
     * 图片上传
     *
     * @param imageBase64
     * @param imageName
     * @return
     */
    public String uploadImage(String imageBase64, String imageName) {
        if (!StringUtils.isEmpty(imageBase64)) {
            InputStream in = base64ToInputStream(imageBase64);
            return upload(imageName, in, null);
        }
        return null;
    }

    public static InputStream base64ToInputStream(String base64) {
        ByteArrayInputStream stream = null;
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(base64.trim());
            stream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }

    /**
     * description: 下载文件
     * 推荐设置桶为公共策略，直接请求localhost:9000/bucket/fileName下载
     * 此方法应用场景针对于不对外开放的敏感数据认证下载
     */
    public static byte[] download(String fileName) {
        ByteArrayOutputStream out = (ByteArrayOutputStream) downloadStream(fileName);
        //封装返回值
        return out.toByteArray();
    }


    public static OutputStream downloadStream(String fileName) {
        try (
                InputStream in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            IOUtils.copy(in, out);
            //封装返回值
            return out;
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
        boolean exist;
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
