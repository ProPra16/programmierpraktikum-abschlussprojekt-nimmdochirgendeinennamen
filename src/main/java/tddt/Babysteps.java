package main.java.tddt;

/**
 * This class handles the Babysteps plugin.
 * @author Caro Jachmann
 * @version unknown
 */
public class Babysteps {
	private long startTime;
	public long duration;
	private boolean enabled;

    /**
     * A babysteps object is created with the boolean variable 'enabled' set to false and the
     * long variable 'duration' set to 180 (seconds).
     */
	public Babysteps() {
		this.enabled = false;
		this.duration = 180;
	}

    /**
     * This method sets the start time of the Object to the current time.
     */
	void startPhase() {
		this.startTime = System.currentTimeMillis();
	}

    /**
     * This method subtracts the {@link #startTime } from the current time.
     * The amount of milliseconds that emerges is converted to seconds.
     * @return True if the elapsed time is less than the Babysteps cycle duration, otherwise false.
     */
	boolean timeLeft() {
		long currentTime = System.currentTimeMillis();
		long delta = currentTime - startTime;
		int time = (int)delta / 1000;

		return time < duration;
	}

    /**
     * @return True if babysteps is enabled
     */
	public boolean isEnabled() {
		return enabled;
	}

    /**
     * Sets enabled to true.
     */
	public void enable() {
		enabled = true;
	}

    /**
     * Sets enabled to false.
     */
	public void disable() {
		enabled = false;
	}

    /**
     * Sets a new Babysteps duration if the new duration differs from the old duration.
     * @param duration The new babysteps cycle duration.
     */
	public void setDuration(int duration) {
		if(this.duration != duration) this.duration = duration;
	}

}
