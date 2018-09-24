package sample.producer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Controller
@EnableWebMvc
public class Producer2Application  extends WebMvcConfigurerAdapter{
	public static void main(String[] args) {
		SpringApplication.run(Producer2Application.class, args);
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String monitor() {
		return "dashboard";
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  if (!registry.hasMappingForPattern("/**")) {
	     registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	  }
	}
}

