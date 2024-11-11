package com.examples.SunatOpenfeign.SunatOpenfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SunatOpenfeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(SunatOpenfeignApplication.class, args);
	}

}
