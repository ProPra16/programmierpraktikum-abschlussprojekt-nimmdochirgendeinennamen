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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;

public class Tracker {

    public LocalTime time;
    
    String oldCode;
    String oldTest;

    //constructor
    public Tracker(String code, String test) {
        time = LocalTime.now();
        oldCode = code;
        oldTest = test;
    }

    //now: current code/test
    //phase: 0 = RED, 1 = GREEN, 2 = REFACTOR
    public void dump(String now, int phase) {
	//initialise Strings for output
	String changes = "";
	String phaseChanged = "";

	//set Strings according to phase
	if (phase == 0) {
		changes = getDiff(now, 0);
		phaseChanged = "Changed test in RED:\n";
		oldTest = now;
	}

	if (phase == 1) {
		changes = getDiff(now, 1);
		phaseChanged = "Changed code in GREEN:\n";
		oldCode = now;
	}

	if (phase == 2) {
		changes = getDiff(now, 2);
		phaseChanged = "Changed code in REFACTOR:\n";
		oldCode = now;
	}

	//complete output String
        time = LocalTime.now();
	String output = time.toString();
	output += ("\n" + phaseChanged + "\n" + changes + "\n\n");
        
	//write to file
        try {
		BufferedWriter out = new BufferedWriter(new FileWriter("TrackerData.txt", true));
        	out.write(output);
		out.close();
        } catch (IOException e) {}
    }

    //can only go back in GREEN
    public String wentBack() {
	time = LocalTime.now();
	String output = time.toString();
	output += "Changed code in GREEN:\nWent back to RED, no changes.\n\n";
    }

    //get differences
    //not final version
    private String getDiff(String now, int phase) {

	String different;
    	
    	if (phase == 0) {
		if (now.equals(oldTest)) different = "*No changes made*";
		else different = StringUtils.difference(oldTest, now);
	}


	else {
		if (now.equals(oldCode)) different = "*No changes made*";
		else different = StringUtils.difference(oldCode, now);
	}
    return different;   
    }
}	
