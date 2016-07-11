package test.java;

import org.junit.Test;

import main.java.Babysteps;

import static org.junit.Assert.assertEquals;


public class BabystepsTest {
	@Test
	public void start() {
		Babysteps n = new Babysteps();
		boolean erwartet = false;
		boolean berechnet = n.isEnabled();
		assertEquals(erwartet, berechnet);
	}

	@Test
	public void enabling() {
		Babysteps n = new Babysteps();
		boolean erwartet = true;
		n.enable();
		boolean berechnet = n.isEnabled();
		assertEquals(erwartet, berechnet);
	}

	@Test
	public void disabling() {
		Babysteps n = new Babysteps();
		boolean erwartet = false;
		n.enable();
		n.disable();
		boolean berechnet = n.isEnabled();
		assertEquals(erwartet, berechnet);
	}

	@Test
	public void startDuration() {
		Babysteps n = new Babysteps();
		long erwartet = 180;
		long berechnet = n.duration;
		assertEquals(erwartet, berechnet);
	}

	public void setDuration() {
		Babysteps n = new Babysteps();
		long erwartet = 80;
		n.setDuration(80);
		long berechnet = n.duration;
		assertEquals(erwartet, berechnet);
	}

}