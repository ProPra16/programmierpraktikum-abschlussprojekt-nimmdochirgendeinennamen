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

package main.java.tddt;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

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
		((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:pictures/icon.png"));
		alert.setTitle("Something went wrong");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	//doesnt really fit the implementation style of the class but whatever... needed some exception output
	public static void showException(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:pictures/icon.png"));
		alert.setTitle("Exception Dialog");
		alert.setHeaderText("An Exception has occured.");
		alert.setContentText(e.getMessage());

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionText = sw.toString();

		Label label = new Label("Stacktrace:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);

		GridPane gridPane = new GridPane();
		gridPane.add(label, 0, 0);
		gridPane.add(textArea, 0, 1);
		alert.getDialogPane().setContent(gridPane);
		alert.setResizable(false);

		alert.showAndWait();
	}

	private String showTextInput(String message) {
		TextInputDialog dialog = new TextInputDialog();
		((Stage)dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:pictures/icon.png"));
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
		((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:pictures/icon.png"));
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
