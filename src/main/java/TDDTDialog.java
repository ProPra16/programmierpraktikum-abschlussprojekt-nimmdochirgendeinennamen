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
	
	private void showTestFails(String info) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Test failed!");
		alert.setHeaderText(null);

		TextArea textArea = new TextArea(info);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane pane = new GridPane();
		pane.setMaxWidth(Double.MAX_VALUE);
		pane.add(textArea, 0, 1);

		alert.getDialogPane().setContent(pane);
		alert.showAndWait();
	}

	private void showCompileError(String info) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Compile Error!");
		alert.setHeaderText(null);

		TextArea textArea = new TextArea(info);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane pane = new GridPane();
		pane.setMaxWidth(Double.MAX_VALUE);
		pane.add(textArea, 0, 1);

		alert.getDialogPane().setContent(textArea);
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
}
