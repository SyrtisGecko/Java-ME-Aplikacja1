import java.io.IOException;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

public class PokazSzczegolyKontaktu extends Form implements CommandListener {
	
	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot; // wyczysc;
	private StringItem naglowek, separator;
	
	Emotikony emotikony;
	Kontakt kontakt;
	
	public PokazSzczegolyKontaktu(Kontakt kontakt, Displayable ekranPowrotny) {
		super("Szczegoly Kontaktu");
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		emotikony = new Emotikony();
		this.kontakt = kontakt;

		createCommands();
		
		addCommands();

		this.setCommandListener(this);
		
		wyswietlSzczegoly();
		
//		appendItems();
	}
	
	private void createCommands() {
		powrot = new Command("Powrot", Command.BACK, 1);
//		wyczysc = new Command("Wyczysc", Command.ITEM, 1);
	}
	
	private void addCommands() {
		this.addCommand(powrot);
//		this.addCommand(wyczysc);
	}
	
	private void wyswietlSzczegoly() {
		this.append(new StringItem(null, "Nazwa:", Item.LAYOUT_LEFT));
		this.append(new StringItem(null, kontakt.getNazwa(), Item.LAYOUT_CENTER));
		this.append(new StringItem(null, "Numer Telefonu:", Item.LAYOUT_LEFT));
		this.append(new StringItem(null, kontakt.getNrTelefonu(), Item.LAYOUT_CENTER));
		this.append(new StringItem(null, "Numer Alternatywny:", Item.LAYOUT_LEFT));
		this.append(new StringItem(null, kontakt.getNrAlternatywny(), Item.LAYOUT_CENTER));
		this.append(new StringItem(null, "E-mail:", Item.LAYOUT_LEFT));
		this.append(new StringItem(null, kontakt.getEmail(), Item.LAYOUT_CENTER));
		this.append(new StringItem(null, "Opis:", Item.LAYOUT_LEFT));
		this.append(new StringItem(null, kontakt.getOpis(), Item.LAYOUT_CENTER));
		this.append(new StringItem(null, "Emotikona:", Item.LAYOUT_LEFT));
		this.append(new ImageItem(null, zaladujEmote(), Item.LAYOUT_CENTER, null));
	}

	private Image zaladujEmote() {
		return emotikony.getEmot(Integer.parseInt(kontakt.getEmotikona()));
	}

	public void commandAction(Command komenda, Displayable elemEkranu) {
		if(komenda == powrot) {		
			wyswietlacz.setCurrent(ekranP);
			
		}
	}

}
