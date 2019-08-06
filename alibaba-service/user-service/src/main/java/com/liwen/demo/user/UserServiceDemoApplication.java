package com.liwen.demo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

// 扫描mybatis哪些包里面的接口
@MapperScan("com.liwen.demo.user.dao")
@SpringBootApplication
public class UserServiceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceDemoApplication.class, args);
	}

}

