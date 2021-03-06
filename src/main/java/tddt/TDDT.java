/*
 * Copyright (c) 2016. Caro Jachmann, Jule Pohlmann, Kai Brandt, Kai Holzinger
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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main class of the program.
 */
public class TDDT extends Application{

	/**
	 * Creates the main stage and scene.
	 * @param primaryStage The main stage.
     */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML_layouts/TDDTLayout.fxml"));
			Scene scene = new Scene(root, 800, 550);

			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("/pictures/icon.png"));
			primaryStage.setTitle("TDDT Client - Main");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args startup parameters (Not used in this program)...
     */
	public static void main(String[] args) {
		launch(args);
	}
}