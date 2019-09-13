package com.solace.workshops.spring.scs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MqttWebApp {

	public static void main(String[] args) {
		SpringApplication.run(MqttWebApp.class, args);
	}

	@RequestMapping("/")
	String home() {
		return "Welcome to the Solace Spring Cloud Streams Workshop! "
				+ "Lookup your route in Apps Manager or via CLI:'cf app spring-boot-mqttwebapp' "
				+ "Navigate to https://<INSERT ROUTE>/mqttListener.html to see incoming tweets!";
	}

}
