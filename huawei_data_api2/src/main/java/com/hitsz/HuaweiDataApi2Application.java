package com.hitsz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HuaweiDataApi2Application {

	public static void main(String[] args) {
		SpringApplication.run(HuaweiDataApi2Application.class, args);
		System.out.println("spring boot running ...");
	}

}
