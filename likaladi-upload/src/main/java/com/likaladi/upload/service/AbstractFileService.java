package com.likaladi.upload.service;

import com.likaladi.base.BaseServiceImpl;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.upload.entity.FileInfo;
import com.likaladi.upload.mapper.FileInfoMapper;
import com.likaladi.upload.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * 实现文件上传
 */
@Slf4j
public abstract class AbstractFileService  implements FileService{

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public FileInfo uploadFile(MultipartFile file) throws Exception {

        FileInfo fileInfo = FileUtil.getFileInfo(file);

        /** 先根据文件md5查询记录 */
        FileInfo oldFileInfo = fileInfoMapper.selectByPrimaryKey(fileInfo.getId());

        /** 如果已存在文件，避免重复上传同一个文件 */
        if(!Objects.isNull(oldFileInfo)){
            return oldFileInfo;
        }

        if (!fileInfo.getName().contains(".")) {
            ErrorBuilder.throwMsg("缺少后缀名");
        }

        /** 设置文件来源 */
        fileInfo.setSource(getFileSource());

        uploadOperate(file, fileInfo);

        /** 将文件信息保存到数据库 */
        fileInfoMapper.insertSelective(fileInfo);

        log.info("上传文件：{}", fileInfo);

        return fileInfo;
    }

    /**
     * 子类设置文件来源 : 本地/阿里云
     * @return
     */
    public abstract String getFileSource();

    /**
     * 子类上传操作：本地实现上传/阿里云oss实现上传
     * @param file
     */
    public abstract void uploadOperate(MultipartFile file, FileInfo fileInfo) throws Exception;


}
