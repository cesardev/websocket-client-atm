package com.neo.mx.atm.topics;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.neo.mx.atm.models.MensajeVO;
import com.neo.mx.atm.topics.subscribe.ChatWebsocketTopicSubscribe;

import javafx.event.ActionEvent;


@Service
public class ChatWebsocketTopic {

//	private static String URL = "http://180.177.87.107:8080/chat-websocket";
	private static String URL = "http://localhost:8080/chat-websocket";

	private Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
    private List<Transport> transports = Collections.singletonList(webSocketTransport);
    private SockJsClient sockJsClient = new SockJsClient(transports);
    private WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
    private StompSessionHandler sessionHandler = new ChatWebsocketTopicSubscribe();
    private StompSession stompSession;


//    @Autowired
    public void connectWs() throws InterruptedException, ExecutionException {

    	stompClient.setMessageConverter(new MappingJackson2MessageConverter());

    	stompSession = stompClient.connect(URL, sessionHandler).get();

    }
    
    public void deconnect() {
    	stompSession.disconnect();
    	stompClient.stop();
    }

    public void sendMsg( ActionEvent actionEvent ) {

    	MensajeVO mensaje = new MensajeVO();

		mensaje.setColor("blue");
		mensaje.setFecha(null);
		mensaje.setTexto("prueba desde java client");
		mensaje.setTipo("NUEVO_USUARIO");
		mensaje.setUsername(System.getProperty("user.name"));

		stompSession.send("/app/mensaje", mensaje);

    }
}