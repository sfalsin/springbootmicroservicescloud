package br.com.pag.notificationproducer;

import java.util.UUID;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	// @Autowired
	// MessageProducer mp;

	@Autowired
	private ProducerTemplate producerTemplate;

	@Value("${server.port}")
	private String porta;

	@RequestMapping("/hello")
	public String hello() throws Exception {
		for (int x = 0; x < 1000; x++) {
			String message = UUID.randomUUID().toString();
			producerTemplate.sendBody("kafka:{{kafka.topic}}?brokers={{kafka.server}}:{{kafka.port}}&partitioner=br.com.pag.notificationproducer.MyPartitioner", message);
		}

		return "Hello from servico1 porta " + porta;
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
