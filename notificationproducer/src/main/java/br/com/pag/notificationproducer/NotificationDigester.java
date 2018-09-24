//package br.com.pag.notificationproducer;
//
//import javax.ws.rs.core.MediaType;
//
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.apache.camel.ProducerTemplate;
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.model.rest.RestBindingMode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class NotificationDigester extends RouteBuilder {
//
//	@Autowired
//	private ProducerTemplate producerTemplate;
//	
//	@Value("${server.port}")
//	String serverPort;
//
//	@Value("${pag.api.path}")
//	String contextPath;
//
//	@Override
//	public void configure() throws Exception {
//		
//		
//		
////		restConfiguration().component("servlet") //Let's assume we registered CamelServlet at "/api"
////		.bindingMode(RestBindingMode.json)
////
////		//Enable swagger endpoint.
////		.apiContextPath("/swagger") //swagger endpoint path
////		.apiContextRouteId("swagger") //id of route providing the swagger endpoint
////
////		 //Swagger properties
////		.contextPath("/api") //base.path swagger property; use the mapping path set for CamelServlet
////		.apiProperty("api.title", "Example REST api")
////		.apiProperty("api.version", "1.0")
////		;
//		
//		restConfiguration().contextPath(contextPath) //
//				.port(serverPort).enableCORS(true)
//				.apiContextPath("/api-doc")
//				.apiProperty("api.title", "Test REST API")
//				.apiProperty("api.version", "v1").apiProperty("cors", "true") // cross-site
//				.apiContextRouteId("doc-api").component("servlet").bindingMode(RestBindingMode.json)
//				.dataFormatProperty("prettyPrint", "true");
//
//		rest("/api/").description("Teste REST Service")
//        .id("api-route")
//        .post("/bean")
//        .produces(MediaType.APPLICATION_JSON)
//        .consumes(MediaType.APPLICATION_JSON)
////        .get("/hello/{place}")
//        .bindingMode(RestBindingMode.auto)
//        .type(MyBean.class)
//        .enableCORS(true)
////        .outType(OutBean.class)
//
//        .to("direct:remoteService");
//		
//		from("direct:remoteService")
//        .routeId("direct-route")
//        .tracing()
//        .log(">>> ${body.id}")
//        .log(">>> ${body.name}")
////        .transform().simple("blue ${in.body.name}")                
//        .process(new Processor() {
//            @Override
//            public void process(Exchange exchange) throws Exception {
//                MyBean bodyIn = (MyBean) exchange.getIn().getBody();
//                producerTemplate.sendBody("kafka:{{kafka.topic}}?brokers={{kafka.server}}:{{kafka.port}}", bodyIn.getName());
//
//                ExampleServices.example(bodyIn);
//
//                exchange.getIn().setBody(bodyIn);
//            }
//        })
//        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));
//		
//		
////        from("kafka:{{kafka.topic}}?brokers={{kafka.server}}:{{kafka.port}}&groupId={{kafka.channel}}")
////                .process(new Processor() {
////                    @Override
////                    public void process(Exchange exchange) throws Exception {
////                        System.out.println("Message Body : " + exchange.getIn().getBody());
////                    }
////                });
//
////        from("timer://producer?period=1000")
////                .process(new Processor() {
////                    @Override
////                    public void process(Exchange exchange) throws Exception {
////                        String message = UUID.randomUUID().toString();
////                        producerTemplate.sendBody("kafka:{{kafka.topic}}?brokers={{kafka.server}}:{{kafka.port}}", message);
////                    }
////                });
//
//	}
//
//}
