/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import model.GameModel;
import javafx.scene.control.PopupControl;
import javafx.scene.layout.GridPane;
import model.characters.Attackable;
import model.characters.Enemy;
import model.characters.Hero;
import model.characters.MyCharacter;
import model.characters.Other;
import model.characters.Talkable;
import model.environement.Door;
import model.myObjects.MyObject;
import view.GameView;
/**
 *
 * @author JUGURTHA
 */
public class GameController implements Initializable {
    


    public GameModel model;
    GameView view;
    Hero myHero;
    
    public GameController(GameView view){
        this.model = new GameModel(this);
        this.view = view;
        this.view.setController(this);
        this.myHero = model.getHomer();
    }
  
    
    @FXML
    public void moveHomer(KeyEvent e ) {
        this.view.getGridPane().getChildren().remove(this.view.getHomer());

        if ( null != e.getCode())
            switch (e.getCode()) {
                case Z: model.moveHomer(0, -1); break;
                case S: model.moveHomer(0, +1); break;
                case Q: model.moveHomer(-1, 0); break;
                case D: model.moveHomer(+1, 0); break;
                default: break;
            }
        this.view.getGridPane().add(this.view.getHomer(), model.getX(), model.getY());          
    }
    
    @FXML
    public void help(){
        this.view.getTabPane().getSelectionModel().select(this.view.getMessageTab());
        this.showMessage(this.model.help());
    }
    
    @FXML
    public void look(){
        this.view.getTabPane().getSelectionModel().select(this.view.getMessageTab());
        this.showMessage(this.model.look());
    }
    
    @FXML
    public void quit(){
        myHero.beAttacked(-1);
    }
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Image image = new Image("view/img/border.jpg");
        this.view.getGridPane().setBackground(
            new Background(
                new BackgroundImage(image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT)
            )
        );
            
        this.view.getRoot().addEventHandler(KeyEvent.KEY_PRESSED, (event) ->{
            moveHomer(event);
        });        
        syncRoom();   
    }
    
    public void addInventory(ImageView img){
        this.view.getVboxInventory().getChildren().add(img);
    }
    
    public void syncRoom(){
        this.view.getGridPane().getChildren().removeAll(this.view.getGridPane().getChildren());
        this.view.getTopLabel().setText(""+ myHero.getCurrentRoom());
        syncDoors();
        
        if(!(myHero.getCurrentRoom().isLigth)){
            Image image = new Image("view/img/border.png");
            this.view.getGridPane().setBackground(
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
        
        
        this.view.getGridPane().getChildren().remove(this.view.getHomer());
        this.view.getGridPane().add(this.view.getHomer(), model.getX(), model.getY());
        
        for (Node node : this.view.getGridPane().getChildren()) {
            GridPane.setHalignment(node, HPos.CENTER);
        }
        
    }
    
    public void syncObjects(){
        List<MyObject> objects = myHero.getCurrentRoom().getObjects();
        for (MyObject object : objects) {
            ImageView img = object.getImg();
            Tooltip tooltip = new Tooltip("Look : " + object.descriptif());
            Tooltip.install(img, tooltip);
            
            img.setOnMouseEntered(e -> {
                img.scaleXProperty().setValue(1.7);
                img.scaleYProperty().setValue(1.7);
                
            });
            
            img.setOnMouseExited(e -> {
                img.scaleXProperty().setValue(1);
                img.scaleYProperty().setValue(1);
                PopupControl pop = new PopupControl();
            });
            
            img.setOnMouseClicked(e -> {
                this.myHero.take(object.toString());
                this.view.getGridPane().getChildren().remove(img);
                this.addInventory(img);
                
                img.setOnMouseClicked(event -> {
                    myHero.use(object.toString());
                });
            });
            
            img.setCursor(Cursor.HAND);
            this.view.getGridPane().add(img, object.getX(), object.getY());
        }
    }
    
    public void syncCharacters(){
        List<MyCharacter> characters = myHero.getCurrentRoom().getCharacters();
        
        for (MyCharacter ch : characters) {
            if(!(ch instanceof Hero)){
                ImageView img = ch.getImg();
                img.setCursor(Cursor.HAND);
                if(ch instanceof Enemy){
                    img.setOnMouseClicked(e ->{
                        if(ch.isAlive())
                            myHero.attack((Attackable) ch);
                        else
                            show("The dead body of " + ch.getName());
                    });
                } else {
                    img.setOnMouseClicked(e ->{
                        myHero.talk();
                    });
                }
          
            
                this.view.getGridPane().add(img, ch.getX(), ch.getY());
            }
        }
    }
    
    public void syncDoors(){
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
            if(door.room[1].equals(myHero.getCurrentRoom())){
                this.view.getGridPane().add(img, 4 - door.getX(), 4 - door.getY());
            } else {
                this.view.getGridPane().add(img, door.getX(), door.getY());
            }  
        }
    }
    
    public void show(String text){
        this.view.getTopLabel().setText(text);
    }
    
    public void showMessage(String text){
        
        this.view.getTabPane().getSelectionModel().select(this.view.getMessageTab());        
        Label newMessage = new Label(text);
        newMessage.setStyle("-fx-background-color: lightblue;");
        this.view.getLabelMessage().getChildren().add(newMessage);
        this.view.getScrollMessages().setVvalue(1);
    }   
    
    public SimpleDoubleProperty getHpProperty(){
        return model.hpProperty();
    }
    
    public void talk(Other other){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(other.getSpeechs().get(0));

        ButtonType buttonTypeYes = new ButtonType(other.getCondition());
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes){
            other.dropAllInventory();
            alert.getButtonTypes().remove(buttonTypeYes);
            alert.setContentText(other.getSpeechs().get(1));
            alert.showAndWait();
        } else {
            alert.getButtonTypes().remove(buttonTypeYes);
            alert.setContentText(other.getSpeechs().get(2));
            alert.showAndWait();
        }
        this.syncRoom();
    }
    
    
}