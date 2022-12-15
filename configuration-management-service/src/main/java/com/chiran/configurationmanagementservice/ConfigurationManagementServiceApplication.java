package com.chiran.configurationmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationManagementServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConfigurationManagementServiceApplication.class, args);
	}

}
