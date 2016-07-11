//Dient zur Zwischenspeicherung des Codes für
// ->Babysteps: Zurücksetzen nachdem Zeit abgelaufen ist
//? ->Tracker: Bekommt Eingabe für Auswertung (?)
//? ->Beim Zurückgehen von Code schreiben zu Test schreiben

//Kann man das nicht auch als StringProperty machen?
//Wäre keine extra Klasse nötig

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
