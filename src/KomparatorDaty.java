import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.rms.RecordComparator;
/*****
 * 
 * @author Przemek
 *
 *Klasa KomparatorDaty segreguje zapisane dane wzgledem daty
 *
 */
public class KomparatorDaty implements RecordComparator {

	public int compare(byte[] rec1, byte[] rec2) {

		ByteArrayInputStream str_b_1 = new ByteArrayInputStream(rec1);
		DataInputStream str_wej_1 = new DataInputStream(str_b_1);
		ByteArrayInputStream str_b_2 = new ByteArrayInputStream(rec2);
		DataInputStream str_wej_2 = new DataInputStream(str_b_2);
		
		String arg1 = "";
		String arg2 = "";
		try {
			String dzien_1 = Integer.toString(str_wej_1.readInt());
			dzien_1 = toFullValue(dzien_1);
			String miesiac_1 = Integer.toString(str_wej_1.readInt());
			miesiac_1 = toFullValue(miesiac_1);
			String rok_1 = Integer.toString(str_wej_1.readInt());
			arg1 = rok_1 + miesiac_1 + dzien_1;

			String dzien_2 = Integer.toString(str_wej_2.readInt());
			dzien_2 = toFullValue(dzien_2);
			String miesiac_2 = Integer.toString(str_wej_2.readInt());
			miesiac_2 = toFullValue(miesiac_2);
			String rok_2 = Integer.toString(str_wej_2.readInt());
			arg2 = rok_2 + miesiac_2 + dzien_2;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//relacja odpowiada porownaniu obu rekordow
		int relacja = arg1.compareTo(arg2);
		if(relacja < 0)
			return -1;
		else if(relacja > 0)
			return 1;
		else return 0;
	}

	private String toFullValue(String value) {
		if(value.length() > 1) {
			return value;
		} else {
			return "0" + value;
		}
		
	}

}
