package com.neo.mx.atm.topics.subscribe;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import com.neo.mx.atm.models.MensajeVO;

import javafx.application.Platform;
import javafx.util.Duration;
import tray.notification.TrayNotification;
import tray.animations.AnimationType;
import tray.notification.NotificationType;


public class ChatWebsocketTopicSubscribe extends StompSessionHandlerAdapter {

	@Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		session.subscribe("/chat/mensaje", this);
		
		Platform.runLater( new Runnable() {

			@Override
			public void run() {
				AnimationType type = AnimationType.POPUP;
				
		        TrayNotification tray = new TrayNotification();;
		        
		        tray.setAnimationType(type);
		        tray.setTitle("Conexión a websocket");
		        tray.setMessage("Suscrito al websocket");
		        tray.setNotificationType(NotificationType.SUCCESS);
		        tray.showAndDismiss(Duration.millis(3000));
			}
			
		});
    }

	@Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
    	System.out.println("Got an exception"+ exception);
    	exception.printStackTrace();
    }

	@Override
    public Type getPayloadType(StompHeaders headers) {
        return MensajeVO.class;
    }
	
	@Override
    public void handleFrame(StompHeaders headers, Object payload) {

		MensajeVO msg = (MensajeVO) payload;
		
		Platform.runLater( new Runnable() {

			@Override
			public void run() {
				AnimationType type = AnimationType.POPUP;
				
		        TrayNotification tray = new TrayNotification();;
		        
		        tray.setAnimationType(type);
		        tray.setTitle(msg.getUsername());
		        tray.setMessage(msg.getTexto());
		        tray.setNotificationType(NotificationType.INFORMATION);
		        tray.showAndWait();
//		        tray.showAndDismiss(Duration.millis(3000));
			}
			
		});
        
    }

}
