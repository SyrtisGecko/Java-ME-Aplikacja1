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
		String arg1 = new String(rec1);
		String arg2 = new String(rec2);
		
		//relacja odpowiada porownaniu obu rekordow
		int relacja = arg1.compareTo(arg2);
		if(relacja < 0)
			return -1;
		else if(relacja > 0)
			return 1;
		else return 0;
	}

}
