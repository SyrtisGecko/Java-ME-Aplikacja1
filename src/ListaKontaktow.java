import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class ListaKontaktow {

	// deklaracja magazynu przechowujacego zapisany tekst
	public static RecordStore magazyn;
	
	private RecordEnumeration iterator;
	
	private Vector kontakty;

	Emotikony emoty;
	
	public ListaKontaktow() {

		emoty = new Emotikony();
	}
	
	public void otworzMagazyn() {
		try {
			magazyn = RecordStore.openRecordStore("Wpisy", true, RecordStore.AUTHMODE_PRIVATE, false);
		}
		catch (RecordStoreException ex) {
			ex.printStackTrace();
		}
	}
	
	public void zamknijMagazyn() {
		try {
			magazyn.closeRecordStore();
		}
		catch (RecordStoreException ex) {
			ex.printStackTrace();
		}
	}
	
	protected void zaladujKontakty() {
		kontakty = new Vector();

		try {
			iterator = magazyn.enumerateRecords(null, new KomparatorTekstu(), false);
			
			while(iterator.hasNextElement()) {
//				System.out.println("Next RecordStoreID: " + iterator.nextRecordId());iterator.
//				System.out.println()
				byte[] rekord = iterator.nextRecord();
//				String Tekst = new String(rekord);
//				System.out.println(Tekst);
				ByteArrayInputStream str_b = new ByteArrayInputStream(rekord);
				DataInputStream str_wej = new DataInputStream(str_b);
				
				try {
					Kontakt kontakt = new Kontakt(str_wej.readUTF(), str_wej.readUTF(), str_wej.readUTF(), str_wej.readUTF(), 
													str_wej.readUTF(), str_wej.readUTF(), str_wej.readInt());
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
	
	protected void wyczyscMagazyn() {
		System.out.println("wyczyscMagazyn()");
		try {
			System.out.println("ID " + magazyn.getNextRecordID());
			magazyn.closeRecordStore();
			RecordStore.deleteRecordStore("Wpisy");
			magazyn = RecordStore.openRecordStore("Wpisy", true, RecordStore.AUTHMODE_PRIVATE, false);
			System.out.println("ID " + magazyn.getNextRecordID());
			
		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		zaladujKontakty();
	}
	
	public void usunKontakt(int i) {
		System.out.println("Kontakt do usuniecia: " + i + " - " + ((Kontakt)kontakty.elementAt(i)).getNazwa());
		
		try {
			magazyn.deleteRecord(((Kontakt)kontakty.elementAt(i)).getID());
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		kontakty.removeElementAt(i);
		
//		RecordEnumeration iter;
//		try {
//			iter = magazyn.enumerateRecords(null, new KomparatorTekstu(), false);
//			
//			for(int k = 0; k < i; k++) {
//				byte[] rekord = iter.nextRecord();
//			}
//						
//				byte[] rekord = iter.nextRecord();
//				ByteArrayInputStream str_b = new ByteArrayInputStream(rekord);
//				DataInputStream str_wej = new DataInputStream(str_b);
//				
//				try {
//					System.out.println("Usuwany kontakt: " + str_wej.readUTF());
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//				
//			
//		} catch (RecordStoreException ex) {
//			ex.printStackTrace();
//		} catch (NullPointerException e) {
//			System.out.println("Magazyn pusty");
//		}
	}
	
	public int getSize() {
		return kontakty.size();
	}
	
	public String getNazwaKontaktu(int i) {
		return ((Kontakt)kontakty.elementAt(i)).getNazwa();
	}
	
	public Image getEmotikonaKontaktu(int i) {
		return emoty.getEmot(Integer.parseInt(((Kontakt)kontakty.elementAt(i)).getEmotikona()));
	}
	
	public Kontakt getSelectedKontakt(int i) {
		return (Kontakt)kontakty.elementAt(i);
	}
}
