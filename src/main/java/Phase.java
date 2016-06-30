package main.java;

public class Phase {
	int phase;

	public Phase() {
		phase = 0;
	}

	public void next() {
		if (phase < 2) phase++;
		else 		   phase = 0;
	}

	public void previous() {
		if (phase > 0)
			phase--;
	}

	public void reset() {
		phase = 0;
	}
	
	public int get() {
		return phase;
	}
}
