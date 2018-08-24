package br.com.meupag.sbac3;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "hello", url = "http://localhost:8082/hello")
public interface Servico1 {
 
 @RequestMapping("/servico1")
 String servico1();
}
