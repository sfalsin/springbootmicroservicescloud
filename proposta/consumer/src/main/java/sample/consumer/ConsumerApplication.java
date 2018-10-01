package sample.consumer;

import java.lang.annotation.Annotation;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableBinding(Sink.class)
@EnableSchemaRegistryClient
@ComponentScan("sample.consumer")
@Controller
public class ConsumerApplication {

	@Autowired
	WSService sender;
	
	@Autowired
	private ApplicationContext context;
	
	@Value("${server.port}")
	private String serverPort;

	private final Log logger = LogFactory.getLog(getClass());

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
		
	}

	private static Integer counter = 0;

	@StreamListener(Sink.INPUT)
	@Async
	// public void process(Proc1 data) {
	public void process(Message<?> message) {
		logger.info(ConsumerApplication.getProcessId("falhou") + " - "
				+ message.getHeaders().get("kafka_receivedPartitionId"));
		// logger.info(message.getPayload());
		this.counter++;

		try {

			sender.getSocket().sendMessage("{\"tipo\":\"consumer\",\"id\":\"" + ConsumerApplication.getProcessId("falhou") + "\", \"port\":\""+serverPort+"\" , \"value\":\""
					+ message.getHeaders().get("kafka_receivedPartitionId") + "\"}");
			System.out.println("msg enviada!");
		} catch (Exception ex) {
			try {
				sender.tryConnect();
			} catch (Exception e) {
			}
		}
		System.out.println("@@@ msg " + this.counter);
		try {
			Thread.sleep(80);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/classes", method = RequestMethod.GET)
	@ResponseBody
	public String[] sendMessage() {
		Reflections reflections = new Reflections("sample.consumer");
		Set<Class<?>> controllers = 
			    reflections.getTypesAnnotatedWith(Controller.class);
		for(Class<?> clazz:controllers) {
			//for(Annotation anno:clazz.getAnnotation(Controller.class)) {
				Class<? extends Annotation> type = clazz.getAnnotation(Controller.class).annotationType();
	            for (Method method : type.getDeclaredMethods()) {
	                Object value;
					try {
						value = method.invoke(clazz.getAnnotation(Controller.class), (Object[])null);
		                System.out.println(" " + method.getName() + ": " + value);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }				
			//}
		}
		String[] listaBeans = context.getBeanNamesForAnnotation(Controller.class);
		return listaBeans;
	}
	
	
	@Configuration
	static class ConfluentSchemaRegistryConfiguration {
		@Bean
		public SchemaRegistryClient schemaRegistryClient(
				@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}") String endpoint) {
			ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
			client.setEndpoint("http://localhost:8081");
			return client;
		}
	}

	
	public static String getProcessId(final String fallback) {
		// Note: may fail in some JVM implementations
		// therefore fallback has to be provided

		// something like '<pid>@<hostname>', at least in SUN / Oracle JVMs
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		final int index = jvmName.indexOf('@');

		if (index < 1) {
			// part before '@' empty (index = 0) / '@' not found (index = -1)
			return fallback;
		}

		try {
			return Long.toString(Long.parseLong(jvmName.substring(0, index)));
		} catch (NumberFormatException e) {
			// ignore
		}
		return fallback;
	}

}