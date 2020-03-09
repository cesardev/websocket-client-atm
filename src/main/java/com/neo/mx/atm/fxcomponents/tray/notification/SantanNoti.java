package com.neo.mx.atm.fxcomponents.tray.notification;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.neo.mx.atm.fxcomponents.tray.animations.AnimationProvider;
import com.neo.mx.atm.fxcomponents.tray.animations.AnimationType;
import com.neo.mx.atm.fxcomponents.tray.animations.FadeAnimation;
import com.neo.mx.atm.fxcomponents.tray.animations.PopupAnimation;
import com.neo.mx.atm.fxcomponents.tray.animations.SlideAnimation;
import com.neo.mx.atm.fxcomponents.tray.animations.TrayAnimation;
import com.neo.mx.atm.fxcomponents.tray.models.CustomStage;
import com.neo.mx.atm.models.AtmVO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class SantanNoti {
	
	@FXML
    private Label lblTitulo, lblUsuario, lblClose, labelFrom, labelAtmTxt, labelEstatusTxt, labelMotivoTxt, labelFallaTxt, labelFechaTxt;
    @FXML
    private ImageView imageIcon, imageIconFrom, imageIconClose;
    @FXML
    private Rectangle rectangleColor, rectangleColorDesc;
    @FXML
    private AnchorPane SantanNoti;

    private CustomStage stage;
    private NotificationType notificationType;
    private AnimationType animationType;
    private EventHandler<ActionEvent> onDismissedCallBack, onShownCallback;
    private TrayAnimation animator;
    private AnimationProvider animationProvider;

    /**
     * Initializes an instance of the tray notification object
     * @param titulo The titulo text to assign to the tray
     * @param body The body text to assign to the tray
     * @param img The image to show on the tray
     * @param rectangleFill The fill for the rectangle
     */
    public SantanNoti(String titulo, String body, Image img, Paint rectangleFill) {
        initTrayNotification(titulo, body, NotificationType.CUSTOM);

        setImage(img);
        setRectangleFill(rectangleFill);
    }

    /**
     * Initializes an instance of the tray notification object
     * @param titulo The titulo text to assign to the tray
     * @param body The body text to assign to the tray
     * @param notificationType The notification type to assign to the tray
     */
    public SantanNoti(String titulo, String body, NotificationType notificationType ) {
        initTrayNotification(titulo, body, notificationType);
    }

    /**
     * Initializes an empty instance of the tray notification
     */
    public SantanNoti() {
        initTrayNotification("", "", NotificationType.CUSTOM);
    }

    private void initTrayNotification(String titulo, String usuario, NotificationType type) {

        try {

        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/assets/fxml/SantanNoti.fxml"));
        	fxmlLoader.setController(this);
            fxmlLoader.load();

            initStage();
            initAnimations();

            setTray(titulo, usuario, type);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAnimations() {

        animationProvider =
            new AnimationProvider(new FadeAnimation(stage), new SlideAnimation(stage), new PopupAnimation(stage));

        //Default animation type
        setAnimationType(AnimationType.SLIDE);
    }

	private void initStage() {

        stage = new CustomStage(SantanNoti, StageStyle.UNDECORATED);
        stage.setScene(new Scene(SantanNoti));
        stage.setAlwaysOnTop(true);
        stage.setLocation(stage.getBottomRight());

        stage.setTitle("Santander");
        stage.setMaximized(false);
        stage.setResizable(false);
//        lblClose.setOnMouseClicked(e -> dismiss());
//        lblClose.setVisible(false);
        imageIconClose.setOnMouseClicked( e -> dismiss() );
    }

    public void setNotificationType(NotificationType nType) {

        notificationType = nType;

        URL imageLocation = null;
        String paintHex = null;
        File santanImg = new File(getClass().getResource("/assets/img/santander.png").getFile());
        File closeImg = new File(getClass().getResource("/assets/img/minimize.png").getFile());

        switch (nType) {

            case INFORMATION:
                imageLocation = getClass().getResource("/assets/img/info.png");
                paintHex = "#2C54AB";
                break;

            case NOTICE:
                imageLocation = getClass().getResource("/assets/img/notice.png");
                paintHex = "#8D9695";
                break;

            case SUCCESS:
                imageLocation = getClass().getResource("/assets/img/success.png");
                paintHex = "#009961";
                break;

            case WARNING:
                imageLocation = getClass().getResource("/assets/img/warning.png");
                paintHex = "#E23E0A";
                break;

            case ERROR:
                imageLocation = getClass().getResource("/assets/img/error.png");
                paintHex = "#CC0033";
                break;

            case CUSTOM:
                return;
        }

        setRectangleFill(Paint.valueOf(paintHex));
        setImage(new Image(imageLocation.toString()));
        setTrayIcon(imageIcon.getImage());
        setImageIconFrom(new Image(santanImg.toURI().toString()));
        setImageIconClose(new Image(closeImg.toURI().toString()));
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setTray(String titulo, String usuario, NotificationType type) {
        setTitulo(titulo);
        setUsuario(usuario);
        setNotificationType(type);
    }

    public void setTray(String titulo, String usuario, Image img, Paint rectangleFill, AnimationType animType) {
        setTitulo(titulo);
        setUsuario(usuario);
        setImage(img);
        setRectangleFill(rectangleFill);
        setAnimationType(animType);
    }

    public boolean isTrayShowing() {
        return animator.isShowing();
    }

    /**
     * Shows and dismisses the tray notification
     * @param dismissDelay How long to delay the start of the dismiss animation
     */
    public void showAndDismiss(Duration dismissDelay) {

        if (isTrayShowing()) {
            dismiss();
        } else {
            stage.show();

            onShown();
            animator.playSequential(dismissDelay);
        }

        onDismissed();
    }

    /**
     * Displays the notification tray
     */
    public void showAndWait() {

        if (! isTrayShowing()) {
            stage.show();

            animator.playShowAnimation();

            onShown();
        }
    }

    /**
     * Dismisses the notifcation tray
     */
    public void dismiss() {

        if (isTrayShowing()) {
            animator.playDismissAnimation();
            onDismissed();
        }
    }

    private void onShown() {
        if (onShownCallback != null)
            onShownCallback.handle(new ActionEvent());
    }

    private void onDismissed() {
        if (onDismissedCallBack != null)
            onDismissedCallBack.handle(new ActionEvent());
    }

    /**
     * Sets an action event for when the tray has been dismissed
     * @param event The event to occur when the tray has been dismissed
     */
    public void setOnDismiss(EventHandler<ActionEvent> event) {
        onDismissedCallBack  = event;
    }

    /**
     * Sets an action event for when the tray has been shown
     * @param event The event to occur after the tray has been shown
     */
    public void setOnShown(EventHandler<ActionEvent> event) {
        onShownCallback  = event;
    }

    /**
     * Sets a new task bar image for the tray
     * @param img The image to assign
     */
    public void setTrayIcon(Image img) {
        stage.getIcons().clear();
        stage.getIcons().add(img);
    }

    public Image getTrayIcon() {
        return stage.getIcons().get(0);
    }

    public void setImage (Image img) {
        imageIcon.setImage(img);

        setTrayIcon(img);
    }
    

/*
 * 
 */
//  SETEO POR ELEMENTOS DE NOTIFICACIÓN
    public void setTitulo(String txt) {
        lblTitulo.setText(txt);
    }

    public void setUsuario(String txt) {
        lblUsuario.setText(txt);
    }

    public void setLabelAtmTxt( String txt ) {
    	labelAtmTxt.setText( txt );
    }

    public void setLabelEstatusTxt( String txt ) {
    	labelEstatusTxt.setText( txt );
    }

    public void setLabelMotivoTxt( String txt ) {
    	labelMotivoTxt.setText( txt );
    }

    public void setLabelFallaTxt( String txt ) {
    	labelFallaTxt.setText( txt );
    }

    public void setLabelFechaTxt( String txt ) {
    	labelFechaTxt.setText( txt );
    }

//  SETEO GENERAL DE DATOS A LA NOTIFICACIÓN
    public void setDataNoti( AtmVO atm ) {
    	lblTitulo.setText( atm.getTitulo() );
    	lblUsuario.setText( atm.getUsuario() );
    	labelAtmTxt.setText( atm.getCodigoAtm().toString() );
    	labelEstatusTxt.setText( atm.getEstatus() );
    	labelMotivoTxt.setText( atm.getMotivo() );
    	labelFallaTxt.setText( atm.getFalla() );
    	labelFechaTxt.setText( atm.getFecha() );
    }
/*
 * 
 */
    


    public Image getImage() {
        return imageIcon.getImage();
    }

    public void setImageIconFrom(Image img) {
    	imageIconFrom.setImage(img);
    }
    
    public void setImageIconClose(Image img) {
    	imageIconClose.setImage(img);
    }

    public void setRectangleFill(Paint value) {
        rectangleColor.setFill(value);
        rectangleColorDesc.setFill(value);
    }

    public Paint getRectangleFill() {
        return rectangleColor.getFill();
    }

    public void setAnimationType(AnimationType type) {
    	AnimationType typeTmp = AnimationType.SLIDE;
        animator = animationProvider.findFirstWhere(a -> a.getAnimationType() == typeTmp);
        animationType = type;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

}
