package br.com.meupag.sbac2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@Value("${server.port}")
	private String porta;

	@RequestMapping("/hello")
	public String hello() {
		return "Hello from servico1 porta "+porta;
	}

	@RequestMapping("/servico1")
	public String s1() {
		return "Gett servico1 porta ";
	}

	@RequestMapping("/servico3")
	public String s3() {
		return "Gett servico1 porta ";
	}
}
