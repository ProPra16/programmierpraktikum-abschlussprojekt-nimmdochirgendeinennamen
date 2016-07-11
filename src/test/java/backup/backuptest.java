package test.java.backup;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.backup.Backup;


public class backuptest {
	@Test
	public void leererString() {
		Backup n = new Backup();
		String erwartet = "";
		String berechnet = n.getLastBackup();
		assertEquals(erwartet, berechnet);
	}

	@Test
	public void standardTest() {
		Backup n = new Backup();
		n.setNewBackup("Hallo");
		String erwartet = "Hallo";
		String berechnet = n.getLastBackup();
		assertEquals(erwartet, berechnet);
	}

	@Test
	public void mehrfacherMethodenAufruf() {
		Backup n = new Backup();
		n.setNewBackup("Hallo");
		n.setNewBackup("Welt");
		n.getLastBackup();
		String erwartet = "Welt";
		String berechnet = n.getLastBackup();
		assertEquals(erwartet, berechnet);

	}

}
