/*
 * Copyright (c) 2016. Caro Jachmann, Jule Pohlmann, Kai Brandt, Kai Holzinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package tddt;

/**
 * This class handles the phase state of the program
 * @author unknown
 * @version unknown
 */
public class Phase {
	private int phase;
	private int start;
	private int end;
	private int step;

    /**
     * @param start The start phase
     * @param end The end phase
     * @param step The step that's done when changing phase
     */
	public Phase(int start, int end, int step) {
		phase = 0;
		this.start = start;
		this.end = end;
		this.step = step;
	}

    /**
     * Go to the next phase.
     */
	public void next() {
		if (phase < end) phase = phase + step;
		else 			 phase = start;
	}

    /**
     * Go to the previous phase.
     */
	public void previous() {
		if (phase > start)
			phase = phase - step;
	}

    /**
     * Go back to the start phase
     */
	public void reset() {
		phase = start;
	}

    /**
     * @return The current phase.
     */
	public int get() {
		return phase;
	}
}
