package br.com.meupag.sbac1;

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

	@RequestMapping("/servico2")
	public String s2() {
		return "Gett servico1 porta ";
	}
	@RequestMapping("/servico3")
	public String s3() {
		return "Gett servico1 porta ";
	}
}
