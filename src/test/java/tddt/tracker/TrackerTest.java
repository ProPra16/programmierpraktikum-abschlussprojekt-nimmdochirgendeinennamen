package tddt.tracker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tddt.tracker.Tracker;

public class TrackerTest {
	// callDump is not tested since LocalTime is too exact (but was tested
	// manually).

	// Test dump-method.
	// This will also test getDiff and getLines.
	@Test
	public void testDump0() {
		Tracker testTracker0 = new Tracker("This is code\nwith several lines\nlike this one.",
				"This is a test\nwith only two lines.");
		String result = testTracker0.dump("This is a test\nwith only two lines.\n...Or not.", 0);
		String expected = "Changed test in RED:\nNew line 3: ...Or not.\n\n";
		assertEquals(expected, result);
	}

	@Test
	public void testDump1() {
		Tracker testTracker1 = new Tracker("This is code\nwith several lines\nlike this one.",
				"This is a test\nwith only two lines.");
		String result = testTracker1.dump("This is code\nwith one less line.", 1);
		String expected = "Changed code in GREEN:\nChanged line 2: with one less line.\nRemoved line 3: like this one.\n\n";
		assertEquals(expected, result);
	}

	@Test
	public void testDump2() {
		Tracker testTracker2 = new Tracker("This is code\nwith several lines\nlike this one.",
				"This is a test\nwith only two lines.");
		String result = testTracker2.dump("This is code\nwith two changed lines\nincluding this one.", 2);
		String expected = "Changed code in REFACTOR:\nChanged line 2: with two changed lines\nChanged line 3: including this one.\n\n";
		assertEquals(expected, result);
	}

	@Test
	public void testDump3() {
		Tracker testTracker3 = new Tracker("This is code\nwith several lines\nlike this one.",
				"This is a test\nwith only two lines.");
		String result = testTracker3.dump("This is code\nwith several lines\nlike this one.", 2);
		String expected = "Changed code in REFACTOR:\n*No changes made*\n\n";
		assertEquals(expected, result);
	}
}
