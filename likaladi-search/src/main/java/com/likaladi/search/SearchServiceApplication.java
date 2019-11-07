package com.likaladi.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author likaladi
 * 排除数据源加载：
 * 		exclude={DataSourceAutoConfiguration.class}
 *
 */
@MapperScan("com.likaladi.search.mapper")
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.likaladi", exclude={DataSourceAutoConfiguration.class})
public class SearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
	}

}

