package projet;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchStage extends Stage{
    public SwitchStage(String path) {
        super();
        
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            this.setScene(new Scene(root));
            this.setResizable(false);
        }
       catch (Exception e){
           System.out.println("ERREUR, SwitchStage");
           e.printStackTrace();
       }
    }
}