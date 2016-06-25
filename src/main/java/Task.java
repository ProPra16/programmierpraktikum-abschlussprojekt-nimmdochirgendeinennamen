package main.java;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Task {

	private String codeCode;
	private String testCode;

	public Task(Stage stage) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose a catalog-folder");
		File file = fc.showOpenDialog(stage);
		if (file == null) {
			new Dialogs("alert", "File could not get read properly");
			return;
		}

		String content = readFile(file);
		String[] splitContent = content.toString().split("<Code-Test-Border, do not remove>");

		//check if selected file is a Task
		if (splitContent.length != 2) {
			new Dialogs("alert", "The chosen file is not a Task");
			return;
		}

		this.codeCode = splitContent[0];
		this.testCode = splitContent[1];
	}

	private String readFile(File file) {
		StringBuilder content = new StringBuilder();
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			for (String line : lines)
				content.append(line + "\n");

		} catch (Exception e) {
            e.printStackTrace();
		}
		return content.toString();
	}

	public String getCode() {
		return this.codeCode;
	}

	public String getTest() {
		return this.testCode;
	}

}
