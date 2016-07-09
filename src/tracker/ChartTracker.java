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
package tracker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ChartTracker {
    /*
     * Measures time spent in a certain phase.
     * When the phase is ended, writes the time (in seconds) to a text file.
     *
     * Call constructor to start, nextPhase() to change to the next phase,
     * greenBack() to go back from GREEN to RED.
     * Time will be measured and written automatically.
     */

    //seconds spent in each phase so far
    int greenSeconds;
    int redSeconds;
    int refactorSeconds;
    
    //system time when phase is entered
    private long greenStartTime;
    private long redStartTime;
    private long refactorStartTime;
               
    public ChartTracker() {
        //initialise times to 0
        this.greenSeconds = 0;
        this.redSeconds = 0;
        this.refactorSeconds = 0;
        
        //start in RED phase        
        this.redStartTime = System.currentTimeMillis();
    }
    
    //change to next phase, call with current phase (0 = RED, 1 = GREEN, 2 = REFACTOR)
    public void nextPhase(int phase) {
        if (phase == 0) redToGreen();
        if (phase == 1) greenToRefactor();
        if (phase == 2) refactorToRed();
        
        writeToFile();
    }
    
    //change BACK from GREEN to RED
    public void greenBack(int phase) {
	//will only work in phase GREEN
	if (phase == 1) {
            long greenEndTime = System.currentTimeMillis() - greenStartTime;
            greenSeconds += (int)greenEndTime/1000;
            redStartTime = System.currentTimeMillis();

            writeToFile();
    }
    }

                                        /*INTERNAL METHODS*/
    private void redToGreen() {
        long redEndTime = System.currentTimeMillis() - redStartTime;
        redSeconds += (int)redEndTime/1000;
        greenStartTime = System.currentTimeMillis();
    }
    
    private void greenToRefactor() {
        long greenEndTime = System.currentTimeMillis() - greenStartTime;
        greenSeconds += (int)greenEndTime/1000;
        refactorStartTime = System.currentTimeMillis();
    }
    
    private void refactorToRed() {
        long refactorEndTime = System.currentTimeMillis() - refactorStartTime;
        refactorSeconds += (int)refactorEndTime/1000;
        redStartTime = System.currentTimeMillis();
    }

    //write to file
    private void writeToFile() {
        try (PrintWriter printFile = new PrintWriter("TrackingData.txt")) {
            printFile.println("red\n" + redSeconds);
	    printFile.println("green\n" + greenSeconds);
            printFile.println("refactor\n" + refactorSeconds);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}