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
	
	Kontakt kontakt;
	ListaKontaktow listaKontaktow;

	Emotikony emotikony;
	
	public EkranDodajKontakt(Displayable ekranPowrotny, ListaKontaktow listaKontaktow) {
		super("Dodaj Kontakt");
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		emotikony = new Emotikony();
		this.listaKontaktow = listaKontaktow;
		
		createCommands();
		
		addCommands();

		this.setCommandListener(this);
		
		defineFormItems();
		
		appendItems();
		
	}

	private void addCommands() {
		this.addCommand(powrot);
		this.addCommand(zapisz);
		this.addCommand(wyczysc);
	}

	private void createCommands() {
		powrot = new Command("Powrot", Command.BACK, 1);		
		zapisz = new Command("Zapisz", Command.ITEM, 1);
		wyczysc = new Command("Wyczysc", Command.ITEM, 1);
	}

	private void defineFormItems() {
		naglowek = new StringItem(null, "Dodawanie nowego kontaktu");
		separator = new StringItem(null, "----------------------------------");
		nazwaText = new TextField("Nazwa:", "", 20, TextField.ANY);
		numerTelefonu = new TextField("Numer telefonu:", "", 16, TextField.PHONENUMBER);
		numerAlternatywny = new TextField("Numer alternatywny:", "", 16, TextField.PHONENUMBER);
		email = new TextField("Adres e-mail:", "", 30, TextField.EMAILADDR);
		opis = new TextField("Opis:", "", 100, TextField.ANY);
		wyborEmotikony = new ChoiceGroup("Wybierz emotikone:", Choice.EXCLUSIVE, pustyString(emotikony.getSize()), emotikony.getArrayOfEmots());	
	}

	private String[] pustyString(int size) {
		String[] str = new String[size];
		for(int i = 0; i < size; i++) {
			str[i] = "";
		}
		System.out.println(str.length);
		return str;
	}

	private void appendItems() {
		this.append(naglowek);
		this.append(separator);
		this.append(nazwaText);
		this.append(numerTelefonu);
		this.append(numerAlternatywny);
		this.append(email);
		this.append(opis);
		this.append(wyborEmotikony);		
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
			str_wyj.writeUTF(kontakt.getEmotikona());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] rekord = str_b.toByteArray();
		String Tekst = new String(rekord);
		System.out.println(Tekst);
		try {
			MojMidlet1.magazyn.addRecord(rekord, 0, rekord.length);
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		try {
			str_b.close();
			str_wyj.close();
		} catch (IOException e) {
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
				kontakt = new Kontakt(nazwaText.getString(), numerTelefonu.getString(), numerAlternatywny.getString(), email.getString(), opis.getString(), emotikony.getStringIndex(wyborEmotikony.getImage(wyborEmotikony.getSelectedIndex())));
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
	
	private void nowyKontaktPopUp() {
		Alert nowyKontaktAlert = new Alert("Nowy kontakt", "\"" + nazwaText.getString() + "\" dodano do listy kontaktow.", null, AlertType.INFO);
		nowyKontaktAlert.setTimeout(2500);
		wyswietlacz.setCurrent(nowyKontaktAlert, this);
	}
	
	private void nieprawidlowyKontaktPopUp() {
		Alert nieprawidlowyKontaktAlert = new Alert("!!!UWAGA!!!", "Nieprawidlowe dane. Nie mozna dodac do listy kontaktow.", null, AlertType.WARNING);
		nieprawidlowyKontaktAlert.setTimeout(2500);
		wyswietlacz.setCurrent(nieprawidlowyKontaktAlert, this);
	}

}
