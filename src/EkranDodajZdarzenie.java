import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStoreException;

/****
 * 
 * @author Przemek
 *
 * Klasa obslugujaca ekran sluzacy do dodawania nowych Zdarzen
 *
 */
public class EkranDodajZdarzenie extends Form implements CommandListener {

	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot, zapisz, wyczysc;
	private TextField opis;
	private StringItem naglowek, separator;
	private DateField data;
	private int dzien, miesiac, rok;
	
	Zdarzenie zdarzenie;
	Terminarz terminarz;

	
	public EkranDodajZdarzenie(Displayable ekranPowrotny, Terminarz terminarz) {
		super("Dodaj Zdarzenie");
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		this.terminarz = terminarz;
		
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
		naglowek = new StringItem(null, "Dodawanie zaplanowanego zdarzenia");
		separator = new StringItem(null, "----------------------------------");
		data = new DateField("Data: ", DateField.DATE);
		opis = new TextField("Opis:", "", 200, TextField.ANY);
	}

	private void appendItems() {
		this.append(naglowek);
		this.append(separator);
		this.append(data);
		this.append(opis);		
	}
	
	private void wyczyscPola() {
		opis.setString("");
	}
	
	private void rozdzielDate() {
		System.out.println(data.toString());
		Calendar cal= Calendar.getInstance();
		cal.setTime(data.getDate());
		dzien = cal.get(Calendar.DAY_OF_MONTH);
		miesiac = cal.get(Calendar.MONTH);
		rok = cal.get(Calendar.YEAR);
	}
	
	private void zapiszZdarzenie() {
		
		ByteArrayOutputStream str_b = new ByteArrayOutputStream();
		DataOutputStream str_wyj = new DataOutputStream(str_b);
		try {
			str_wyj.writeInt(zdarzenie.getDzien());
			str_wyj.writeInt(zdarzenie.getMiesiac());
			str_wyj.writeInt(zdarzenie.getRok());
			str_wyj.writeUTF(zdarzenie.getOpis());
			str_wyj.writeInt(zdarzenie.getID());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] rekord = str_b.toByteArray();
		String Tekst = new String(rekord);
		System.out.println(Tekst);
		try {
			terminarz.magazynZdarzen.addRecord(rekord, 0, rekord.length);
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
			rozdzielDate();
				try {
					zdarzenie = new Zdarzenie(dzien, miesiac, rok, opis.getString(), terminarz.magazynZdarzen.getNextRecordID());
				} catch (RecordStoreException e) {
					e.printStackTrace();
				}
				zapiszZdarzenie();
				noweZdarzeniePopUp();
				wyczyscPola();
			
		} else if(komenda == wyczysc) {
			wyczyscPola();
		}

	}
	
	private void noweZdarzeniePopUp() {
		Alert nowyKontaktAlert = new Alert("INFO", "Nowe zdarzenie zostalo dodane.", null, AlertType.INFO);
		nowyKontaktAlert.setTimeout(2500);
		wyswietlacz.setCurrent(nowyKontaktAlert, this);
	}
	
}
