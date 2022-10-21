package cn.mina.boot.example.oss.controller;

import cn.mina.boot.common.exception.GlobalErrorCode;
import cn.mina.boot.common.exception.MinaGlobalException;
import cn.mina.boot.oss.minio.MinaOssMinioUtil;
import cn.mina.boot.web.common.context.MinaWebResult;
import cn.mina.boot.web.common.context.MinaWebTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/foo")
@Slf4j
public class FooController {


    @PostMapping("/upload")
    public List<String> upload(@RequestParam(name = "files") MultipartFile[] files) {
        List<String> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            try (
                    InputStream in = file.getInputStream();
            ) {
                MinaOssMinioUtil.upload(fileName, in, file.getContentType());
            } catch (Exception e) {
                throw new MinaGlobalException(GlobalErrorCode.ERROR_SYS_ERROR.customMassage("minio 上传失败！"));
            }
            fileList.add(fileName);
        }

        return fileList;
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String fileName) {
        //封装返回值
        byte[] bytes = MinaOssMinioUtil.download(fileName);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new MinaGlobalException(GlobalErrorCode.ERROR_SYS_ERROR.customMassage("minio 下载失败！"));
        }
        headers.setContentLength(bytes.length);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setAccessControlExposeHeaders(Arrays.asList("*"));
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/url")
    public String getUrl(@RequestParam(name = "fileName") String fileName) {
        String url = MinaOssMinioUtil.getUrl(fileName, 7, TimeUnit.DAYS);
        return url;
    }

}
