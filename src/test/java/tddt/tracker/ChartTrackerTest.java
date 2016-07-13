package tddt.tracker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tddt.tracker.ChartTracker;

public class ChartTrackerTest {

	@Test
	public void test() throws InterruptedException {
		ChartTracker testTracker = new ChartTracker();

		Thread.sleep(1000);
		testTracker.nextPhase(0);
		assertEquals(1, testTracker.redSeconds);

		Thread.sleep(2000);
		testTracker.greenBack();

		assertEquals(2, testTracker.greenSeconds);

		Thread.sleep(3000);
		testTracker.nextPhase(0);

		assertEquals(4, testTracker.redSeconds);

		Thread.sleep(4200);
		testTracker.nextPhase(1);
		assertEquals(6, testTracker.greenSeconds);

		Thread.sleep(1300);
		testTracker.nextPhase(2);
		assertEquals(1, testTracker.refactorSeconds);

		Thread.sleep(2016);
		testTracker.nextPhase(0);
		assertEquals(6, testTracker.redSeconds);

		testTracker.nextPhase(1);
	}
}
