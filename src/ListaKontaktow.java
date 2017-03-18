import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;
import javax.microedition.rms.InvalidRecordIDException;
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
	
	StringItem item_1, item_2, item_3, item_4;
	String[] str = {"str_1", "str_2", "str_3"};
	Emotikony emoty;
	Image[] img = {null, null, null};

	public ListaKontaktow(Displayable ekranPowrotny) {
		super("Twoja Lista Kontaktow", List.EXCLUSIVE);
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		kontakty = new Vector();
		
		emoty = new Emotikony();
		img[0] = emoty.getEmot(4);
		img[1] = emoty.getEmot(1);
		img[2] = emoty.getEmot(11);
		this.append(str[0], img[0]);
		this.append(str[1], img[1]);
		this.append(str[2], img[2]);
		
		createCommands();
		
		addCommands();
		
		this.setCommandListener(this);
		
//		wyswietlKontakty();
	}
	

	protected void zaladujKontakty() {
//		int n = 0;
//		
//		try {
//			n = MojMidlet1.magazyn.;
//			System.out.println("Rozmiar magazynu: " + n);
//		} catch (RecordStoreNotOpenException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			System.out.println("Magazyn pusty");
//		}
//		
//		if(n > 0) {
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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (RecordStoreException ex) {
			ex.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Magazyn pusty");
		}
//		this.setString(CalyText);	
//		}
}


	protected void wyswietlKontakty() {
		item_1 = new StringItem(null, "Kontakt 1", Item.BUTTON);
		item_1.setLayout(Item.LAYOUT_LEFT);
		item_1.setPreferredSize(this.getWidth()/2 - 1, 15);
		item_2 = new StringItem(null, "Kontakt 2", Item.BUTTON);
		item_2.setLayout(Item.LAYOUT_LEFT);
		item_2.setPreferredSize(this.getWidth()/2 - 1, 15);
		item_3 = new StringItem(null, "EMO1", Item.BUTTON);
		item_3.setLayout(Item.LAYOUT_RIGHT);
		item_3.setPreferredSize(this.getWidth()/3 - 1, 15);
		item_4 = new StringItem(null, "EMO2", Item.BUTTON);
		item_4.setLayout(Item.LAYOUT_RIGHT);
		item_4.setPreferredSize(this.getWidth()/3 - 1, 15);
		
//		item_1.setDefaultCommand(new Command("Set_1", Command.ITEM, 2));
//		item_1.setItemCommandListener(this);
//		item_2.setDefaultCommand(new Command("Set_2", Command.ITEM, 2));
//		item_2.setItemCommandListener(this);
		
//		this.append(item_1);
//		this.append(item_3);
//		this.append(new Spacer(this.getWidth(), 10));
//		this.append(item_2);
//		this.append(item_4);
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
//		usunWszystkoAlert.setTimeout(12500);
//		Command tak = new Command("TAK", Command.OK, 1);
//		Command nie = new Command("NIE", Command.CANCEL, 1);
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
