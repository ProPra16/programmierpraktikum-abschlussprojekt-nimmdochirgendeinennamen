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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tracker {

    public LocalDateTime time;

    String oldCode;
    String oldTest;

    File file;

    //constructor
    public Tracker(String code, String test) {
        time = LocalDateTime.now();
        oldCode = code;
        oldTest = test;

        file = new File("TrackingAnalyse.txt");
        FileWriter clear;
		try {
			clear = new FileWriter(file, false);
			clear.write("");
	        clear.close();
		} catch (IOException e) {}
    }

    public void callDump(String now, int phase, boolean back) {
    	time = LocalDateTime.now();
    	String output = time.toString() + "\n";
    	if (!back) output += dump(now, phase);

    	//if user went back from GREEN to RED
    	else if (back) { output += "Changed code in GREEN:\nWent back to RED, no changes.\n"; }
    	writeToFile(output);
    }

    public void showOutput() {
    	Text show = new Text(output());

    	ScrollPane window = new ScrollPane();
        window.setContent(show);
        window.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        window.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

    	Scene scene = new Scene(window, 400, 600);
    	Stage stage = new Stage();

    	stage.setTitle("Tracking history");
    	stage.setScene(scene);
    	stage.show();
    }

    /*INTERNAL METHODS*/
    private String output() {
    	String output = "";

    	Scanner scanner = null;

    	try {
		scanner = new Scanner(file);
    	} catch (FileNotFoundException e) {}

	  	while (scanner.hasNextLine()) {
			output += "\n" + scanner.nextLine();
	  	}
	  	scanner.close();
    	return output;
    }

    //now: current code/test
    //phase: 0 = RED, 1 = GREEN, 2 = REFACTOR
    public String dump(String now, int phase) {
	//initialise Strings for output
	String changes = "";
	String phaseChanged = "";

	//set Strings according to phase
	if (phase == 0) {
		changes = getDiff(now, 0);
		phaseChanged = "Changed test in RED:";
		oldTest = now;
	}

	if (phase == 1) {
		changes = getDiff(now, 1);
		phaseChanged = "Changed code in GREEN:";
		oldCode = now;
	}

	if (phase == 2) {
		changes = getDiff(now, 2);
		phaseChanged = "Changed code in REFACTOR:";
		oldCode = now;
	}

	//complete output String
	String output = (phaseChanged + "\n" + changes + "\n");
	return output;
    }

    public void writeToFile(String output) {
	try {
		BufferedWriter out = new BufferedWriter (new FileWriter(file, true));
        	out.write(output);
        	out.close();
        } catch (IOException e) {}
    }

    //get differences
    private String getDiff(String now, int phase) {

	String old;

	if (phase == 0) old = oldTest;
	else old = oldCode;

	String different = "";

	if (now.equals(old)) different += "*No changes made*\n";

	else {
		String[] codeArray = getLines(now);
		String[] oldCodeArray = getLines(old);

		int min = Math.min(codeArray.length, oldCodeArray.length);
		int lineNo;

		for(int i = 0; i < min; i++) {
		    	lineNo = i + 1;
		    	if(!codeArray[i].equals(oldCodeArray[i])) 
				different += ("Changed line " + lineNo + ": " + codeArray[i] + "\n");
		}

		if (codeArray.length > oldCodeArray.length) {
			for (int j = min; j < codeArray.length; j++) {
				lineNo = j + 1;
				different += ("New line " + lineNo + ": " + codeArray[j] + "\n");
			}
		}

		if (oldCodeArray.length > codeArray.length) {
			for (int k = min; k < oldCodeArray.length; k++) {
				lineNo = (k + 1);
				different += ("Removed line " + lineNo + ": " + oldCodeArray[k] + "\n");					
			}
		}
	}
    return different;
    }

    private static String[] getLines(String a) {
        String[] splitted = a.split("\n");
        return splitted;
    }
}	
	
