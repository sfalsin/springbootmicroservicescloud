package sample.producer1;

import java.lang.management.ManagementFactory;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.EnableSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.Proc1;
import com.example.Sensor;


@SpringBootApplication
@EnableBinding(Source.class)
@EnableSchemaRegistryClient
@Controller
@EnableWebMvc
public class Producer1Application extends WebMvcConfigurerAdapter{

	@Autowired
	private Source source;

	@Autowired
	WSService sender;
	
	@Value("${server.port}")
	private String serverPort;
	
	private Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(Producer1Application.class, args);
	}


	@RequestMapping(value = "/messages/{msgs}/{partitions}", method = RequestMethod.GET)
	@ResponseBody
	public String sendMessage(@PathVariable(value="msgs") Integer msgs, @PathVariable(value="partitions") Integer partitions) {
		loop(msgs,partitions);
		//AnaliseCPF acpf = new AnaliseCPF();
		//acpf.setCpf(UUID.randomUUID().toString());
		//acpf.setDataNascimento(UUID.randomUUID().toString());
		//acpf.setId(UUID.randomUUID().toString());
		//ssource.output().send(MessageBuilder.withPayload(acpf).build());
		return msgs+" mensagens enviadas!";
	}

	@RequestMapping(value = "/monitor", method = RequestMethod.GET)
	public String monitor() {
		return "dashboard";
	}

	@Async
	private void loop(Integer msgs, Integer partitions) {
		Integer bloco = (int) Math.round(((double)msgs / partitions)+0.5d);
				//Math.ceil((double)msgs/partitions);
		for(int x=1;x<=msgs;x++) {
			source.output().send(MessageBuilder.withPayload(randomProc1()).setHeader("partitionKey", Math.round(x/bloco)+1).build());
			try {

				sender.getSocket().sendMessage("{\"tipo\":\"producer\",\"id\":\"" + Producer1Application.getProcessId("falhou") + "\", \"port\":\""+serverPort+"\" , \"value\":\""
						+ (Math.round(x/bloco)+1) + "\"}");
				System.out.println("msg enviada!");
			} catch (Exception ex) {
				try {
					sender.tryConnect();
				} catch (Exception e) {
				}
			}
			System.out.println("@@@ partição "+(Math.round(x/bloco)+1));
		}

	}
	
	private Sensor randomProc1() {
		Sensor sensor = new Sensor();
		sensor.setId(UUID.randomUUID().toString() + "-v1");
		sensor.setAcceleration(random.nextFloat() * 10);
		sensor.setVelocity(random.nextFloat() * 100);
		sensor.setTemperature(random.nextFloat() * 50);
		sensor.setTeste(2.0f);
		return sensor;
	}

	private Proc1 randomSensor() {
		Proc1 sensor = new Proc1();
		sensor.setId(UUID.randomUUID().toString() + "-v1");
		sensor.setValor(1.0f);
		return sensor;
	}

	//Another convenience POST method for testing deterministic values
	@RequestMapping(value = "/messagesX", method = RequestMethod.POST)
	public String sendMessageX(@RequestParam(value="id") String id, @RequestParam(value="acceleration") float acceleartion,
							   @RequestParam(value="velocity") float velocity, @RequestParam(value="temperature") float temperature) {
		Sensor sensor = new Sensor();
		sensor.setId(id + "-v1");
		sensor.setAcceleration(acceleartion);
		sensor.setVelocity(velocity);
		sensor.setTemperature(temperature);
		source.output().send(MessageBuilder.withPayload(sensor).build());
		return "ok, have fun with v1 payload!";
	}

	@Configuration
	static class ConfluentSchemaRegistryConfiguration {
		@Bean
		public SchemaRegistryClient schemaRegistryClient(@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}") String endpoint){
			ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
			client.setEndpoint(endpoint);
			return client;
		}
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  if (!registry.hasMappingForPattern("/**")) {
	     registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
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



