/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
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
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.GameModel;
import javafx.scene.control.PopupControl;
import model.characters.Attackable;
import model.characters.Enemy;
import model.characters.Hero;
import model.characters.MyCharacter;
import model.environement.Door;
import model.myObjects.MyObject;
import view.GameView;
/**
 *
 * @author JUGURTHA
 */
public class GameController {

    public GameModel model;
    public GameView view;
    Hero myHero = model.getHomer();
    
    public GameController(){
        this.model = new GameModel(this);
        this.view  = new GameView(this);
    }
  
    public void moveHomer(KeyEvent e ) {
        view.gridPane.getChildren().remove(view.homer);

        if ( null != e.getCode())
            switch (e.getCode()) {
                case Z: model.moveHomer(0, -1); break;
                case S: model.moveHomer(0, +1); break;
                case Q: model.moveHomer(-1, 0); break;
                case D: model.moveHomer(+1, 0); break;
                default: break;
            }
        view.gridPane.add(view.homer, model.getX(), model.getY());          
    }
    

    public void help(){
        //ControllerHelp();
        
        view.tabPane.getSelectionModel().select(view.messageTab);
        String s = this.model.help();
        view.labelMessage.setText(s);
    }
    
    public void look(){
        view.tabPane.getSelectionModel().select(view.messageTab);
        view.labelMessage.setText(this.model.look());
    }
    
    public void quit(){
        myHero.beAttacked(-1);
    }
 
    
    public void initialize(URL url, ResourceBundle rb) {
        
        Image image = new Image("view/img/border.jpg");
        view.gridPane.setBackground(
            new Background(
                new BackgroundImage(image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT)
            )
        );
            
        view.root.addEventHandler(KeyEvent.KEY_PRESSED, (event) ->{
            moveHomer(event);
        });
        view.hpBar.progressProperty().bind(model.hpProperty());
        
        syncRoom();   
    }
    
    public void addInventory(ImageView img){
        view.vboxInventory.getChildren().add(img);
    }
    
    public void syncRoom(){
        view.gridPane.getChildren().removeAll(view.gridPane.getChildren());
        view.topLabel.setText(""+ myHero.getCurrentRoom());
        syncDoors();
        
        if(!(myHero.getCurrentRoom().isLigth)){
            Image image = new Image("view/img/border.png");
            view.gridPane.setBackground(
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
        
        
        view.gridPane.getChildren().remove(view.homer);
        view.gridPane.add(view.homer, model.getX(), model.getY());
        
        for (Node node : view.gridPane.getChildren()) {
            GridPane.setHalignment(node, HPos.CENTER);
        }
        
    }
    
    private void syncObjects(){
        List<MyObject> objects = myHero.getCurrentRoom().getObjects();
        for (MyObject object : objects) {
            
            ImageView img = object.getImg();
            Tooltip tooltip = new Tooltip("Look : " + object.descriptif());
            Tooltip.install(img, tooltip);
            
            img.setOnMouseEntered(e -> {
                img.setFitHeight(80);
                img.setFitWidth(80);
            });
            
            img.setOnMouseExited(e -> {
                img.setFitHeight(50);
                img.setFitWidth(50);
                PopupControl pop = new PopupControl();
            });
            
            img.setOnMouseClicked(e -> {
                this.myHero.take(object.toString());
                view.gridPane.getChildren().remove(img);
                view.addInventory(img);
                
                img.setOnMouseClicked(event -> {
                    myHero.use(object.toString());
                });
            });
            
            img.setCursor(Cursor.HAND);
            view.gridPane.add(img, object.getX(), object.getY());
            System.out.println("j'ajoute un "+ object + " à la position [ " + object.getX() + " , " + object.getY() + " ]");
        }
    }
    
    private void syncCharacters(){
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
                view.gridPane.add(img, ch.getX(), ch.getY());
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
            if(door.room[1].equals(myHero.getCurrentRoom())){
                view.gridPane.add(img, 4 - door.getX(), 4 - door.getY());
            } else {
                view.gridPane.add(img, door.getX(), door.getY());
            }
                
            
            
            
        }
    }
    
    public void show(String text){
        view.topLabel.setText(text);
    }
    
    public void showMessage(String text){
        
        view.tabPane.getSelectionModel().select(view.messageTab);
        String newText = view.labelMessage.getText() + "\n ------------------------------\n" + text;
        view.labelMessage.setText(newText);
    }
    
    public SimpleDoubleProperty getHpProperty(){
        return model.hpProperty();
    }
}
