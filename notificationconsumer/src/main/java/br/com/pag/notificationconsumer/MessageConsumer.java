package br.com.pag.notificationconsumer;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer extends RouteBuilder{


    @Override
    public void configure() throws Exception {

        from("kafka:{{kafka.topic}}?brokers={{kafka.server}}:{{kafka.port}}&groupId={{kafka.channel}}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Message Body : " + exchange.getIn().getBody());
                    }
                });

    }

}
