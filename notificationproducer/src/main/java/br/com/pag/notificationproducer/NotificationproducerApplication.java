package br.com.pag.notificationproducer;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotificationproducerApplication {
    
	
	
	@Value("${server.port}")
    String serverPort;
    
    @Value("${pag.api.path}")
    String contextPath;
	
	
	public static void main(String[] args) {
		SpringApplication.run(NotificationproducerApplication.class, args);
	}
	

}
