package com.neo.mx.atm.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neo.mx.atm.topics.ChatWebsocketTopic;

@Component
@FxmlView("main-stage.fxml")
public class MyController {

    @FXML
    private Label weatherLabel;
    private ChatWebsocketTopic chatWebsocketTopic;

    @Autowired
    public MyController(ChatWebsocketTopic chatWebsocketTopic) throws InterruptedException, ExecutionException {
        this.chatWebsocketTopic = chatWebsocketTopic;
        conectarWs( new ActionEvent() );
    }
    
    public void conectarWs( ActionEvent actionEvent ) throws InterruptedException, ExecutionException {
//        this.weatherLabel.setText(weatherService.getWeatherForecast());
    	chatWebsocketTopic.connectWs();
    }
    
    public void enviarMsj( ActionEvent actionEvent ) {
    	chatWebsocketTopic.sendMsg( actionEvent );
    }
}
