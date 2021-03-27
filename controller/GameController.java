/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.prism.paint.Color;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import model.GameModel;
import model.characters.Hero;
import model.characters.MyCharacter;
import model.environement.Door;
import model.myObjects.MyObject;
/**
 *
 * @author JUGURTHA
 */
public class GameController implements Initializable {
    
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
    @FXML Label labelMessage;
    @FXML Label topLabel;
    @FXML ProgressBar hpBar;
    @FXML VBox vboxInventory;

    //Random rand = new Random();
    GameModel model = new GameModel();
    Hero myHero = model.getHomer();
  
    
    @FXML
    public void moveHomer(KeyEvent e ) {
        gridPane.getChildren().remove(homer);

        if ( null != e.getCode())
            switch (e.getCode()) {
                case Z: model.moveHomer(0, -1); break;
                case S: model.moveHomer(0, +1); break;
                case Q: model.moveHomer(-1, 0); break;
                case D: model.moveHomer(+1, 0); break;
                default: break;
            }
        gridPane.add(homer, model.getX(), model.getY());          
    }
    
    @FXML
    public void help(){
        tabPane.getSelectionModel().select(messageTab);
        this.labelMessage.setText(this.model.help());
    }
    
    @FXML
    public void look(){
        tabPane.getSelectionModel().select(messageTab);
        this.labelMessage.setText(this.model.look());
    }
    
    @FXML
    public void quit(){
        myHero.beAttacked(-1);
    }
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Image image = new Image("view/img/border.jpg");
            gridPane.setBackground(
                new Background(
                    new BackgroundImage(image,
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT)
                )
            );
            
        root.addEventHandler(KeyEvent.KEY_PRESSED, (event) ->{
            moveHomer(event);
        });
        hpBar.progressProperty().bind(model.hpProperty());
        
        syncInventory();
        syncRoom();   
    }
    
    public void syncInventory(){
        for (MyObject object : model.getInventory()) {
            ImageView img = object.getImg();
            img.setFitHeight(50);
            img.setFitWidth(50);
            vboxInventory.getChildren().add(img);
        }
    }
    
    public void syncRoom(){
        gridPane.getChildren().removeAll(gridPane.getChildren());
        topLabel.setText(""+ myHero.getCurrentRoom());
        syncDoors();
        
        if(!(myHero.getCurrentRoom().isLigth)){
            Image image = new Image("view/img/border.png");
            gridPane.setBackground(
                new Background(
                    new BackgroundImage(image,
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT)
                )
                
            );
            return;
        }

        syncObjects();
        syncCharacters();
        
        
        gridPane.getChildren().remove(homer);
        gridPane.add(homer, model.getX(), model.getY());
        
        for (Node node : gridPane.getChildren()) {
            GridPane.setHalignment(node, HPos.CENTER);
        }
        
    }
    
    private void syncObjects(){
        List<MyObject> objects = myHero.getCurrentRoom().getObjects();
        
        for (MyObject object : objects) {
            ImageView img = object.getImg();
            
            img.setOnMouseEntered(e -> {
                img.setFitHeight(80);
                img.setFitWidth(80);
            });
            
            img.setOnMouseExited(e -> {
                img.setFitHeight(50);
                img.setFitWidth(50);
            });
            
            img.setCursor(Cursor.HAND);
            gridPane.add(img, object.getX(), object.getY());
        }
    }
    
    private void syncCharacters(){
        List<MyCharacter> characters = myHero.getCurrentRoom().getCharacters();
        
        for (MyCharacter ch : characters) {
            if(!(ch instanceof Hero)){
                ImageView img = ch.getImg();
                img.setCursor(Cursor.HAND);
            
                gridPane.add(img, ch.getX(), ch.getY());
            }
        }
    }
    
    private void syncDoors(){
        List<Door> doors = myHero.getCurrentRoom().getDoors();
        
        for (Door door : doors) {
            ImageView img = new ImageView("view/img/door.png");
            img.setFitHeight(100);
            img.setFitWidth(100);
            img.setCursor(Cursor.HAND);
            Tooltip tooltip = new Tooltip("GO to " + door.getOtherRoom(myHero));
            Tooltip.install(img, tooltip);
      
            img.setOnMouseClicked(e -> {
                myHero.go(door.getOtherRoom((Hero)myHero));
                this.syncRoom();
            });
            gridPane.add(img, door.getX(), door.getY());
            
        }
    }
    
}
