//Dient zur Zwischenspeicherung des Codes fuer
// ->Babysteps: Zuruecksetzen nachdem Zeit abgelaufen ist
//? ->Tracker: Bekommt Eingabe fuer Auswertung (?)
//? ->Beim Zurueckgehen von Code schreiben zu Test schreiben

//Kann man das nicht auch als StringProperty machen?
//Waere keine extra Klasse noetig

package tddt.backup;

import tddt.Controller;

/**
 * This class is used to save the version of the code when the babysteps timer is active. After the time has run out,
 * the code in the text area will be reset to the backup code.
 * Class usage:
 * Backup creation:
 * @see Controller#newTask() Create backup once an exercise is loaded.
 * @see Controller#nextPhase() Create a new backup once the phase is switched
 * Reset to backup:
 * @see Controller#turnBabystepsOn() Reset to backup if babysteps is turned on and the time has run out
 * @see Controller#prevPhase() Reset to backup if you go to previous phase
 */
public class Backup {

	private String inhalt;

    /**
     * The constructor creates a backup object which contains the backup String.
     */
	public Backup() {
		inhalt = "";
	}

    /**
     * The getLastBackup class is used to get the String contained in the current Backup object
     * @return The backup String
     */
	public String getLastBackup() {
		return this.inhalt;
	}

    /**
     * The setNewBackup method is used to store a new String in the Backup object
     * @param a The String that's going to be stored.
     */
	public void setNewBackup(String a) {
		this.inhalt = a;
	}
}
