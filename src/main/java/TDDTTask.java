package main.java;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TDDTTask {

	private String codeCode;
	private String testCode;

	public TDDTTask(Stage stage) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose a catalog-folder");
		File file = fc.showOpenDialog(stage);
		if (file == null) {
			new Dialogs("alert", "File could not get read properly");
			return;
		}

		TDDTFileReader reader = new TDDTFileReader();
		String content = reader.read(file);
		String[] splitContent = content.toString().split("<Code-Test-Border, do not remove>");

		//check if selected file is a Task
		if (splitContent.length != 2) {
			new Dialogs("alert", "The chosen file is not a Task");
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
