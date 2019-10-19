package com.likaladi.upload.service.impl;

import com.aliyun.oss.OSSClient;
import com.likaladi.upload.entity.FileInfo;
import com.likaladi.upload.enums.FileSource;
import com.likaladi.upload.service.AbstractFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author likaladi
 * 实现阿里云OSS上传
 */
@Service
public class AliyunFileServiceImpl extends AbstractFileService {

    @Autowired
    private OSSClient ossClient;

    /**
     * 配置阿里云bucket：likaladi
     */
    @Value("${file.aliyun.bucketName}")
    private String bucketName;

    /**
     * 配置OSS上传文件目录
     */
    @Value("${file.aliyun.catalog}")
    private String catalog;

    /**
     * Bucket 域名
     */
    @Value("${file.aliyun.domain}")
    private String domain;

    @Override
    public String getFileSource() {
        return FileSource.ALIYUN.name();
    }

    @Override
    public void uploadOperate(MultipartFile file, FileInfo fileInfo) throws Exception{
        ossClient.putObject(bucketName, catalog+"/"+ fileInfo.getName(), file.getInputStream());
        fileInfo.setUrl(domain + "/" + fileInfo.getName());
    }
}
