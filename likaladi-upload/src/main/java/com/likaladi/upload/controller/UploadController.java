package com.likaladi.upload.controller;

import com.likaladi.search.config.FileStrategyFactory;
import com.likaladi.upload.entity.FileInfo;
import com.likaladi.upload.service.FileInfoService;
import com.likaladi.upload.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "上传接口", description = "上传接口")
@Slf4j
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 文件上传<br>
     * @param file
     * @param fileSource
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "文件", notes = "用户登录", httpMethod = "POST")
    @PostMapping
    public FileInfo upload(@RequestParam("file") MultipartFile file, String fileSource) throws Exception {

        FileService fileService = FileStrategyFactory.getFileService(fileSource);

        return fileService.uploadFile(file);
    }

    /**
     * 文件删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        FileInfo fileInfo = fileInfoService.findById(id);
        if (fileInfo != null) {
            FileService fileService = FileStrategyFactory.getFileService(fileInfo.getSource());
            fileService.delete(fileInfo);
        }
    }

}
