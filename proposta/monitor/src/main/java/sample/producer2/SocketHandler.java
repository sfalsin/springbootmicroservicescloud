package sample.producer2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {

	//private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	private static List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	private WebSocketSession monitor;

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		if (message.getPayload().equals("monitor")) {
			monitor = session;
			//sessions.remove(session);
			System.out.println("Monitor encontrado!");
		} else {
			if (monitor!=null) {
				monitor.sendMessage(new TextMessage(message.getPayload()));
			} else {
				System.out.println("Mensagem ignorada, monitor não encontrado!");
			}
			//System.out.println("Sessões para enviar ="+sessions.size());
			//for (WebSocketSession item : sessions) {
			//	System.out.println("mensagem ="+message.getPayload());
			//	item.sendMessage(new TextMessage(message.getPayload()));
			//}
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// the messages will be broadcasted to all users.
		System.out.println("Nova conexão!");
		sessions.add(session);
	}

}