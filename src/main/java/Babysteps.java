package main.java;

public class Babysteps {
	long startTime;
	public long duration;
	boolean enabled;

	public Babysteps() {
		this.enabled = false;
		this.duration = 180;
	}

	public void startPhase() {
		startTime = System.currentTimeMillis();
	}

	public boolean timeLeft() {
		long currentTime = System.currentTimeMillis();
		long delta = currentTime - startTime;
		int time = (int)delta / 1000;

		if (time < duration) return true;
		else return false;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
