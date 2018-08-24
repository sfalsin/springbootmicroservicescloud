package br.com.meupag.sbac2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Sbac2Application {

	public static void main(String[] args) {
		SpringApplication.run(Sbac2Application.class, args);
	}
}
