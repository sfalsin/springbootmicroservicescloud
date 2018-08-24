package br.com.meupag.eurekasrvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekasrvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekasrvrApplication.class, args);
	}
}
