package br.com.meupag.sbac3;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

//@FeignClient(name = "hello", url = "http://localhost:8082/")
//@FeignClient(name = "hello", fallback = HelloFallBack.class)
@FeignClient(name = "SERVICO1", fallback = Servico1Fallback.class)
public interface Servico1 {

	@RequestMapping("/hello")
	String servico1();
}
