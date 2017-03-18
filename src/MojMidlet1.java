import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.TextBox;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/*****
 * 
 * @author Przemek
 *
 *MojMidlet1 - glowna klasa midletu
 *Tworzy ekran glowny, z ktorego mozna przejsc do pozostalych ekranow sluzacych do zapisu i odczytu tekstu
 *Przechowywany jest tu rowniez, w zmiennej magazyn, zapisany tekst
 *
 */

public class MojMidlet1 extends MIDlet implements CommandListener {
	
	// deklaracja zmiennej wyswietlacza
	private static Display wyswietlacz;
	
	// deklaracja zmiennych TextBox
	Form tb; // okno2, okno3;
	
	// deklaracja zmiennych dla komend
	private Command koniec, dodaj_kontakt, wyswietl_liste; //o2, wprowadz, wyswietl, ;
	
	// deklaracja magazynu przechowujacego zapisany tekst
	public static RecordStore magazyn;

	Form dodajKontaktForm;
	Form listaKontaktow;
	Image obr;
	
//	List listaKontaktow;
	
	
	public MojMidlet1() {

		wyswietlacz = Display.getDisplay(this);
		
		tb = new Form("Moj Midlet1 - Lista kontaktow");
		
		displayImage();
		
		// tworzenie komend
		createCommands();
				
		// dodanie komend
		addCommands(tb);
		
		// utworzenie obiektow pozostalych ekranow
		addScreens(tb);		
	}

	

	private void displayImage() {
		try {
			obr = Image.createImage("/img/friends-icon.png");
		} catch (IOException e) {
			e.printStackTrace();
			obr = null;
		}
	tb.append(new ImageItem(null, obr, ImageItem.LAYOUT_CENTER, null));
}



	private void addScreens(Form tb) {
//		okno2 = (TextBox) new Ekran2(tb);
//		okno3 = (TextBox) new Ekran3(tb);
		dodajKontaktForm = new EkranDodajKontakt(tb);
//		String[] str = {"Kontakt_1", "Kontakt_2", "Kontakt_3"};
		listaKontaktow = new ListaKontaktow(tb);
	}



	private void createCommands() {
		koniec = new Command("Koniec", Command.EXIT, 1);
//		o2 = new Command("Okno_2", Command.SCREEN, 1);
//		wprowadz = new Command("Wprowadz dane", Command.ITEM, 1);
//		wyswietl = new Command("Wyswietl dane", Command.ITEM, 1);
		dodaj_kontakt = new Command("Dodaj Kontakt", Command.ITEM, 1);
		wyswietl_liste = new Command("Wyswietl liste", Command.ITEM, 1);
		
	}
	
	private void addCommands(Displayable d) {
//		d.addCommand(o2);
//		d.addCommand(wprowadz);
//		d.addCommand(wyswietl);
		d.addCommand(koniec);
		d.addCommand(dodaj_kontakt);
		d.addCommand(wyswietl_liste);
		
	}
	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
		System.err.println("****Wywolano destroyApp****");
		
		try {
			magazyn.closeRecordStore();
		}
		catch (RecordStoreException ex) {
			ex.printStackTrace();
		}

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// ustawienie aktualnego ekranu midletu na TextBox
		wyswietlacz.setCurrent(tb);
		
		
		// dodanie Listenera
		tb.setCommandListener((CommandListener)this);
		
		try {
			magazyn = RecordStore.openRecordStore("Wpisy", true, RecordStore.AUTHMODE_PRIVATE, false);
		}
		catch (RecordStoreException ex) {
			ex.printStackTrace();
		}
	}
	
	

	public void commandAction(Command komenda, Displayable elemEkranu) {
		if(komenda == koniec) {
			// usun aplikacje
			try {
				destroyApp(false);
			} catch (MIDletStateChangeException e) {
				e.printStackTrace();
			}
			// zawiadom platforme
			notifyDestroyed();
//		} else if(komenda == o2) {
//			wyswietlacz.setCurrent(okno2);
//		} else if(komenda == wprowadz) {
//			wyswietlacz.setCurrent(okno3);
//		} else if(komenda == wyswietl) {
//			wyswietlacz.setCurrent(okno2);
//			((Ekran2) okno2).wyswietlCalyTekst();
		} else if(komenda == dodaj_kontakt) {
			wyswietlacz.setCurrent(dodajKontaktForm);
		} else if(komenda == wyswietl_liste) {
			wyswietlacz.setCurrent(listaKontaktow);
		}
		
	}

	public static Display mojDisplay() {
		return wyswietlacz;
	}

}
