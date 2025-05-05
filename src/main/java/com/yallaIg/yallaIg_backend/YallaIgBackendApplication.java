package com.yallaIg.yallaIg_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YallaIgBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(YallaIgBackendApplication.class, args);
	}

}
