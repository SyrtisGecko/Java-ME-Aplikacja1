
public class Kontakt {
	
	private String nazwa = "";
	private String nrTelefonu = "";
	private String nrAlternatywny = "";
	private String email = "";
	private String opis = "";
	private String emotikona = "0";
	private int id = 0;
	
	
	public Kontakt(String nazwa, String nrTelefonu, String nrAlternatywny, String email, String opis, String emotikona, int id) {
		this.setNazwa(nazwa);
		this.setNrTelefonu(nrTelefonu);
		this.setNrAlternatywny(nrAlternatywny);
		this.setEmail(email);
		this.setOpis(opis);
		this.setEmotikona(emotikona);
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getNrTelefonu() {
		return nrTelefonu;
	}

	public void setNrTelefonu(String nrTelefonu) {
		this.nrTelefonu = nrTelefonu;
	}

	public String getNrAlternatywny() {
		return nrAlternatywny;
	}

	public void setNrAlternatywny(String nrAlternatywny) {
		this.nrAlternatywny = nrAlternatywny;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public String getEmotikona() {
		return emotikona;
	}

	public void setEmotikona(String emotikona) {
		this.emotikona = emotikona;
	}
	
	public int getID() {
		return id;
	}

	
	//------------
	public void wyswietl() {
		System.out.println(getID() + " " + getNazwa() + " " + getNrTelefonu() + " Emot: " + getEmotikona());
	}

}
