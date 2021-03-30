/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import controller.GameController;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 *
 * @author JUGURTHA
 */
public class GameView implements Initializable{
    
    @FXML public BorderPane root;
    @FXML public VBox rightVBox;
    @FXML public GridPane gridPane;
    @FXML public ImageView homer;
    @FXML public Button lookBtn;
    @FXML public Button helpBtn;
    @FXML public Button quitBtn;
    @FXML public TabPane tabPane;
    @FXML public Tab mapTab;
    @FXML public Tab inventoryTab;
    @FXML public Tab messageTab;
    @FXML public Label labelMessage;
    @FXML public Label topLabel;
    @FXML public ProgressBar hpBar;
    @FXML public VBox vboxInventory;
    
    public GameController controller;
    
    public GameView(GameController controller){
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
        System.out.println("ouiiiiiiiiiiiiiiiiiii");
    }
    
    public void addInventory(ImageView img){
        controller.addInventory(img);
    }
    
    public void syncRoom(){
        controller.syncRoom();        
    }
    
    public void show(String text){
        controller.show(text);
    }
    
    public void showMessage(String text){
        controller.showMessage(text);
        
    }
    
}
