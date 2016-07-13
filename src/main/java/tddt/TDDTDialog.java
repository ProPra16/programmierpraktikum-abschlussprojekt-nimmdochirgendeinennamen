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

package tddt;

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

/**
 * This class handles dialog spawning  while the program is running.
 * @author Dominik Kuhnen
 * @version unknown
 */
public class TDDTDialog {

	private Object value;

	/**
	 * This method is used to differentiate between the different dialogs and call the show method for each of it.
	 * @param type The dialog type
	 * @param message The dialog message
     */
	//TODO think about changing it to a static method wrapper class
	public TDDTDialog(String type, String message) {
		switch (type){
			case "alert": 		 showAlert(message);
								 break;
			case "testFail": 	 showTestFails(message);
								 break;
			case "compileError": showCompileError(message);
			 					 break;
			case "textInput":	 value = showTextInput(message);
			default:
		}
	}

	/**
	 * ??
	 * @return The value Object
     */
	public Object getValue() {
		return value;
	}

	/**
	 * Shows an alert dialog
	 * @param message The printed message
     */
	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:pictures/icon.png"));
		alert.setTitle("Something went wrong");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * This method cannot be called using {@link #TDDTDialog(String, String)}, because it needs the Exception object
	 * in order to work properly.
	 * @param e The exception thats going to be shown in the dialog.
     */
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

	/**
	 * Shows a simple dialog that waits for the user to enter some text.
	 * @param message The message is shown in the content text of the dialog.
	 * @return The user input
     */
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

	/**
	 * Spawns a dialog that shows info about a failed test.
	 * @param message The info about the failed test
     */
	private void showTestFails(String message) {
		spawnTextAreaAlert("Test failed!", message);
	}

	/**
	 * Spawns a dialog that shows info about a failed compilation.
	 * @param message The errorlog of the failed compilation
     */
	private void showCompileError(String message) {
		spawnTextAreaAlert("Compile Error!", message);
	}

	/**
	 * Spawns an alert dialog with a text area.
	 * @param title The title of the dialog.
	 * @param message The message thats printed inside of the text area.
     */
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
