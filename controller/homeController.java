/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import projet.SwitchStage;

/**
 *
 * @author JUGURTHA
 */
public class homeController implements Initializable {
    
    
    @FXML
    Button startBtn;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
         startBtn.setOnAction( event -> {
            ((Stage) startBtn.getScene().getWindow()).close();
            SwitchStage myStage = new SwitchStage("view/game.fxml");
            myStage.show();
        });
        
    }   
    
}
