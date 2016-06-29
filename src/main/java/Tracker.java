package main.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

//import org.apache.commons.lang3.StringUtils;

public class Tracker {
	public LocalTime time;
	String oldCode;
	String oldTest;

	public Tracker(String code, String test) {
		time = LocalTime.now();
		oldCode = code;
		oldTest = test;
	}

	public void dump(String code, String test) {
		String output = getDiff(code, test, oldCode, oldTest);
		time = LocalTime.now();
		try (BufferedWriter out = new BufferedWriter(new FileWriter("TrackerData.txt", true))) {
			out.write(time.toString() + "\n" + output);
		} catch (IOException e) {}
		code = oldCode;
		test = oldTest;
	}

	private String getDiff(String code, String test, String oldCode, String oldTest) {
		return "-1";
		/*
		String diffCode = StringUtils.difference(oldCode, code);
		String diffTest = StringUtils.difference(oldTest, test);
		return "Changed Code:\n" + diffCode + "\n\n Changed Test:\n" + diffTest;
		*/
	}
}

/*

package main.java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;

public class Tracker {
	public LocalTime time;
	String oldCode;
	String oldTest;

	public Tracker(String code, String test) {
		time = LocalTime.now();
		oldCode = code;
		oldTest = test;
	}

	public void dump(String code, String test) {
		String output = getDiff(code, test, oldCode, oldTest);
		time = LocalTime.now();
		try (BufferedWriter out = new BufferedWriter(new FileWriter("TrackerData.txt", true))) {
			out.write(time.toString() + "\n" + output);
		} catch (IOException e) {}
		code = oldCode;
		test = oldTest;
	}

	private String getDiff(String code, String test, String oldCode, String oldTest) {
		String diffCode = StringUtils.difference(oldCode, code);
		String diffTest = StringUtils.difference(oldTest, test);
		return "Changed Code:\n" + diffCode + "\n\n Changed Test:\n" + diffTest;
	}
}

*/
