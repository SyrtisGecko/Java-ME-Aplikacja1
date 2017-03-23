import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/*****
 * 
 * @author Przemek
 *
 *MojMidlet1 - glowna klasa midletu
 *Tworzy ekran glowny, z ktorego mozna przejsc do pozostalych ekranow sluzacych do zapisu i odczytu tekstu
 *Przechowywany jest tu rowniez, w zmiennej magazyn, zapisany tekst
 *
 */

// TODO implement few other Comparators

public class MojMidlet1 extends MIDlet implements CommandListener {
	
	// deklaracja zmiennej wyswietlacza
	private static Display wyswietlacz;
	
	// deklaracja zmiennych dla komend
	private Command koniec, dodaj_kontakt, wyswietl_liste;

	Form ekranDodajKontaktForm;
	Form tb; 
	List ekranListaKontaktow;
	Image obr;
	ListaKontaktow listaKontaktow;
	
	
	public MojMidlet1() {

		wyswietlacz = Display.getDisplay(this);
		
		tb = new Form("Moj Midlet1 - Lista kontaktow");
		
		displayImage();
		
		listaKontaktow = new ListaKontaktow();
		
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
		ekranDodajKontaktForm = new EkranDodajKontakt(tb, listaKontaktow);
		ekranListaKontaktow = new EkranListaKontaktow(tb, listaKontaktow);
	}



	private void createCommands() {
		koniec = new Command("Koniec", Command.EXIT, 1);
		dodaj_kontakt = new Command("Dodaj Kontakt", Command.ITEM, 1);
		wyswietl_liste = new Command("Wyswietl liste", Command.ITEM, 1);
		
	}
	
	private void addCommands(Displayable d) {
		d.addCommand(koniec);
		d.addCommand(dodaj_kontakt);
		d.addCommand(wyswietl_liste);
		
	}
	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
		System.err.println("****Wywolano destroyApp****");
		
		listaKontaktow.zamknijMagazyn();
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// ustawienie aktualnego ekranu midletu na TextBox
		wyswietlacz.setCurrent(tb);
		
		
		// dodanie Listenera
		tb.setCommandListener((CommandListener)this);
		
		listaKontaktow.otworzMagazyn();
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
		} else if(komenda == dodaj_kontakt) {
			wyswietlacz.setCurrent(ekranDodajKontaktForm);
		} else if(komenda == wyswietl_liste) {
			wyswietlacz.setCurrent(ekranListaKontaktow);
			listaKontaktow.zaladujKontakty();
			((EkranListaKontaktow) ekranListaKontaktow).wyswietlKontakty();
		}
		
	}

	public static Display mojDisplay() {
		return wyswietlacz;
	}

}
