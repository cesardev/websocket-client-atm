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
import com.neo.mx.atm.fxcomponents.tray.notification.SantanNoti;


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
		        tray.setTitle("ATM CONNECT");
		        tray.setMessage(System.getProperty("user.name").toUpperCase() + ", ahora estas conectado a ATM CONNECT");
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
				SantanNoti notiSantan = new SantanNoti();

		        File santanImg = new File(getClass().getResource("/assets/img/santander.png").getFile());
		        File neoImg = new File(getClass().getResource("/assets/img/neo.png").getFile());

		        notiSantan.setAnimationType(type);
		        notiSantan.setTitulo(msg.getTexto().toUpperCase());
		        notiSantan.setUsuario(msg.getUsername());
		        notiSantan.setNotificationType(NotificationType.INFORMATION);
		        notiSantan.showAndWait();
//		        notiSantan.setAnimationType(AnimationType.FADE);
		        notiSantan.setRectangleFill(Paint.valueOf("#ec0000"));
		        notiSantan.setImage(new Image(neoImg.toURI().toString()));
		        notiSantan.setTrayIcon(new Image(santanImg.toURI().toString()));
//		        notiSantan.setAnimationType(AnimationType.FADE);
//		        notiSantan.showAndDismiss(Duration.millis(3000));

			}
			
		});
        
    }

}
