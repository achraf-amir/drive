package com.drive.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({
				"com.drive.common.rest",
				"com.drive.mock"
})
public class DriveMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriveMockApplication.class, args);
	}

}
