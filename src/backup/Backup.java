//Dient zur Zwischenspeicherung des Codes f�r
// ->Babysteps: Zur�cksetzen nachdem Zeit abgelaufen ist
//? ->Tracker: Bekommt Eingabe f�r Auswertung (?)
//? ->Beim Zur�ckgehen von Code schreiben zu Test schreiben

//Kann man das nicht auch als StringProperty machen?
//W�re keine extra Klasse n�tig

package backup;


public class Backup {

	String inhalt;

	public Backup() {
		inhalt = "";
	}


	public String getLastBackup() {
		return this.inhalt;
	}

	public void setNewBackup(String a) {
		this.inhalt = a;
	}
}
