/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    @FXML VBox vboxInventory;
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
        controller.quit();
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
    
    public void syncObjects(){
        controller.syncObjects();
    }
    
    public void syncCharacters(){
        controller.syncCharacters();
    }
    
    public void syncDoors(){
        controller.syncDoors();
    }
    
    public void show(String text){
        controller.show(text);
    }
    
    public void showMessage(String text){
        controller.showMessage(text);
    }
    
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

    public VBox getVboxInventory() {
        return vboxInventory;
    }

    public GameController getController() {
        return controller;
    }
    
}
