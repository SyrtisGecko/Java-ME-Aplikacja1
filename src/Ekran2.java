import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStoreException;

/*****
 * 
 * @author Przemek
 *
 *Klasa Ekran2 tworzy ekran sluzacy do wyswietlania calego tekstu z magazynu
 *Mozna rowniez przejsc do okno3 midletu
 *
 */

public class Ekran2 extends TextBox implements CommandListener {
	
	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot, wprowadz, wyswietl;
	private RecordEnumeration iterator;
	private String CalyText;
	TextBox okno3;
	

	public Ekran2(Displayable ekranPowrotny) {
		super("Okno_2", "...Witaj w oknie 2", 256, 0);
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		
		// tworzenie polecen
		createCommands();
		
		// dodanie komend do TextBox
		addCommands();
		
		// ustawienie Listenera
		this.setCommandListener(this);
		

		okno3 = new Ekran3(this);
	}

	private void addCommands() {
		this.addCommand(powrot);		
		this.addCommand(wprowadz);
		this.addCommand(wyswietl);		
	}

	private void createCommands() {
		powrot = new Command("Powrot", Command.BACK, 1);		
		wprowadz = new Command("Wprowadz dane", Command.ITEM, 2);
		wyswietl = new Command("Wyswietl", Command.ITEM, 1);		
	}

	public void commandAction(Command komenda, Displayable elemEkranu) {
		if(komenda == powrot) {
			wyswietlacz.setCurrent(ekranP);
		} else if(komenda == wprowadz) {
			
			wyswietlacz.setCurrent(okno3);

		} else if(komenda == wyswietl) {
			wyswietlCalyTekst();
		}
	}
	
	public void wyswietlCalyTekst() {
		CalyText = "";
		try {
			iterator = MojMidlet1.magazyn.enumerateRecords(null, new KomparatorTekstu(), false);
			
			while(iterator.hasNextElement()) {
				byte[] rekord = iterator.nextRecord();
				String Tekst = new String(rekord);
				CalyText += (Tekst + "\n");
			}
		} catch (RecordStoreException ex) {
			ex.printStackTrace();
		}
		this.setString(CalyText);
	}
	


}
