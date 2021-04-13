package model.characters;

import model.myObjects.MyObject;
import model.myObjects.Parchment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javafx.scene.control.TextInputDialog;
import model.GameModel;


public class Other extends MyCharacter implements Talkable{

    public final List<String> speechs = new ArrayList<>();

    public String getCondition() {
        return condition;
    }
	private String condition = "yes";
	Scanner interact = new Scanner(System.in);
	
	public Other(String name, String condition) {
		super(name);
		this.condition = condition;
		
	}
	public Other(String name, String condition, String url) {
		super(name, url);
		this.condition = condition;
		
	}
	
	public Other(String name) {
		super(name);
		
	}

	
	public void addSpeechs(String speech1, String speech2, String speech3) {
		this.speechs.add(speech1);
		this.speechs.add(speech2);
		this.speechs.add(speech3);
	}
        
        public List<String> getSpeechs() {
            return speechs;
        }


	@Override
	public void talkTo(Talkable t) {
            
            if (this.getName().equalsIgnoreCase("Lisa")) 
                    this.talkToLisa(t);
            else
            if(this.getName().equalsIgnoreCase("Mr Burns"))
                    this.talkToBurns(t);

            else {
                GameModel.talk(this);
	
            }
        }
	
	public void talkToLisa(Talkable t) {
            GameModel.showMessage(this.speechs.get(0));
		List<MyObject> inv = ((MyCharacter)t).inventory;
		for(MyObject obj : inv) {
			if(obj instanceof Parchment) {
				((Parchment)obj).decrypt();
				GameModel.showMessage(this.speechs.get(1));
				GameModel.showMessage(obj.descriptif());
				return;
			}
		}
		GameModel.showMessage(this.speechs.get(2));

	}
	
	public void talkToBurns(Talkable t) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Talk with Burns");
            dialog.setContentText(this.speechs.get(0));
            
            Optional<String> result = dialog.showAndWait();
            int note = Integer.parseInt(result.get());  
            
            if(note < 7) {
                dialog.setContentText("-Burns : REALLY ? (-_-)\n"
                                    + "-Samuel : OK OK je rigole je comptais pas leur mettre une salle note de toutes facons\n"
                                    + "        : je vais revoir ma note a la hausse, aller\n");
                dialog.showAndWait();
            } else {
                if(note >= 7 && note < 15) {
                    dialog.setContentText("-Burns : OHHHHH honestly, don't they deserve more ?\n"
                                        + "        I will give you only one digit : '1***'\n"
                                        + "-Samuel : Mmmmmmm, c'est vrai qu'ils meritent plus...\n");
                    dialog.showAndWait();
                } else {
                    if(note >= 15 && note < 18) {
                        dialog.setContentText("-Burns : frankly, it touches me thank you\n"
                                            + "+ \"        I will give you three digit : '170*'\n"
                                            + "-Samuel : Okayyy, ils abusent un peu je trouve\n"
                                            + "        Mais c'est vrai que c'est bien foutu, aller je leurs met un 20/20\n");
                        dialog.showAndWait();
                    } else {
                        dialog.setContentText("-Burns : WAAAAAW, you're the best Samuel ;) \n"
                                            + "        Take the code : '1703'\n"
                                            + "-Samuel : Tres bien, finissons-en (c'etait cool GG)\n");
                    }
                }
            }
		
	}
    
}
