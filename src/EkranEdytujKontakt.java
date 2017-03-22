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

public class EkranEdytujKontakt extends Form implements CommandListener {

	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot, zapisz;
	private TextField nazwaText, numerTelefonu, numerAlternatywny, opis, email;
	private StringItem naglowek, separator;
	private ChoiceGroup wyborEmotikony;
	
	Kontakt kontakt;
	ListaKontaktow listaKontaktow;

	Emotikony emotikony;
	
	public EkranEdytujKontakt(Displayable ekranPowrotny, ListaKontaktow listaKontaktow, Kontakt kontakt) {
		super("Edytuj Kontakt");
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		emotikony = new Emotikony();
		this.listaKontaktow = listaKontaktow;
		this.kontakt = kontakt;
		
		createCommands();
		
		addCommands();

		this.setCommandListener(this);
		
		defineFormItems();
		
		appendItems();
		
	}

	private void addCommands() {
		this.addCommand(powrot);
		this.addCommand(zapisz);
	}

	private void createCommands() {
		powrot = new Command("Powrot", Command.BACK, 1);		
		zapisz = new Command("Zapisz", Command.ITEM, 1);
	}

	private void defineFormItems() {
		naglowek = new StringItem(null, "Edytowanie kontaktu");
		separator = new StringItem(null, "----------------------------------");
		nazwaText = new TextField("Nazwa:", kontakt.getNazwa(), 20, TextField.ANY);
		numerTelefonu = new TextField("Numer telefonu:", kontakt.getNrTelefonu(), 16, TextField.PHONENUMBER);
		numerAlternatywny = new TextField("Numer alternatywny:", kontakt.getNrAlternatywny(), 16, TextField.PHONENUMBER);
		email = new TextField("Adres e-mail:", kontakt.getEmail(), 30, TextField.EMAILADDR);
		opis = new TextField("Opis:", kontakt.getOpis(), 100, TextField.ANY);
		wyborEmotikony = new ChoiceGroup("Wybierz emotikone:", Choice.EXCLUSIVE, pustyString(emotikony.getSize()), emotikony.getArrayOfEmots());	
		wyborEmotikony.setSelectedIndex(Integer.parseInt(kontakt.getEmotikona()), true);
	}

	private String[] pustyString(int size) {
		String[] str = new String[size];
		for(int i = 0; i < size; i++) {
			str[i] = "";
		}
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
			str_wyj.writeInt(kontakt.getID());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] rekord = str_b.toByteArray();
		String Tekst = new String(rekord);
		System.out.println(Tekst);
		try {
			listaKontaktow.magazyn.setRecord(kontakt.getID(), rekord, 0, rekord.length);
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
	
	private void edytujKontakt() {
		kontakt.setNazwa(nazwaText.getString());
		kontakt.setNrTelefonu(numerTelefonu.getString());
		kontakt.setNrAlternatywny(numerAlternatywny.getString());
		kontakt.setEmail(email.getString());
		kontakt.setOpis(opis.getString());
		kontakt.setEmotikona(emotikony.getStringIndex(wyborEmotikony.getImage(wyborEmotikony.getSelectedIndex())));
	}
		

	public void commandAction(Command komenda, Displayable elemEkranu) {
		
		if(komenda == powrot) {		
			wyswietlacz.setCurrent(ekranP);
			((EkranListaKontaktow) ekranP).wyswietlKontakty();
			
		} else if(komenda == zapisz) {
			
			if(nazwaText.getString().equals("") || nazwaText.getString().equals(null)) {
				nieprawidlowyKontaktPopUp();
				
			} else {
				edytujKontakt();
				kontakt.wyswietl();
				zapiszKontakt();
				edytowanyKontaktPopUp();
				listaKontaktow.zaladujKontakty();
			}
		} 

	}
	
	private void edytowanyKontaktPopUp() {
		Alert edytowanyKontaktAlert = new Alert("Zedytowano kontakt", "\t\"" + nazwaText.getString() + "\".", null, AlertType.INFO);
		edytowanyKontaktAlert.setTimeout(2500);
		wyswietlacz.setCurrent(edytowanyKontaktAlert, this);
	}
	
	private void nieprawidlowyKontaktPopUp() {
		Alert nieprawidlowyKontaktAlert = new Alert("!!!UWAGA!!!", "Nieprawidlowe dane. Nie mozna dodac do listy kontaktow.", null, AlertType.WARNING);
		nieprawidlowyKontaktAlert.setTimeout(2500);
		wyswietlacz.setCurrent(nieprawidlowyKontaktAlert, this);
	}

}
