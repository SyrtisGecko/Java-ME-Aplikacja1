
public class Zdarzenie {
	private int id = 0;
	private String opis;
	private int dzien, miesiac, rok;
	
	public Zdarzenie(int dzien, int miesiac, int rok, String opis, int id) {
		this.dzien = dzien;
		this.miesiac = miesiac;
		this.rok = rok;
		this.opis = opis;
		this.id = id;
	}
	
	public int getDzien() {
		return dzien;
	}
	
	public int getMiesiac() {
		return miesiac;
	}
	
	public int getRok() {
		return rok;
	}
	
	public String getOpis() {
		return opis;
	}
	
	public int getID() {
		return id;
	}
	
	public String getData() {
		return dzien + "." + miesiac + "." + rok;
	}
}
