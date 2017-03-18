import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class ListaKontaktow extends List implements CommandListener {
	
	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot, wybierz, usun, usun_wszystkie, tak, nie;
	private RecordEnumeration iterator;
	private Vector kontakty;
	
	Emotikony emoty;

	public ListaKontaktow(Displayable ekranPowrotny) {
		super("Twoja Lista Kontaktow", List.EXCLUSIVE);
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		emoty = new Emotikony();
		
		createCommands();
		
		addCommands();
		
		this.setCommandListener(this);
		
	}
	

	protected void zaladujKontakty() {
		kontakty = new Vector();

		try {
			iterator = MojMidlet1.magazyn.enumerateRecords(null, new KomparatorTekstu(), false);
			
			while(iterator.hasNextElement()) {
				byte[] rekord = iterator.nextRecord();
				String Tekst = new String(rekord);
				System.out.println(Tekst);
				ByteArrayInputStream str_b = new ByteArrayInputStream(rekord);
				DataInputStream str_wej = new DataInputStream(str_b);
				
				try {
					Kontakt kontakt = new Kontakt(str_wej.readUTF(), str_wej.readUTF(), str_wej.readUTF(), str_wej.readUTF(), str_wej.readUTF(), str_wej.readUTF());
					kontakty.addElement(kontakt);
					System.out.print("Dodano do wektora: ");
					kontakt.wyswietl();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (RecordStoreException ex) {
			ex.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Magazyn pusty");
		}
}


	protected void wyswietlKontakty() {
		this.deleteAll();
		
		for(int i = 0; i < kontakty.size(); i++) {
			this.append(((Kontakt) kontakty.elementAt(i)).getNazwa(), 
							emoty.getEmot(Integer.parseInt(((Kontakt)kontakty.elementAt(i)).getEmotikona())));
		}
	}


	private void createCommands() {
		powrot = new Command("Powrot", Command.BACK, 1);
		wybierz = new Command("Pokaz szczegoly", Command.ITEM, 3);
		usun = new Command("Usun kontakt", Command.ITEM, 1);
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
		Alert usunWszystkoAlert = new Alert("!!!UWAGA!!!", "Czy napewno chcesz usunac wszystkie kontakty?", null, AlertType.CONFIRMATION);
		usunWszystkoAlert.addCommand(tak);
		usunWszystkoAlert.addCommand(nie);
		usunWszystkoAlert.setCommandListener(this);
		
		wyswietlacz.setCurrent(usunWszystkoAlert);
		
	}
	
	private void wyczyscMagazyn() {
//		RecordEnumeration iterator;
		System.out.println("wyczyscMagazyn()");
		try {
//			iterator = MojMidlet1.magazyn.enumerateRecords(null, null, false);
			System.out.println("ID " + MojMidlet1.magazyn.getNextRecordID());
			MojMidlet1.magazyn.closeRecordStore();
			RecordStore.deleteRecordStore("Wpisy");
			MojMidlet1.magazyn = RecordStore.openRecordStore("Wpisy", true, RecordStore.AUTHMODE_PRIVATE, false);
			System.out.println("ID " + MojMidlet1.magazyn.getNextRecordID());
			
//			while(iterator.hasNextElement()) {
//				int i = iterator.nextRecordId();
////				byte[] rekord = iterator.nextRecord();
//				MojMidlet1.magazyn.deleteRecord(i);
//				System.out.println("wyczyscMagazyn().rekord " + i);
//			}
		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
//		} catch (InvalidRecordIDException e) {
//			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}


	public void commandAction(Command komenda, Displayable elemEkranu) {
		if(komenda == powrot) {
			wyswietlacz.setCurrent(ekranP);
		} else if(komenda == wybierz) {

		} else if(komenda == usun) {

		} else if(komenda == usun_wszystkie) {
			usunWszystkoPopUp();
		} else if(komenda == tak) {
			System.out.println("Wybrano TAK");
			wyczyscMagazyn();
			wyswietlacz.setCurrent(this);
		} else if(komenda == nie) {
			System.out.println("Wybrano NIE");
			wyswietlacz.setCurrent(this);
		} 
	}

}
