package com.likaladi.upload.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "file_info")
public class FileInfo implements Serializable {
    /**
     * 文件md5
     */
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 是否是图片
     */
    @Column(name = "isImg")
    private Boolean isimg;

    /**
     * 文件类型
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 物理路径
     */
    private String path;

    /**
     * 文件网络url
     */
    private String url;

    /**
     * 文件存储地方
     */
    private String source;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
