package br.com.pag.notificationconsumer;


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer extends RouteBuilder{


    @Override
    public void configure() throws Exception {

        from("kafka:{{kafka.topic}}?brokers={{kafka.server}}:{{kafka.port}}&groupId={{kafka.channel}}"
        		+ "&maxPollRecords={{consumer.maxPollRecords}}"
        		+ "&consumersCount={{consumer.consumersCount}}"
        		//+ "&seekTo={{consumer.seekTo}}"
        		+ "&autoOffsetReset=earliest"
        		+ "&autoCommitEnable=true"
        		+ "&autoCommitIntervalMs=500")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                    	String messageKey = "";
						if (exchange.getIn() != null) {
							Message message = exchange.getIn();
							Integer partitionId = (Integer) message
									.getHeader(KafkaConstants.PARTITION);
							String topicName = (String) message
									.getHeader(KafkaConstants.TOPIC);
							if (message.getHeader(KafkaConstants.KEY) != null)
								messageKey = (String) message
										.getHeader(KafkaConstants.KEY);
							Object data = message.getBody();


							System.out.println("topicName :: "
									+ topicName + " partitionId :: "
									+ partitionId + " messageKey :: "
									+ messageKey + " message :: "
									+ data + "\n");
						}
                        System.out.println("Message Body : " + exchange.getIn().getBody());
                    }
                }).to("log:input");

    }

}
