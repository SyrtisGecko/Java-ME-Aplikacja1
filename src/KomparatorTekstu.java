import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.rms.RecordComparator;
/*****
 * 
 * @author Przemek
 *
 *Klasa KomparatorTekstu porownuje lancuchy znakow i segreguje je alfabetycznie
 *
 */
public class KomparatorTekstu implements RecordComparator {

	public int compare(byte[] rec1, byte[] rec2) {

		ByteArrayInputStream str_b_1 = new ByteArrayInputStream(rec1);
		DataInputStream str_wej_1 = new DataInputStream(str_b_1);
		ByteArrayInputStream str_b_2 = new ByteArrayInputStream(rec2);
		DataInputStream str_wej_2 = new DataInputStream(str_b_2);
		
		String arg1 = "";
		String arg2 = "";
		try {
			arg1 = str_wej_1.readUTF();
			arg2 = str_wej_2.readUTF();
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

}
