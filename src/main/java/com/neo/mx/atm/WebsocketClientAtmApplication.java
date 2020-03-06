package com.neo.mx.atm;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsocketClientAtmApplication {

	public static void main(String[] args) {
//		SpringApplication.run(WebsocketClientAtmApplication.class, args);

		Application.launch(JavaFxApplication.class, args);
	}

}
