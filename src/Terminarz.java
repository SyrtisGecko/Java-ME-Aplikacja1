import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/******
 * 
 * @author Przemek
 *
 * Klasa obslugujaca magazyn do przechowywania Zdarzen
 *
 */
public class Terminarz {
	public RecordStore magazynZdarzen;
	private RecordEnumeration iterator;
	private Vector zdarzenia;

	
	public Terminarz() {

	}
	
	// metoda otwierajaca magazyn
	public void otworzMagazyn() {
		try {
			magazynZdarzen = RecordStore.openRecordStore("Zdarzenia", true, RecordStore.AUTHMODE_PRIVATE, false);
		}
		catch (RecordStoreException ex) {
			ex.printStackTrace();
		}
	}
	
	// metoda zamykajaca magazyn
	public void zamknijMagazyn() {
		try {
			magazynZdarzen.closeRecordStore();
		}
		catch (RecordStoreException ex) {
			ex.printStackTrace();
		}
	}
	
	// laduje Zdarzenia z pamieci urzadzenia do wektora
	protected void zaladujZdarzenia() {
		zdarzenia = new Vector();

		try {
			iterator = magazynZdarzen.enumerateRecords(null, new KomparatorDaty(), false);
			
			while(iterator.hasNextElement()) {
				byte[] rekord = iterator.nextRecord();
				ByteArrayInputStream str_b = new ByteArrayInputStream(rekord);
				DataInputStream str_wej = new DataInputStream(str_b);
				
				try {
					Zdarzenie zdarzenie = new Zdarzenie(str_wej.readInt(), str_wej.readInt(), str_wej.readInt(), str_wej.readUTF(), str_wej.readInt());
					zdarzenia.addElement(zdarzenie);
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
			System.out.println("ID " + magazynZdarzen.getNextRecordID());
			zamknijMagazyn();
			RecordStore.deleteRecordStore("Zdarzenia");
			otworzMagazyn();
			System.out.println("ID " + magazynZdarzen.getNextRecordID());
			
		} catch (RecordStoreNotOpenException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		zaladujZdarzenia();
	}
	
	// usuwa wybrany rekord z magazynu
	public void usunZdarzenie(int i) {
		
		try {
			magazynZdarzen.deleteRecord(((Zdarzenie)zdarzenia.elementAt(i)).getID());
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
		
		zdarzenia.removeElementAt(i);
	}
	
	public int getSize() {
		return zdarzenia.size();
	}
	
	public String getData(int i) {
		return ((Zdarzenie)zdarzenia.elementAt(i)).getDzien() + "." + ((Zdarzenie)zdarzenia.elementAt(i)).getMiesiac() + "." + ((Zdarzenie)zdarzenia.elementAt(i)).getRok();
	}
	
	public Zdarzenie getSelectedZdarzenia(int i) {
		return (Zdarzenie)zdarzenia.elementAt(i);
	}
}
