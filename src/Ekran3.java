import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.rms.RecordStoreException;

/*****
 * 
 * @author Przemek
 *
 *Klasa Ekran3 tworzy ekran sluzacy do wprowadzania tekstu i zapisywania go w magazynie
 *
 */

public class Ekran3 extends TextBox implements CommandListener {
	
	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot, zapisz;

	public Ekran3(Displayable ekranPowrotny) {
		super("Okno_3", "...Witaj w oknie 3", 256, 0);
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		
		// polecenie powrotu
		powrot = new Command("Powrot", Command.BACK, 1);		
		zapisz = new Command("Zapisz", Command.ITEM, 2);
		
		// dodanie komeny powrot do TextBox
		this.addCommand(powrot);
		
		this.addCommand(zapisz);
		
		// ustawienie Listenera
		this.setCommandListener(this);
	}

	public void commandAction(Command komenda, Displayable elemEkranu) {
		if(komenda == powrot) {
			
			wyswietlacz.setCurrent(ekranP);
		
		} else if(komenda == zapisz) {
			
			byte[] rekord = this.getString().getBytes();
			if(rekord.length > 0)
				try {
					MojMidlet1.magazyn.addRecord(rekord, 0, rekord.length);
					this.setString("...zapisane");
				} catch (RecordStoreException ex) {
					ex.printStackTrace();
				}
			else this.setString("...nic nie zapisano");
		}

	}

}
