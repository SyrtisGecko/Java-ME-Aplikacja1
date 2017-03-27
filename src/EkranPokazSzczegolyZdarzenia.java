import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

/****
 * 
 * @author Przemek
 *
 * Klasa obslugujaca ekran sluzacy do wyswietlania szczegolow zapisanego Zdarzenia
 *
 */
public class EkranPokazSzczegolyZdarzenia extends Form implements CommandListener {

	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot;
	private StringItem data_label, data, opis_label, opis;
	
	Zdarzenie zdarzenie;
	
	public EkranPokazSzczegolyZdarzenia(Zdarzenie zdarzenie, Displayable ekranPowrotny) {
		super("Szczegoly Zdarzenia");
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		this.zdarzenie = zdarzenie;

		createCommands();
		
		addCommands();

		this.setCommandListener(this);
		
		defineFormItems();
		
		appendItems();
	}
	
	private void createCommands() {
		powrot = new Command("Powrot", Command.BACK, 1);
	}
	
	private void addCommands() {
		this.addCommand(powrot);
	}
	
	private void defineFormItems() {
		data_label = new StringItem(null, "Data:");
		data = new StringItem(null, zdarzenie.getData());
		opis_label = new StringItem(null, "Opis:");
		opis = new StringItem(null, zdarzenie.getOpis());
	}
	
	private void appendItems() {
		this.append(data_label);
		data_label.setLayout(Item.LAYOUT_LEFT);
		this.append(data);
		data.setLayout(Item.LAYOUT_CENTER);
		this.append(opis_label);
		opis_label.setLayout(Item.LAYOUT_LEFT);
		this.append(opis);
		opis.setLayout(Item.LAYOUT_CENTER);
	}

	public void commandAction(Command komenda, Displayable elemEkranu) {
		if(komenda == powrot) {		
			wyswietlacz.setCurrent(ekranP);
			
		}
	}

}
