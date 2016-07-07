package main.java;

/**
 * Phase class for controlling
 *
 */
public class Phase {
	int phase;
	int start;
	int end;
	int step;

	public Phase(int start, int end, int step) {
		phase = 0;
		this.start = start;
		this.end = end;
		this.step = step;
	}

	public void next() {
		if (phase < end) phase = phase + step;
		else 			 phase = start;
	}

	public void previous() {
		if (phase > start)
			phase = phase - step;
	}

	public void reset() {
		phase = start;
	}

	public int get() {
		return phase;
	}
}
