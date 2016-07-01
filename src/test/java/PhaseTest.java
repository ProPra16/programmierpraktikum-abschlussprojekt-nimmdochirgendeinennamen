package test.java;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.Phase;

public class PhaseTest {
	@Test
	public void initialize() {
		Phase phase = new Phase();
		assertEquals(0, phase.get());
	}

	@Test
	public void simpleNext() {
		Phase phase = new Phase();
		phase.next();
		assertEquals(1, phase.get());
	}

	@Test
	public void simpleNextNext() {
		Phase phase = new Phase();
		phase.next();
		phase.next();
		assertEquals(2, phase.get());
	}

	@Test
	public void nextWhileOnPhaseTwo() {
		Phase phase = new Phase();
		phase.next();
		phase.next();
		phase.next();
		assertEquals(0, phase.get());
	}

	@Test
	public void reset() {
		Phase phase = new Phase();
		phase.next();
		phase.reset();
		assertEquals(0, phase.get());
	}
}
