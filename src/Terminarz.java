import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class Terminarz {
	public RecordStore magazynZdarzen;
	private RecordEnumeration iterator;
	private Vector zdarzenia;

	
	public Terminarz() {

	}
	
	public void otworzMagazyn() {
		try {
			magazynZdarzen = RecordStore.openRecordStore("Zdarzenia", true, RecordStore.AUTHMODE_PRIVATE, false);
		}
		catch (RecordStoreException ex) {
			ex.printStackTrace();
		}
	}
	
	public void zamknijMagazyn() {
		try {
			magazynZdarzen.closeRecordStore();
		}
		catch (RecordStoreException ex) {
			ex.printStackTrace();
		}
	}
	
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
//					System.out.print("Dodano do wektora: ");
//					zdarzenie.wyswietl();
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
//	
//	public String getNazwaKontaktu(int i) {
//		return ((Kontakt)zdarzenia.elementAt(i)).getNazwa();
//	}
//	
//	public Image getEmotikonaKontaktu(int i) {
//		return emoty.getEmot(Integer.parseInt(((Kontakt)kontakty.elementAt(i)).getEmotikona()));
//	}
//	
	public String getData(int i) {
		return ((Zdarzenie)zdarzenia.elementAt(i)).getDzien() + "." + ((Zdarzenie)zdarzenia.elementAt(i)).getMiesiac() + "." + ((Zdarzenie)zdarzenia.elementAt(i)).getRok();
	}
	
	public Zdarzenie getSelectedZdarzenia(int i) {
		return (Zdarzenie)zdarzenia.elementAt(i);
	}
}
