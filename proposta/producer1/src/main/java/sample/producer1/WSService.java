package sample.producer1;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Order(2)
@PropertySource("classpath:application.yml") 
@Component
public class WSService {
	
	//@Autowired
	private static Producer producer;
	
	private ClientWebSocket socket;
	private WebSocketContainer container;
	@Autowired
	public WSService(Producer producer) {
		this.producer = producer;
		socket = new ClientWebSocket();
		container = ContainerProvider.getWebSocketContainer();
		try {
			tryConnect();

		} catch (DeploymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		// TODO Auto-generated constructor stub
 catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void tryConnect() throws DeploymentException, IOException, URISyntaxException, InterruptedException {
		String dest = "ws://"+producer.getUrl()+"/websocket";
		container.connectToServer(socket, new URI(dest));
		socket.getLatch().await();
	}

	public ClientWebSocket getSocket() {
		return this.socket;
	}
	
}
