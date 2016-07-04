/*
*Measures time spent in a certain phase. 
*When the phase is ended, writes the time to a textfile.
*/
package tracker;

public interface ChartTrackerInterface {
    
    //call when changing to next phase
    public void nextPhase();
    
    //call when changing BACK from GREEN to RED
    public void greenBack();
    
    //following methods are only used by the implementing class and should not 
    //be called outside of it
    public void redToGreen();
    
    public void greenToRefactor();
    
    public void refactorToRed();
    
    public void writeToFile();
}
