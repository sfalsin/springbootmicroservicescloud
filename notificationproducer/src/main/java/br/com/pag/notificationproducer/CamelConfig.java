package br.com.pag.notificationproducer;

import org.apache.camel.CamelContext;
import org.apache.camel.ThreadPoolRejectedPolicy;
import org.apache.camel.spi.ThreadPoolProfile;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
public class CamelConfig {

	@Bean
	CamelContextConfiguration contextConfiguration() {

		return new CamelContextConfiguration() {

			@Override
			public void beforeApplicationStart(CamelContext context) {
				// your custom configuration goes here
				ThreadPoolProfile threadPoolProfile = new ThreadPoolProfile();
				threadPoolProfile.setId("MyDefault");
				threadPoolProfile.setPoolSize(10);
				threadPoolProfile.setMaxPoolSize(15);
				threadPoolProfile.setMaxQueueSize(250);
				threadPoolProfile.setKeepAliveTime(25L);
				threadPoolProfile.setRejectedPolicy(ThreadPoolRejectedPolicy.Abort);
				context.getExecutorServiceManager().registerThreadPoolProfile(threadPoolProfile);

			}

			@Override
			public void afterApplicationStart(CamelContext camelContext) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
    @Controller
    class SwaggerWelcome {
        @RequestMapping("/swagger-ui")
        public String redirectToUi() {
            return "redirect:/webjars/swagger-ui/index.html?url=/camel/api-doc&validatorUrl=";
        }
    }
}