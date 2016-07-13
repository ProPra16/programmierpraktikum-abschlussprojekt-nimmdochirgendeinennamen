/*
 * Copyright (c) 2016. Caro Jachmann, Dominik Kuhnen, Jule Pohlmann, Kai Brandt, Kai Holzinger
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
package tddt.tracker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Measures time spent in a certain phase.<br>
 * When the phase is ended, writes the time (in seconds) to a text file.<br><br>
 *
 * Call constructor to start, nextPhase() to change to the next phase,<br>
 * greenBack() to go back from GREEN to RED.<br>
 * Time will be measured and written automatically.
 * @author Jule Pohlmann
 * @version unknown
 */
public class ChartTracker implements ChartTrackerInterface{


    /**
     *  Seconds spent in each phase so far.
     */
    public int greenSeconds;
    public int redSeconds;
    public int refactorSeconds;

    /**
     * system time when phase is entered
     */
    private long greenStartTime;
    private long redStartTime;
    private long refactorStartTime;

    /**
     * The constructor initializes the times to 0 and starts the tracking in phase red.
     */
    public ChartTracker() {
        //initialize times to 0
        this.greenSeconds = 0;
        this.redSeconds = 0;
        this.refactorSeconds = 0;

        //start in RED phase
        this.redStartTime = System.currentTimeMillis();
    }

    /**
     * Change to next phase.
     * @param phase The current phase (0 = RED, 1 = GREEN, 2 = REFACTOR).
     */
    @Override
    public void nextPhase(int phase) {
        if (phase == 0) redToGreen();
        if (phase == 1) greenToRefactor();
        if (phase == 2) refactorToRed();

        writeToFile();
    }

    /**
     * change BACK from GREEN to RED.
     */
    @Override
    public void greenBack() {
		long greenEndTime = System.currentTimeMillis() - greenStartTime;
		greenSeconds += (int)greenEndTime/1000;
		redStartTime = System.currentTimeMillis();

		writeToFile();
    }

    /*INTERNAL METHODS*/

    /**
     * Change from red to green.
     * Used in the {@link #nextPhase(int)} method.
     */
    private void redToGreen() {
        long redEndTime = System.currentTimeMillis() - redStartTime;
        redSeconds += (int)redEndTime/1000;
        greenStartTime = System.currentTimeMillis();
    }

    /**
     * Change from green to refactor.
     * Used in the {@link #nextPhase(int)} method.
     */
    private void greenToRefactor() {
        long greenEndTime = System.currentTimeMillis() - greenStartTime;
        greenSeconds += (int)greenEndTime/1000;
        refactorStartTime = System.currentTimeMillis();
    }

    /**
     * Change from refactor to red.
     * Used in the {@link #nextPhase(int)} method.
     */
    private void refactorToRed() {
        long refactorEndTime = System.currentTimeMillis() - refactorStartTime;
        refactorSeconds += (int)refactorEndTime/1000;
        redStartTime = System.currentTimeMillis();
    }

    /**
     * Write the gathered data to the specified file.
     * Used in the {@link #nextPhase(int)} and the {@link #greenBack()} methods.
     */
    private void writeToFile() {
        try (BufferedWriter printFile = new BufferedWriter(new FileWriter("TrackingData.txt", false))) {
            printFile.write("red\n" + redSeconds);
            printFile.write("\ngreen\n" + greenSeconds);
            printFile.write("\nrefactor\n" + refactorSeconds);
            printFile.close();
        } catch (IOException ignored) {}
    }
}
