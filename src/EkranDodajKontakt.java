import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStoreException;

public class EkranDodajKontakt extends Form implements CommandListener {
	
	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot, zapisz, wyczysc;
	private TextField nazwaText, numerTelefonu, numerAlternatywny, opis, email;
	private StringItem naglowek, separator;
	private ChoiceGroup wyborEmotikony;
//	private ImageItem obr;
//	Alert nowyKontaktAlert;
	
	Kontakt kontakt;

	private String[] opcja = {"", "", ""};
	private Image[] image = {null, null, null};
	
	public EkranDodajKontakt(Displayable ekranPowrotny) {
		super("Dodaj Kontakt");
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		

		powrot = new Command("Powrot", Command.BACK, 1);		
		zapisz = new Command("Zapisz", Command.ITEM, 1);
		wyczysc = new Command("Wyczysc", Command.ITEM, 1);

		this.addCommand(powrot);
		this.addCommand(zapisz);
		this.addCommand(wyczysc);

		this.setCommandListener(this);
		
		defineFormItems();
		
		appendItems();
		
	}

	private void defineFormItems() {
		naglowek = new StringItem(null, "Dodawanie nowego kontaktu");
		separator = new StringItem(null, "----------------------------------");
		nazwaText = new TextField("Nazwa:", "", 20, TextField.ANY);
		numerTelefonu = new TextField("Numer telefonu:", "", 16, TextField.PHONENUMBER);
		numerAlternatywny = new TextField("Numer alternatywny:", "", 16, TextField.PHONENUMBER);
		email = new TextField("Adres e-mail:", "", 30, TextField.EMAILADDR);
		opis = new TextField("Opis:", "", 100, TextField.ANY);
		loadImg();
		wyborEmotikony = new ChoiceGroup("Wybierz emotikone:", Choice.EXCLUSIVE, opcja, image);	
//		obr = new ImageItem(null, image[0], ImageItem.LAYOUT_DEFAULT, null);
	}

	private void appendItems() {
		this.append(naglowek);
		this.append(separator);
		this.append(nazwaText);
		this.append(numerTelefonu);
		this.append(numerAlternatywny);
		this.append(email);
		this.append(opis);
//		this.append(obr);
		this.append(wyborEmotikony);		
	}
	
	private void loadImg() {
		Image img = null;
		try {
			img = Image.createImage("/img/passive.png");
		} catch (java.io.IOException e) {
			e.printStackTrace();
			img = null;
		}
		
		image[0] = img;
		image[1] = img;
		image[2] = img;
	}
	
	private void nowyKontaktPopUp() {
		Alert nowyKontaktAlert = new Alert("Nowy kontakt", "\"" + nazwaText.getString() + "\" dodano do listy kontaktow.", null, AlertType.INFO);
		nowyKontaktAlert.setTimeout(2000);
		wyswietlacz.setCurrent(nowyKontaktAlert, this);
	}
	
	private void nieprawidlowyKontaktPopUp() {
		Alert nieprawidlowyKontaktAlert = new Alert("!!!UWAGA!!!", "Nieprawidlowe dane. Nie mozna dodac do listy kontaktow.", null, AlertType.WARNING);
		nieprawidlowyKontaktAlert.setTimeout(2000);
		wyswietlacz.setCurrent(nieprawidlowyKontaktAlert, this);
	}
	
	private void wyczyscPola() {
		nazwaText.setString("");
		numerTelefonu.setString("");
		numerAlternatywny.setString("");
		email.setString("");
		opis.setString("");
	}
	
	private void zapiszKontakt() {
		ByteArrayOutputStream str_b = new ByteArrayOutputStream();
		DataOutputStream str_wyj = new DataOutputStream(str_b);
		try {
			str_wyj.writeUTF(kontakt.getNazwa());
			str_wyj.writeUTF(kontakt.getNrTelefonu());
			str_wyj.writeUTF(kontakt.getNrAlternatywny());
			str_wyj.writeUTF(kontakt.getEmail());
			str_wyj.writeUTF(kontakt.getOpis());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] rekord = str_b.toByteArray();
		try {
			MojMidlet1.magazyn.addRecord(rekord, 0, rekord.length);
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void commandAction(Command komenda, Displayable elemEkranu) {
		
		if(komenda == powrot) {		
			wyswietlacz.setCurrent(ekranP);
			
		} else if(komenda == zapisz) {
			
			if(nazwaText.getString().equals("") || nazwaText.getString().equals(null)) {
				nieprawidlowyKontaktPopUp();
				
			} else {
				kontakt = new Kontakt(nazwaText.getString(), numerTelefonu.getString(), numerAlternatywny.getString(), email.getString(), opis.getString());
				kontakt.wyswietl();
				zapiszKontakt();
				nowyKontaktPopUp();
				wyczyscPola();
			}
			
//			byte[] rekord = this.getString().getBytes();
//			if(rekord.length > 0)
//				try {
//					MojMidlet1.magazyn.addRecord(rekord, 0, rekord.length);
//					this.setString("...zapisane");
//				} catch (RecordStoreException ex) {
//					ex.printStackTrace();
//				}
//			else this.setString("...nic nie zapisano");
		} else if(komenda == wyczysc) {
			wyczyscPola();
		}

	}

}
