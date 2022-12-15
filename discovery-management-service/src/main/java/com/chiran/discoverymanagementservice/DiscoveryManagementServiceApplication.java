package com.chiran.discoverymanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryManagementServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryManagementServiceApplication.class, args);
	}
}
