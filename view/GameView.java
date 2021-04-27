/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import projet.SwitchStage;

/**
 *
 * @author JUGURTHA
 */
public class GameView implements Initializable { 
    @FXML BorderPane root;
    @FXML VBox rightVBox;
    @FXML GridPane gridPane;
    @FXML ImageView homer;
    @FXML Button lookBtn;
    @FXML Button helpBtn;
    @FXML Button quitBtn;
    @FXML TabPane tabPane;
    @FXML Tab mapTab;
    @FXML Tab inventoryTab;
    @FXML Tab messageTab;
    @FXML VBox labelMessage;
    @FXML Label topLabel;
    @FXML ProgressBar hpBar;
    @FXML FlowPane flowInventory;
    @FXML ScrollPane scrollMessages;
    
    GameController controller;
    
    public void setController(GameController controller){
        this.controller = controller;
    }
     
    @FXML
    public void moveHomer(KeyEvent e ) {
        controller.moveHomer(e);      
    }
    
    @FXML
    public void help(){
        controller.help();
    }
    
    @FXML
    public void look(){
        controller.look();
    }
    
    @FXML
    public void quit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Really ? I'm always hungry, you want to RAGE QUIT ?");

        ButtonType buttonTypeQuit = new ButtonType("QUIT");
        ButtonType buttonTypeStay = new ButtonType("STAY", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeQuit, buttonTypeStay);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeQuit){
            SwitchStage.switchTo("home", root);
        } else {
            this.show("Thank you ! We go back");
        }
    }
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.controller = new GameController(this);
        controller.initialize(url, rb);
        this.hpBar.progressProperty().bind(controller.getHpProperty());
    }
    
    public void addInventory(ImageView img){
        controller.addInventory(img);
    }
    
    public void syncRoom(){
        controller.syncRoom();
    }
    
    public void show(String text){
        this.topLabel.setText(text);
    }

    public void showMessage(String text, String color) {
        this.tabPane.getSelectionModel().select(this.messageTab);        
        LabelMessage newMessage = new LabelMessage(text, color);
        this.labelMessage.getChildren().add(newMessage);
        this.scrollMessages.setVvalue(1.0);
    }
    
    public void goTransition(int xBefore, int yBefore, int xAfter, int yAfter) {
        
        Circle circleTransition = new Circle(1, Color.BLACK);
        this.gridPane.add(circleTransition, xBefore, yBefore);

        ScaleTransition st = new ScaleTransition(Duration.millis(600), circleTransition);

        st.setToX(1000);
        st.setToY(1000);
        st.play();

        st.setOnFinished(e -> {
            this.syncRoom();
            this.gridPane.add(circleTransition, xAfter, yAfter);
            ScaleTransition st2 = new ScaleTransition(Duration.millis(600), circleTransition);
            st2.setToX(0);
            st2.setToY(0);
            st2.play();
        });
    }
    
    
    // ------------------- getters ------------------- //
    public BorderPane getRoot() {
        return root;
    }

    public VBox getRightVBox() {
        return rightVBox;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public ImageView getHomer() {
        return homer;
    }

    public Button getLookBtn() {
        return lookBtn;
    }

    public Button getHelpBtn() {
        return helpBtn;
    }

    public Button getQuitBtn() {
        return quitBtn;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Tab getMapTab() {
        return mapTab;
    }

    public Tab getInventoryTab() {
        return inventoryTab;
    }

    public Tab getMessageTab() {
        return messageTab;
    }

    public VBox getLabelMessage() {
        return labelMessage;
    }

    public Label getTopLabel() {
        return topLabel;
    }
    
    public ScrollPane getScrollMessages() {
        return scrollMessages;
    }

    public ProgressBar getHpBar() {
        return hpBar;
    }

    public FlowPane getFlowInventory() {
        return flowInventory;
    }

    public GameController getController() {
        return controller;
    }

    public void setBackGround(String bgUrl) {
        Image image = new Image(bgUrl);
            this.gridPane.setBackground(
                new Background(
                    new BackgroundImage(image,
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT)
                )                
            );
    }

}
