package main.java.catalogEditor;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CatalogEditor implements Initializable {
    public TextField classnamefield;
    public TextArea classTextArea;
    public TextField testnamefield;
    public TextArea testTextArea;
    public TextField exnamefield;
    public TextField babystepsfield;
    public TextField babystepstimefield;
    public TextField timetrackingfield;
    public TextArea descfield;
    public Button nextButton1;
    public Button cancelbtn;
    public Button submitbtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(nextButton1 != null) {
            nextButton1.disableProperty().bind(Bindings.isEmpty(exnamefield.textProperty())
                    .or(Bindings.isEmpty(babystepstimefield.textProperty())
                            .or(Bindings.isEmpty(timetrackingfield.textProperty())
                                    .or(Bindings.isEmpty(descfield.textProperty()))
                                    .or(((Bindings.equalIgnoreCase("true",babystepsfield.textProperty())).not()).and((Bindings.equalIgnoreCase("false",babystepsfield.textProperty())).not())
                                            .or(((Bindings.equalIgnoreCase("true",timetrackingfield.textProperty())).not()).and((Bindings.equalIgnoreCase("false",timetrackingfield.textProperty())).not()))))));
        }
        if(submitbtn != null){
            submitbtn.disableProperty().bind((Bindings.isEmpty(classnamefield.textProperty())
                    .or(Bindings.isEmpty(classTextArea.textProperty())
                            .or(Bindings.isEmpty(testnamefield.textProperty())
                                    .or(Bindings.isEmpty(testTextArea.textProperty()))))));
        }
    }

    public void showStage(Stage stage) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource("/main/java/catalogEditor/EditorLayout1.fxml"));
        Scene toExerciseChooser = new Scene(newScene);
        Stage catalog_stage = new Stage();
        catalog_stage.initModality(Modality.WINDOW_MODAL);
        catalog_stage.initOwner(stage);
        catalog_stage.setScene(toExerciseChooser);
        catalog_stage.setTitle("TDDT Client - Exercise Chooser");
        catalog_stage.getIcons().add(new Image("file:pictures/icon.png"));
        catalog_stage.setResizable(false);
        catalog_stage.showAndWait();
    }


    public void onNextButton1Pressed() throws IOException {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        Parent newScene = FXMLLoader.load(getClass().getResource("/main/java/catalogEditor/EditorLayout2.fxml"));
        Scene scene2 = new Scene(newScene);
        stage.setScene(scene2);
    }

    public void onCancelButtonPressed(ActionEvent actionEvent) {
        ((Stage)cancelbtn.getScene().getWindow()).close();
    }

    public void onSubmitButtonPressed(ActionEvent actionEvent) {

    }
}
