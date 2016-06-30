package main.java;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class TDDTDialog {

	Object value;

	//TODO think about chaning it to a static method wrapper class
	public TDDTDialog(String type, String message) {
		switch (type){
			case "alert": 		 showAlert(message);
								 break;
			case "testFail": 	 showTestFails(message);
								 break;
			case "compileError": showCompileError(message);
			 					 break;
			case "textInput":	 value = showTextInput(message);
			default:			 return;
		}
	}

	public Object getValue() {
		return value;
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Something went wrong");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private String showTextInput(String message) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Please input a value");
		dialog.setHeaderText(null);
		dialog.setContentText(message);

		Optional<String> input = dialog.showAndWait();
		if (input.isPresent()) {
			if (!input.get().isEmpty()) {
				return input.get();
			} else {
				new TDDTDialog("alert", "Missing input");
			}
		}
		return "-1";
	}

	private void showTestFails(String message) {
		spawnTextAreaAlert("Test failed!", message);
	}

	private void showCompileError(String message) {
		spawnTextAreaAlert("Compile Error!", message);
	}

	private void spawnTextAreaAlert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setResizable(true);

		TextArea text = new TextArea(message);
		text.setEditable(false);
		text.setWrapText(true);

		GridPane.setHgrow(text, Priority.ALWAYS);
		GridPane.setVgrow(text, Priority.ALWAYS);

		GridPane pane = new GridPane();
		pane.setMaxWidth(Double.MAX_VALUE);
		pane.add(text, 0, 0);

		alert.getDialogPane().setContent(pane);
		alert.showAndWait();
	}
}
