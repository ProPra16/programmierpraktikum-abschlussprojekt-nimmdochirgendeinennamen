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
package main.java.tracker;

public interface ChartTrackerInterface {
    /*
     * Measures time spent in a certain phase. 
     * When the phase is ended, writes the time to a textfile.
     */

    //call when changing to next phase
    public void nextPhase();

    //call when changing BACK
    public void greenBack();

    /*
    internal methods, not needed in Interface but used in implementing class:

    public void redToGreen();

    public void greenToRefactor();

    public void refactorToRed();

    public void writeToFile();
    */
}
