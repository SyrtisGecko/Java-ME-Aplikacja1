import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

public class EkranPokazSzczegolyKontaktu extends Form implements CommandListener {
	
	private Display wyswietlacz;
	private Displayable ekranP;
	private Command powrot;
	private StringItem nazwa_label, get_nazwa, nrTelefonu_label, get_nrTelefonu, nrAlternatywny_label, get_nrAlternatywny,
						email_label, get_email, opis_label, get_opis, emotikona_label;
	private ImageItem get_emotikona;
	
	Emotikony emotikony;
	Kontakt kontakt;
	
	public EkranPokazSzczegolyKontaktu(Kontakt kontakt, Displayable ekranPowrotny) {
		super("Szczegoly Kontaktu");
		wyswietlacz = MojMidlet1.mojDisplay();
		ekranP = ekranPowrotny;
		emotikony = new Emotikony();
		this.kontakt = kontakt;

		createCommands();
		
		addCommands();

		this.setCommandListener(this);
		
		defineFormItems();
		
		appendItems();
	}
	
	private void createCommands() {
		powrot = new Command("Powrot", Command.BACK, 1);
	}
	
	private void addCommands() {
		this.addCommand(powrot);
	}
	
	private void defineFormItems() {
		nazwa_label = new StringItem(null, "Nazwa:");
		get_nazwa = new StringItem(null, kontakt.getNazwa());
		nrTelefonu_label = new StringItem(null, "Numer Telefonu:");
		get_nrTelefonu = new StringItem(null, kontakt.getNrTelefonu());
		nrAlternatywny_label = new StringItem(null, "Numer Alternatywny:");
		get_nrAlternatywny = new StringItem(null, kontakt.getNrAlternatywny());
		email_label = new StringItem(null, "E-mail:");
		get_email = new StringItem(null, kontakt.getEmail());
		opis_label = new StringItem(null, "Opis:");
		get_opis = new StringItem(null, kontakt.getOpis());
		emotikona_label = new StringItem(null, "Emotikona:");
		get_emotikona = new ImageItem(null, zaladujEmote(), ImageItem.LAYOUT_CENTER, null);
	}
	
	private void appendItems() {
		this.append(nazwa_label);
		nazwa_label.setLayout(Item.LAYOUT_LEFT);
		this.append(get_nazwa);
		get_nazwa.setLayout(Item.LAYOUT_CENTER);
		this.append(nrTelefonu_label);
		nrTelefonu_label.setLayout(Item.LAYOUT_LEFT);
		this.append(get_nrTelefonu);
		get_nrTelefonu.setLayout(Item.LAYOUT_CENTER);
		this.append(nrAlternatywny_label);
		nrAlternatywny_label.setLayout(Item.LAYOUT_LEFT);
		this.append(get_nrAlternatywny);
		get_nrAlternatywny.setLayout(Item.LAYOUT_CENTER);
		this.append(email_label);
		email_label.setLayout(Item.LAYOUT_LEFT);
		this.append(get_email);
		get_email.setLayout(Item.LAYOUT_CENTER);
		this.append(opis_label);
		opis_label.setLayout(Item.LAYOUT_LEFT);
		this.append(get_opis);
		get_opis.setLayout(Item.LAYOUT_CENTER);
		this.append(emotikona_label);
		emotikona_label.setLayout(Item.LAYOUT_LEFT);
		this.append(get_emotikona);
	}

	private Image zaladujEmote() {
		return emotikony.getEmot(Integer.parseInt(kontakt.getEmotikona()));
	}

	public void commandAction(Command komenda, Displayable elemEkranu) {
		if(komenda == powrot) {		
			wyswietlacz.setCurrent(ekranP);
			
		}
	}

}
