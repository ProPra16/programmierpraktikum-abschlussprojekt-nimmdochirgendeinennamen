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

package main.java.exerciseChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.tddt.TDDTDialog;
import main.java.xmlHandler.InvalidFileException;
import main.java.xmlHandler.XMLLoader;

public class ExerciseChooser implements Initializable{

    @FXML private ListView exerciseList;
    @FXML private TextArea exerciseDesc;
    @FXML private Button BtnChoose;
    @FXML private Button BtnCancel;
    @FXML private TextArea babystepsDefault;
    @FXML private TextArea babystepsTimeDefault;
    @FXML private TextArea timetracking;
    private static XMLLoader loader;
    private static String[] values = new String[2];

    /*
    * Open a FileChooser dialog if user clicks on open in the file menu.
    * If user chooses a file which is not a catalog file an exception prompt pops up.
    * If catalog is successfully loaded the ListView will show all contained exercises.
    * */
    @FXML
    public void onOpenClicked() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Choose a compatible XML catalog!");
            File file = fc.showOpenDialog(exerciseList.getScene().getWindow());

            if (file != null) {
                values[0] = (String.valueOf(file));
                loader = new XMLLoader(file);

                ObservableList<String> items = FXCollections.observableArrayList();
                for (int i = 0; i < loader.getNumberOfExercises(); i++) {
                    items.add(loader.getExerciseName(i));
                }
                exerciseList.setItems(items);
            }
        }catch (InvalidFileException e){
            TDDTDialog.showException(e);
        }
    }

    //If user clicks on an exercise in the ListView some information about the exercise is shown on the right
    @FXML
    public void onExerciseClicked() {
        if(loader != null) {
            for (int i = 0; i < loader.getNumberOfExercises(); i++) {
                if (exerciseList.getSelectionModel().isSelected(i)) {
                    BtnChoose.setDisable(false);
                    exerciseDesc.setText(loader.getDescription(i));
                    babystepsDefault.setText(Boolean.toString(loader.isBabystepsActive(i)));
                    babystepsTimeDefault.setText(Integer.toString(loader.getBabyStepsTime(i)));
                    timetracking.setText(Boolean.toString(loader.isTimetrackerActive(i)));
                    values[1] = (String.valueOf(i));
                    return;
                }
            }
        }
    }

    //Close catalog chooser if user clicks on the 'Choose' button.
    @FXML
    public void onBtnChooseClicked() throws IOException {
        Stage app_stage = (Stage) (BtnChoose.getScene().getWindow());
        app_stage.close();
    }

    //set return values to null and close the stage if user clicks on cancel button
    @FXML
    public void onBtnCloseClicked() throws IOException {
        Stage catalog_stage = (Stage) (BtnCancel.getScene().getWindow());
        values[0] = null;
        values[1] = null;
        catalog_stage.close();
    }

    //create and show catalog chooser stage. Returns catalog path and exercise number to the main app.
    public String[] showStage(Stage stage) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource("/main/java/exerciseChooser/ExerciseChooserLayout.fxml"));
        Scene toExerciseChooser = new Scene(newScene);
        Stage catalog_stage = new Stage();
        catalog_stage.initModality(Modality.WINDOW_MODAL);
        catalog_stage.initOwner(stage);
        catalog_stage.setScene(toExerciseChooser);
        catalog_stage.setTitle("TDDT Client - Exercise Chooser");
        catalog_stage.getIcons().add(new Image("file:pictures/icon.png"));
        catalog_stage.setResizable(false);
        catalog_stage.showAndWait();
        return values;
    }

    //Cant choose an exercise if no catalog is loaded.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BtnChoose.setDisable(true);
    }
}