package br.com.meupag.sbac3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@Autowired
	Servico1 servico1;
	
	@Value("${server.port}")
	private String porta;

	@RequestMapping("/hello")
	public String hello() {
		return "Hello from servico1 porta "+porta;
	}

	@RequestMapping("/servico1")
	public String s1() {
		return servico1.servico1();
	}
	@RequestMapping("/servico2")
	public String s2() {
		return "Gett servico1 porta ";
	}
}
