//Dient zur Zwischenspeicherung des Codes für
// ->Babysteps: Zurücksetzen nachdem Zeit abgelaufen ist
//? ->Tracker: Bekommt Eingabe für Auswertung (?)
//? ->Beim Zurückgehen von Code schreiben zu Test schreiben

package main.java;

import javafx.scene.control.TextArea;

public class Backup {

	String inhalt;


	public String getLastBackup() {
		return this.inhalt;
	}

	public void setNewBackup(TextArea a) {
		this.inhalt = a.getText();
	}
}
