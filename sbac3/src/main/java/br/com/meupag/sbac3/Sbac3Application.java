package br.com.meupag.sbac3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Sbac3Application {

	public static void main(String[] args) {
		SpringApplication.run(Sbac3Application.class, args);
	}
}
