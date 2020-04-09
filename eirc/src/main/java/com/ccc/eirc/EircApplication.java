package com.ccc.eirc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.ccc.eirc.*.mapper")
public class EircApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EircApplication.class).run(args);
	}

}
