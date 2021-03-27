package model.myObjects;

import model.characters.MyCharacter;

public class Parchment extends MyObject {
	private boolean crypt = true;
	
	public Parchment() {
		super("Parchment", 0, "view/img/parchment.png", 2, 2);
	}
	
	public void decrypt() {
		this.crypt = false;
	}

	@Override
	public String descriptif() {
		if(this.crypt) {
			return this.getCryptedMessage();
		} else {
			return this.getdecryptedMessage();
		}
	}

	@Override
	public void use(MyCharacter c) {
		System.out.println(this.descriptif());
	}
	
	public String getCryptedMessage() {
		return "AZUL a si Burns,\n"
				+ "Mayella thellidh di l'Aditorium, thebghidh ada3didh ar ghouri di l'birru inu,\n"
				+ "attan l'code n tebburth id yi tawwin ar Production :\n"
				+ "'sin sin yiwen sifar'.\n"
				+ "3addid melmi ik yahwa, ayidafedh dakhel.\n"
				+ "Sahit.\n"
				+ "Smiters\n";
	}
	
	public String getdecryptedMessage() {
		return "Hello Mr Burns,\n"
				+ "If you are in the Aditorium, and you wan to came in my office\n"
				+ "this is the code of door to Production :\n"
				+ "'two two one zero'.\n"
				+ "You can came whenever you want, you will find me inside.\n"
				+ "Thank you.\n"
				+ "Smiters\n";
	}
	
	
	
		
}
