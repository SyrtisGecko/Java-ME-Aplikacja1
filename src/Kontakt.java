
public class Kontakt {
	
	private String nazwa = "";
	private String nrTelefonu = "";
	private String nrAlternatywny = "";
	private String email = "";
	private String opis = "";
	
	public Kontakt(String nazwa, String nrTelefonu, String nrAlternatywny, String email, String opis) {
		this.setNazwa(nazwa);
		this.setNrTelefonu(nrTelefonu);
		this.setNrAlternatywny(nrAlternatywny);
		this.setEmail(email);
		this.setOpis(opis);
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
	
	//------------
	public void wyswietl() {
		System.out.println(getNazwa() + " " + getNrTelefonu());
	}

}
