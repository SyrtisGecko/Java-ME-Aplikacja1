import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/****
 * 
 * @author Przemek
 *
 * Klasa obslugujaca ekran sluzacy do wyswietlania listy z zapisanymi Zdarzeniami
 *
 */
public class EkranTerminarz extends List implements CommandListener {

	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot, wybierz, usun, usun_wszystkie, tak, nie;
	Terminarz terminarz;
	
	
	EkranPokazSzczegolyZdarzenia szczegolyZdarzenia;

	public EkranTerminarz(Displayable ekranPowrotny, Terminarz terminarz) {
		super("Twoj terminarz", List.EXCLUSIVE);
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		this.terminarz = terminarz;
		
		createCommands();
		
		addCommands();
		
		this.setCommandListener(this);		
	}


	protected void wyswietlZdarzenia() {
		this.deleteAll();
		
		for(int i = 0; i < terminarz.getSize(); i++) {
			this.append(terminarz.getData(i), 
						null);
		}
	}


	private void createCommands() {
		powrot = new Command("Powrot", Command.BACK, 1);
		wybierz = new Command("Pokaz szczegoly", Command.ITEM, 3);
		usun = new Command("Usun zdarzenie", Command.ITEM, 1);
		usun_wszystkie = new Command("Usun wszystkie", Command.ITEM, 1);
		tak = new Command("TAK", Command.OK, 1);
		nie = new Command("NIE", Command.CANCEL, 1);
	}

	private void addCommands() {
		this.addCommand(powrot);		
		this.addCommand(wybierz);	
		this.addCommand(usun);
		this.addCommand(usun_wszystkie);
	}
	
	private void usunWszystkoPopUp() {
		Alert usunWszystkoAlert = new Alert("!!!UWAGA!!!", "Czy napewno chcesz usunac wszystkie zdarzenia?", null, AlertType.CONFIRMATION);
		usunWszystkoAlert.addCommand(tak);
		usunWszystkoAlert.addCommand(nie);
		usunWszystkoAlert.setCommandListener(this);
		
		wyswietlacz.setCurrent(usunWszystkoAlert);	
	}
	
	private Zdarzenie getSelectedZdarzenia() {
		return terminarz.getSelectedZdarzenia(this.getSelectedIndex());
	}


	public void commandAction(Command komenda, Displayable elemEkranu) {
		if(komenda == powrot) {
			wyswietlacz.setCurrent(ekranP);
		} else if(komenda == wybierz) {
			szczegolyZdarzenia = new EkranPokazSzczegolyZdarzenia(getSelectedZdarzenia(), this);
			wyswietlacz.setCurrent(szczegolyZdarzenia);
		} else if(komenda == usun) {
			terminarz.usunZdarzenie(this.getSelectedIndex());
			wyswietlZdarzenia();
		} else if(komenda == usun_wszystkie) {
			usunWszystkoPopUp();
		} else if(komenda == tak) {
			System.out.println("Wybrano TAK");
			terminarz.wyczyscMagazyn();
			wyswietlZdarzenia();
			wyswietlacz.setCurrent(this);
		} else if(komenda == nie) {
			System.out.println("Wybrano NIE");
			wyswietlacz.setCurrent(this);
//		} else if(komenda == edytuj) {
//			edytujKontakt = new EkranEdytujKontakt(this, listaKontaktow, getSelectedKontakt());
//			wyswietlacz.setCurrent(edytujKontakt);
		}
	}

}
