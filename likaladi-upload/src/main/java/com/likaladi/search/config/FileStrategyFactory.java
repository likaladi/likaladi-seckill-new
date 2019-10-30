package com.likaladi.search.config;

import com.likaladi.error.ErrorBuilder;
import com.likaladi.upload.enums.FileSource;
import com.likaladi.upload.service.FileService;
import com.likaladi.upload.service.impl.AliyunFileServiceImpl;
import com.likaladi.upload.service.impl.LocalFileServiceImpl;
import org.apache.commons.lang3.StringUtils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FileStrategyFactory {


    private static Map<FileSource, FileService> strategyMap = new ConcurrentHashMap<>();

    static {
        strategyMap.put(FileSource.LOCAL, ApplicationContextProvider.getBean(LocalFileServiceImpl.class));
        strategyMap.put(FileSource.ALIYUN, ApplicationContextProvider.getBean(AliyunFileServiceImpl.class));
    }

    /**
     * 根据文件源获取具体的实现类
     *
     * @param fileSource
     * @return
     */
    public static FileService getFileService(String fileSource) {

        /** 默认使用本地上传 */
        if(StringUtils.isBlank(fileSource)){
            return strategyMap.get(FileSource.LOCAL);
        }

        FileSource fileSourceEnum = null;
        try{
            fileSourceEnum = FileSource.valueOf(fileSource);
        }catch (Exception e){
            ErrorBuilder.throwMsg("来源：LOCAL-本地；ALIYUN-阿里云");
        }

        return strategyMap.get(FileSource.valueOf(fileSource));
    }

}
