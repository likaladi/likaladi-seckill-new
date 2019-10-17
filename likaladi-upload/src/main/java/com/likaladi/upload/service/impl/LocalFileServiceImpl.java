package com.likaladi.upload.service.impl;

import com.likaladi.upload.entity.FileInfo;
import com.likaladi.upload.enums.FileSource;
import com.likaladi.upload.service.AbstractFileService;
import com.likaladi.upload.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * @author likaladi
 * 本地上传文件实现
 */
@Slf4j
@Service
public class LocalFileServiceImpl extends AbstractFileService {

    /**
     * Nginx配置的本地映射地址:
     * http://likaladi.seckill --> D:/images
     */
    @Value("${file.local.urlPrefix}")
    private String urlPrefix;

    /**
     * 上传文件存储在本地的根路径：D:/images
     */
    @Value("${file.local.path}")
    private String localFilePath;

    @Override
    public String getFileSource() {
        return FileSource.LOCAL.name();
    }

    @Override
    public void uploadOperate(MultipartFile file, FileInfo fileInfo) {
        int index = fileInfo.getName().lastIndexOf(".");

        // 文件扩展名
        String fileSuffix = fileInfo.getName().substring(index);

        String suffix = "/" + LocalDate.now().toString().replace("-", "/") + "/" + fileInfo.getId() + fileSuffix;

        String path = localFilePath + suffix;
        String url = urlPrefix + suffix;
        fileInfo.setPath(path);
        fileInfo.setUrl(url);

        FileUtil.saveFile(file, path);
    }
}
