package com.example.rentApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class RentAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentAppApplication.class, args);
		System.out.println("Started");
	}

}
