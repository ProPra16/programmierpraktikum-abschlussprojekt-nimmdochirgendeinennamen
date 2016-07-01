package main.java;

import java.io.File;

public class TDDTTask {

	private String codeCode;
	private String testCode;

	public TDDTTask(File file) {
		TDDTFileReader reader = new TDDTFileReader();
		String content = reader.readAll(file);
		String[] splitContent = content.toString().split("<Code-Test-Border, do not remove>\n");

		//check if selected file is a Task
		if (splitContent.length != 2) {
			codeCode = null;
			testCode = null;
			return;
		}

		this.codeCode = splitContent[0];
		this.testCode = splitContent[1];
	}

	public String getCode() {
		return this.codeCode;
	}

	public String getTest() {
		return this.testCode;
	}

}