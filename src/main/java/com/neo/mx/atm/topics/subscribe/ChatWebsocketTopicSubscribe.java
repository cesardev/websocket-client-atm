package com.neo.mx.atm.topics.subscribe;

import java.io.File;
import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import com.neo.mx.atm.fxcomponents.tray.animations.AnimationType;
import com.neo.mx.atm.fxcomponents.tray.notification.TrayNotification;
import com.neo.mx.atm.models.MensajeVO;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import com.neo.mx.atm.fxcomponents.tray.notification.NotificationType;
//import tray.notification.TrayNotification;
//import tray.animations.AnimationType;
//import tray.notification.NotificationType;


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

//		        Image santanImg = new Image("D:\\Neo-crm\\Workspace-test\\websocket-client-atm\\src\\main\\resources\\assets\\img\\logo-santan-cut.jpg");

		        File santanImg = new File("D:\\Neo-crm\\Workspace-test\\websocket-client-atm\\src\\main\\resources\\assets\\img\\santander.png");

		        tray.setAnimationType(type);
		        tray.setTitle(msg.getUsername());
		        tray.setMessage(msg.getTexto());
		        tray.setNotificationType(NotificationType.INFORMATION);
		        tray.showAndWait();
		        tray.setRectangleFill(Paint.valueOf("#ec0000"));
		        tray.setImage(new Image(santanImg.toURI().toString()));
		        tray.setTrayIcon(new Image(santanImg.toURI().toString()));
//		        tray.setAnimationType(AnimationType.FADE);
//		        tray.showAndDismiss(Duration.millis(3000));
			}
			
		});
        
    }

}
