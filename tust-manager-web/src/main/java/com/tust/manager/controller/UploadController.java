package com.tust.manager.controller;


import com.tust.pojo.Result;
import com.tust.utils.FtpUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class UploadController {

    @Value("${FILE_UPLOAD_HOST}")
    private String FILE_UPLOAD_HOST;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FILE_UPLOAD_PORT}")
    private int FILE_UPLOAD_PORT;
    @Value("${FILE_BASE_PATH}")
    private String FILE_BASE_PATH;
    @Value("${FILE_ACCESS_URL}")
    private String FILE_ACCESS_URL;

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) {
        if (file != null){
            // 获取文件的后缀
            String originalName = file.getOriginalFilename();
            String suffix = originalName.substring(originalName.lastIndexOf("."));
            // 时间戳+随机数生成文件名
            String fileName = String.valueOf(System.currentTimeMillis())+(int)((Math.random()*9+1)*100000)+suffix;
            //生成文件在服务器端存储的子目录
            String filePath = new DateTime().toString("/yyyy/MM/dd");
            //生成url
            String url = FILE_ACCESS_URL+filePath+"/"+fileName;

            try {
                // 将文件转化为字节流
                InputStream is = file.getInputStream();
                boolean uploadRes = FtpUtil.uploadFile(FILE_UPLOAD_HOST, FILE_UPLOAD_PORT, FTP_USERNAME, FTP_PASSWORD, FILE_BASE_PATH, filePath, fileName, is);
                return new Result(true, url);
            } catch (IOException e) {
                e.printStackTrace();
                return new Result(false, "上传过程出错");
            }
        }
        return new Result(false, "文件为空");
    }
}
