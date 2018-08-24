package br.com.meupag.sbac1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Sbac1Application {

	public static void main(String[] args) {
		SpringApplication.run(Sbac1Application.class, args);
	}
}
