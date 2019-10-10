package com.likaladi.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 管理后台
 * @author likaladi
 *
 */
@MapperScan("com.likaladi.manager.mapper")
@SpringBootApplication(scanBasePackages = "com.likaladi")
public class ManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageApplication.class, args);
	}

}