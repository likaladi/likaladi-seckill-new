package com.likaladi.upload.service;

import com.likaladi.base.BaseService;
import com.likaladi.upload.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    FileInfo uploadFile(MultipartFile file) throws Exception;
}
