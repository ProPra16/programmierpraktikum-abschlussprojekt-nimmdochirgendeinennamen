//Dient zur Zwischenspeicherung des Codes f�r
// ->Babysteps: Zur�cksetzen nachdem Zeit abgelaufen ist
//? ->Tracker: Bekommt Eingabe f�r Auswertung (?)
//? ->Beim Zur�ckgehen von Code schreiben zu Test schreiben

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
