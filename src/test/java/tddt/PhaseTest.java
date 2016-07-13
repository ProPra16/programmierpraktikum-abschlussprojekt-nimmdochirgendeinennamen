package tddt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PhaseTest {
	@Test
	public void initialize() {
		Phase phase = new Phase(0, 2, 1);
		assertEquals(0, phase.get());
	}

	@Test
	public void simpleNext_Step1() {
		Phase phase = new Phase(0, 2, 1);
		phase.next();
		assertEquals(1, phase.get());
	}

	@Test
	public void simpleNext_Step3() {
		Phase phase = new Phase(0, 2, 3);
		phase.next();
		assertEquals(3, phase.get());
	}

	@Test
	public void simpleNextNext() {
		Phase phase = new Phase(0, 2, 1);
		phase.next();
		phase.next();
		assertEquals(2, phase.get());
	}

	@Test
	public void nextOverIntervalBounds() {
		Phase phase = new Phase(0, 2, 1);
		phase.next();
		phase.next();
		phase.next();
		assertEquals(0, phase.get());
	}

	@Test
	public void reset() {
		Phase phase = new Phase(0, 2, 1);
		phase.next();
		phase.reset();
		assertEquals(0, phase.get());
	}
}
