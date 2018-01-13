package com.github.konradr25.spdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class SpdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpdbApplication.class, args);
	}
}
