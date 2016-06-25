package main.java;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

public class Dialogs {

	Object value;

	public Dialogs(String type, String message) {
		switch (type){
			case "alert": 		 showAlert(message);
								 break;
			case "exception": 	 showException(message);
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
	
	private void showException(String message) {
		/*
        TODO [SomeDialog].showException(new FileNotFoundException("Blub"));
		*/
		return;
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
				new Dialogs("alert", "Missing input");
			}
		}
		return "-1";
	}
}
