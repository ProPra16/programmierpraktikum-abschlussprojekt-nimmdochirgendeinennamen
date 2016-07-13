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

/**
 * This class tracks the coding behavior of the user. It saves what the user changes and when.
 * @author Jule Pohlmann
 * @author Caro Jachmann
 * @version unknown
 */
public class Tracker {

	public LocalDateTime time;
	private String oldCode;
	private String oldTest;
	private File file;

	/**
	 * The constructor initializes the variables and erases all former information off the file
	 * the analyse is saved to.
	 * @param code The initial code which is going to be changed by the user.
	 * @param test The initial test code which is going to be changed by the user.
     */
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
		} catch (IOException ignored) {
		}
	}

	/**
	 * <p>If back is true this method adds a String to the tracker output file. Otherwise it calls {@link #dump(String, int)}</p>
	 * @see #dump(String, int)
	 * @param now The current state of the code.
	 * @param phase The current phase the user is in.
	 * @param back This parameter is true if the user went back into a previous phase.
     */
	public void callDump(String now, int phase, boolean back) {
		time = LocalDateTime.now();
		String output = time.toString() + "\n";
		if (!back)
			output += dump(now, phase);

		// if user went back from GREEN to RED
		else {
			output += "Changed code in GREEN:\nWent back to RED, no changes.\n";
		}
		writeToFile(output);
	}

	/**
	 * Prints the content of the tracking log into a new Stage.
	 */
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

	/* INTERNAL METHODS */

	/**
	 * Reads the content of the tracking log file into a String
	 * @return String that contains the tracking log.
     */
	private String output() {
		String output = "";

		Scanner scanner = null;

		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException ignored) {
		}

		while (scanner != null && scanner.hasNextLine()) {
			output += "\n" + scanner.nextLine();
		}
		if (scanner != null) {
			scanner.close();
		}
		return output;
	}

	/**
	 * Saves the difference between the initial and the current code using the {@link #getDiff(String, int)} method
	 * and changes the initial code to the current code. It then returns the saved difference as a String.
	 * @param now The current code / test
	 * @param phase phase: 0 = RED, 1 = GREEN, 2 = REFACTOR
     * @return A String that contains all the changes that've been done to the initial code.
     */
	public String dump(String now, int phase) {
		// initialise Strings for output
		String changes = "";
		String phaseChanged = "";

		// set Strings according to phase
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

		// complete output String
		return (phaseChanged + "\n" + changes + "\n");
	}

	/**
	 * Writes a String to a file.
	 * @param output The String that is to be saved to the file.
     */
	private void writeToFile(String output) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write(output);
			out.close();
		} catch (IOException ignored) {
		}
	}

	/**
	 * Gets the difference between the current code and the initial code.
	 * @param now The current code.
	 * @param phase The current phase the user is in.
     * @return A String that contains all differences between the current and the initial code.
     */
	private String getDiff(String now, int phase) {

		String old;

		if (phase == 0)
			old = oldTest;
		else
			old = oldCode;

		String different = "";

		if (now.equals(old))
			different += "*No changes made*\n";

		else {
			String[] codeArray = getLines(now);
			String[] oldCodeArray = getLines(old);

			int min = Math.min(codeArray.length, oldCodeArray.length);
			int lineNo;

			for (int i = 0; i < min; i++) {
				lineNo = i + 1;
				if (!codeArray[i].equals(oldCodeArray[i]))
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

	/**
	 * Splits a String at every newline character and saves the fragments to a String array.
	 * @param a The String that is going to be split.
	 * @return The String Array that contains the fragments.
     */
	private static String[] getLines(String a) {
		return a.split("\n");
	}
}
