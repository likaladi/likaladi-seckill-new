package com.liwen.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class UserConsumeDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserConsumeDemoApplication.class, args);
	}

}

