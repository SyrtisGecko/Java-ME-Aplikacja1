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
 * Klasa obslugujaca ekran sluzacy do wyswietlania listy z zapisanymi Kontaktami
 *
 */
public class EkranListaKontaktow extends List implements CommandListener {
	
	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot, wybierz, usun, usun_wszystkie, tak, nie, edytuj;
	ListaKontaktow listaKontaktow;
	
	Emotikony emoty;
	
	EkranPokazSzczegolyKontaktu szczegolyKontaktu;
	EkranEdytujKontakt edytujKontakt;

	public EkranListaKontaktow(Displayable ekranPowrotny, ListaKontaktow listaKontaktow) {
		super("Twoja Lista Kontaktow", List.EXCLUSIVE);
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		this.listaKontaktow = listaKontaktow;
		emoty = new Emotikony();
		
		createCommands();
		
		addCommands();
		
		this.setCommandListener(this);		
	}


	protected void wyswietlKontakty() {
		this.deleteAll();
		
		for(int i = 0; i < listaKontaktow.getSize(); i++) {
			this.append(listaKontaktow.getNazwaKontaktu(i), 
						listaKontaktow.getEmotikonaKontaktu(i));
		}
	}


	private void createCommands() {
		powrot = new Command("Powrot", Command.BACK, 1);
		wybierz = new Command("Pokaz szczegoly", Command.ITEM, 3);
		usun = new Command("Usun kontakt", Command.ITEM, 1);
		usun_wszystkie = new Command("Usun wszystkie", Command.ITEM, 1);
		tak = new Command("TAK", Command.OK, 1);
		nie = new Command("NIE", Command.CANCEL, 1);
		edytuj = new Command("Edytuj kontakt", Command.ITEM, 2);
	}

	private void addCommands() {
		this.addCommand(powrot);		
		this.addCommand(wybierz);	
		this.addCommand(usun);
		this.addCommand(usun_wszystkie);
		this.addCommand(edytuj);
	}
	
	private void usunWszystkoPopUp() {
		Alert usunWszystkoAlert = new Alert("!!!UWAGA!!!", "Czy napewno chcesz usunac wszystkie kontakty?", null, AlertType.CONFIRMATION);
		usunWszystkoAlert.addCommand(tak);
		usunWszystkoAlert.addCommand(nie);
		usunWszystkoAlert.setCommandListener(this);
		
		wyswietlacz.setCurrent(usunWszystkoAlert);	
	}
	
	private Kontakt getSelectedKontakt() {
		return listaKontaktow.getSelectedKontakt(this.getSelectedIndex());
	}


	public void commandAction(Command komenda, Displayable elemEkranu) {
		if(komenda == powrot) {
			wyswietlacz.setCurrent(ekranP);
		} else if(komenda == wybierz) {
			szczegolyKontaktu = new EkranPokazSzczegolyKontaktu(getSelectedKontakt(), this);
			wyswietlacz.setCurrent(szczegolyKontaktu);
		} else if(komenda == usun) {
			listaKontaktow.usunKontakt(this.getSelectedIndex());
			wyswietlKontakty();
		} else if(komenda == usun_wszystkie) {
			usunWszystkoPopUp();
		} else if(komenda == tak) {
			System.out.println("Wybrano TAK");
			listaKontaktow.wyczyscMagazyn();
			wyswietlKontakty();
			wyswietlacz.setCurrent(this);
		} else if(komenda == nie) {
			System.out.println("Wybrano NIE");
			wyswietlacz.setCurrent(this);
		} else if(komenda == edytuj) {
			edytujKontakt = new EkranEdytujKontakt(this, listaKontaktow, getSelectedKontakt());
			wyswietlacz.setCurrent(edytujKontakt);
		}
	}

}
