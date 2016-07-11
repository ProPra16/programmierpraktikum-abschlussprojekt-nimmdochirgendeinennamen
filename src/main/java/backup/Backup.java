//Dient zur Zwischenspeicherung des Codes fuer
// ->Babysteps: Zuruecksetzen nachdem Zeit abgelaufen ist
//? ->Tracker: Bekommt Eingabe fuer Auswertung (?)
//? ->Beim Zurueckgehen von Code schreiben zu Test schreiben

//Kann man das nicht auch als StringProperty machen?
//Waere keine extra Klasse noetig

package main.java.backup;

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
