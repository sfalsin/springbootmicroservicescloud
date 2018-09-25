package sample.consumer;

import com.example.Proc1;
import com.example.Sensor;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;

import javax.websocket.DeploymentException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;

@SpringBootApplication
@EnableBinding(Sink.class)
@EnableSchemaRegistryClient
@ComponentScan("sample.consumer")
public class ConsumerApplication {

	@Autowired
	WSService sender;
	
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