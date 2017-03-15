import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
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
	TextBox tb, okno2, okno3;
	
	// deklaracja zmiennych dla komend
	private Command koniec, o2, wprowadz, wyswietl, dodaj_kontakt;
	
	// deklaracja magazynu przechowujacego zapisany tekst
	public static RecordStore magazyn;

	Form dodajKontaktForm;
	
	public MojMidlet1() {

		wyswietlacz = Display.getDisplay(this);
		
		tb = new TextBox("Moj Midlet1", "Witaj uzytkowniku!!", 256, 0);
		
		// tworzenie komend
		createCommands();
				
		// dodanie komend
		addCommands(tb);
		
		// utworzenie obiektow pozostalych ekranow
		addScreens(tb);		
	}

	

	private void addScreens(TextBox tb) {
		okno2 = (TextBox) new Ekran2(tb);
		okno3 = (TextBox) new Ekran3(tb);
		dodajKontaktForm = new EkranDodajKontakt(tb);
	}



	private void createCommands() {
		koniec = new Command("Koniec", Command.EXIT, 1);
//		o2 = new Command("Okno_2", Command.SCREEN, 1);
		wprowadz = new Command("Wprowadz dane", Command.ITEM, 1);
		wyswietl = new Command("Wyswietl dane", Command.ITEM, 1);
		dodaj_kontakt = new Command("Dodaj Kontakt", Command.ITEM, 1);
		
	}
	
	private void addCommands(Displayable d) {
//		d.addCommand(o2);
		d.addCommand(wprowadz);
		d.addCommand(wyswietl);
		d.addCommand(koniec);
		d.addCommand(dodaj_kontakt);
		
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// zawiadom platforme
			notifyDestroyed();
		} else if(komenda == o2) {
			wyswietlacz.setCurrent(okno2);
		} else if(komenda == wprowadz) {
			wyswietlacz.setCurrent(okno3);
		} else if(komenda == wyswietl) {
			wyswietlacz.setCurrent(okno2);
			((Ekran2) okno2).wyswietlCalyTekst();
		} else if(komenda == dodaj_kontakt) {
			wyswietlacz.setCurrent(dodajKontaktForm);
		}
		
	}

	public static Display mojDisplay() {
		return wyswietlacz;
	}

}
