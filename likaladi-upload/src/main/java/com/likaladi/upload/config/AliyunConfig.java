package com.likaladi.upload.config;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置
 *
 * @author liwen
 *
 */
@Configuration
public class AliyunConfig {

	@Value("${file.aliyun.endpoint}")
	private String endpoint;
	@Value("${file.aliyun.accessKeyId}")
	private String accessKeyId;
	@Value("${file.aliyun.accessKeySecret}")
	private String accessKeySecret;

	/**
	 * 阿里云文件存储client
	 *
	 */
	@Bean
	public OSSClient ossClient() {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		return ossClient;
	}

}
