package com.liwen.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.liwen.demo.dao")
@SpringBootApplication
public class SeckillApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeckillApplication.class, args);
	}

}

