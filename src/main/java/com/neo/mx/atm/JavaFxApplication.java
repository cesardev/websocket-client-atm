package com.neo.mx.atm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.neo.mx.atm.controllers.MyController;
import com.neo.mx.atm.topics.ChatWebsocketTopic;

public class JavaFxApplication extends Application {

	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init() {
		String[] args = getParameters().getRaw().toArray(new String[0]);

		this.applicationContext = new SpringApplicationBuilder().sources(WebsocketClientAtmApplication.class).run(args);
	}

	@Override
	public void start(Stage stage) throws InterruptedException {
		FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
		Parent root = fxWeaver.loadView(MyController.class);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
//		Platform.setImplicitExit(false);

	}

	@Override
	public void stop() {
		this.applicationContext.close();
		Platform.exit();
	}

}
