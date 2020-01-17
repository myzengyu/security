package com.cloud.eurekaColony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //启动eureka注册中心server
public class EurekaColonyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaColonyApplication.class, args);
	}

}
