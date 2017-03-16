import java.util.Vector;
import javax.microedition.lcdui.Image;

public class Emotikony {
	
	Vector emoty;
	
	public Emotikony() {
		emoty = new Vector();
		
		ladujEmoty();
	}

	private void ladujEmoty() {
		laduj("/img/qubodup-small-smile.png");
		laduj("/img/qubodup-small-wink.png");
		laduj("/img/qubodup-small-cool.png");
		laduj("/img/qubodup-small-angry.png");
		laduj("/img/qubodup-small-confused.png");
		laduj("/img/qubodup-small-crying.png");
		laduj("/img/qubodup-small-glad.png");
		laduj("/img/qubodup-small-grin.png");
		laduj("/img/qubodup-small-O_o.png");
		laduj("/img/qubodup-small-razz.png");
		laduj("/img/qubodup-small-sad.png");
		laduj("/img/qubodup-small-shame.png");
		laduj("/img/qubodup-small-surprised.png");
		laduj("/img/qubodup-small-uhm.png");
		laduj("/img/qubodup-small-unplzd.png");
	}

	private void laduj(String png) {
		Image img = null;
		try {
			img = Image.createImage(png);
		} catch (java.io.IOException e) {
			e.printStackTrace();
			img = null;
		}
		emoty.addElement(img);
	}

}
