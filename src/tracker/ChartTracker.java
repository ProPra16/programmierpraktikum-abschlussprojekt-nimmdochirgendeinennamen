/*
*Measures time spent in a certain phase. 
*When the phase is ended, writes the time (in seconds) to a textfile.
*
*Call constructor to start, nextPhase() to change to the next phase, 
*greenBack() to go back from GREEN to RED.
*Time will be measured and written automatically.
*/
package tracker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ChartTracker implements ChartTrackerInterface {
    //seconds spent in each phase so far
    int greenSeconds;
    int redSeconds; 
    int refactorSeconds;
    
    //system time when phase is entered
    long greenStartTime;
    long redStartTime;
    long refactorStartTime;
    
    //current phase (0 = RED, 1 = GREEN, 2 = REFACTOR)
    int phase;
           
    public ChartTracker() {
        //initialise times to 0
        this.greenSeconds = 0;
        this.redSeconds = 0;
        this.refactorSeconds = 0;
        
        //start in RED phase
        this.phase = 0;
        
        redStartTime = System.currentTimeMillis();
    }
    
    //change to next phase, automatically updates phase
    //and writes times to textfile
    public void nextPhase() {
        if (phase == 0) redToGreen();
        if (phase == 1) greenToRefactor();
        if (phase == 2) refactorToRed();
        
        writeToFile();
        phase++;
    }
    
    //change BACK from GREEN to RED
    public void greenBack() {
	//will only work if currently in phase GREEN
        if (phase == 1) {
            long greenEndTime = System.currentTimeMillis() - greenStartTime;
            greenSeconds += (int)greenEndTime/1000;
            redStartTime = System.currentTimeMillis();

            writeToFile();
	    phase--;
        }
    }

    //following methods are only used by this class and should not 
    //be called outside of it
     
    //@Override
    public void redToGreen() {
        long redEndTime = System.currentTimeMillis() - redStartTime;
        redSeconds += (int)redEndTime/1000;
        greenStartTime = System.currentTimeMillis();
    }
    
    public void greenToRefactor() {
        long greenEndTime = System.currentTimeMillis() - greenStartTime;
        greenSeconds += (int)greenEndTime/1000;
        refactorStartTime = System.currentTimeMillis();
    }
    
    public void refactorToRed() {
        long refactorEndTime = System.currentTimeMillis() - refactorStartTime;
        refactorSeconds += (int)refactorEndTime/1000;
        redStartTime = System.currentTimeMillis();
    }

    //write to file:
    public void writeToFile() {
        try (PrintWriter printFile = new PrintWriter("TrackingData.txt")) {
            printFile.println("red\n" + redSeconds);
	    printFile.println("green\n" + greenSeconds);
            printFile.println("refactor\n" + refactorSeconds);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
