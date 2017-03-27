import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/******
 * 
 * @author Przemek
 *
 * Klasa obslugujaca magazyn do przechowywania Kontaktow
 *
 */
public class ListaKontaktow {

	// deklaracja magazynu przechowujacego zapisany tekst
	public RecordStore magazyn;
	private RecordEnumeration iterator;
	private Vector kontakty;

	Emotikony emoty;
	
	public ListaKontaktow() {

		emoty = new Emotikony();
	}
	
	// metoda otwierajaca magazyn
	public void otworzMagazyn() {
		try {
			magazyn = RecordStore.openRecordStore("Wpisy", true, RecordStore.AUTHMODE_PRIVATE, false);
		}
		catch (RecordStoreException ex) {
			ex.printStackTrace();
		}
	}
	
	// metoda zamykajaca magazyn
	public void zamknijMagazyn() {
		try {
			magazyn.closeRecordStore();
		}
		catch (RecordStoreException ex) {
			ex.printStackTrace();
		}
	}
	
	// laduje Kontakty z pamieci urzadzenia do wektora
	protected void zaladujKontakty() {
		kontakty = new Vector();

		try {
			iterator = magazyn.enumerateRecords(null, new KomparatorTekstu(), false);
			
			while(iterator.hasNextElement()) {
				byte[] rekord = iterator.nextRecord();
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
	
	// usuwa wszystke rekordy z magazynu
	protected void wyczyscMagazyn() {
		try {
			System.out.println("ID " + magazyn.getNextRecordID());
			zamknijMagazyn();
			RecordStore.deleteRecordStore("Wpisy");
			otworzMagazyn();
			System.out.println("ID " + magazyn.getNextRecordID());
			
		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		zaladujKontakty();
	}
	
	// usuwa wybrany rekord z magazynu
	public void usunKontakt(int i) {
		System.out.println("Kontakt do usuniecia: " + i + " - " + ((Kontakt)kontakty.elementAt(i)).getNazwa());
		
		try {
			magazyn.deleteRecord(((Kontakt)kontakty.elementAt(i)).getID());
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		
		kontakty.removeElementAt(i);
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
