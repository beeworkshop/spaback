package com.beeworkshop.spaback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * @author beeworkshop
 * @description springboot项目入口
 *
 */

@SpringBootApplication
@MapperScan(value = "com.beeworkshop.spaback.dao")
public class SpabackApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpabackApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}

}
