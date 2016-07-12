package main.java.catalogEditor;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class creates a new Window which allows the user to create a new exercise for the catalog.
 * The new exercise can either be stored inside a new catalog file, or it can be appended to an
 * existing catalog.
 * @author Kai Holzinger
 * @version 1.0
 */
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
    public HBox ttwrapper;

    /**
     * Both the next button on the first scene and the submit button on the second scene
     * are bound to the specific text fields.
     *
     * The next button is bound so that it becomes enabled only if all text fields on the
     * first scene are not empty and the babysteps field and timetracking field have to
     * contain either true or false.
     *
     * The submit button is bound so that it becomes enabled only if all text fields on
     * the second scene are not empty.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(nextButton1 != null) {
            nextButton1.disableProperty().bind(Bindings.isEmpty(exnamefield.textProperty()).or(
                                            Bindings.isEmpty(babystepstimefield.textProperty()).or(
                                            Bindings.isEmpty(timetrackingfield.textProperty()).or(
                                            Bindings.isEmpty(descfield.textProperty())).or(
                                            (Bindings.equalIgnoreCase("true",babystepsfield.textProperty()).not()).and(Bindings.equalIgnoreCase("false",babystepsfield.textProperty()).not()).or(
                                            (Bindings.equalIgnoreCase("true",timetrackingfield.textProperty()).not()).and(Bindings.equalIgnoreCase("false",timetrackingfield.textProperty()).not()))))));
        }
        if(submitbtn != null){
            submitbtn.disableProperty().bind(Bindings.isEmpty(classnamefield.textProperty()).or(
                                            Bindings.isEmpty(classTextArea.textProperty()).or(
                                            Bindings.isEmpty(testnamefield.textProperty()).or(
                                            Bindings.isEmpty(testTextArea.textProperty())))));
        }
    }

    /**
     * This method is used to initially create the new stage and show the first scene.
     * The modality is set to WINDOW_MODAL so that the main window is inaccessible while
     * the catalog editor window is open.
     * @param stage The main stage, it's set to the owner window of the catalog editor window.
     * @throws IOException If the FXML file is corrupt...
     */
    public void showStage(Stage stage) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource("/main/java/catalogEditor/EditorLayout1.fxml"));
        Scene toExerciseChooser = new Scene(newScene);
        Stage catalog_stage = new Stage();
        catalog_stage.initModality(Modality.WINDOW_MODAL);
        catalog_stage.initOwner(stage);
        catalog_stage.setScene(toExerciseChooser);
        catalog_stage.setTitle("TDDT Client - Catalog Editor");
        catalog_stage.getIcons().add(new Image("file:pictures/icon.png"));
        catalog_stage.setResizable(false);
        catalog_stage.showAndWait();
    }

    /**
     * This method changes the scene to scene 2
     * @throws IOException If the FXML file is corrupt...
     */
    public void onNextButton1Pressed() throws IOException {
        Stage stage = (Stage) cancelbtn.getScene().getWindow();
        Parent newScene = FXMLLoader.load(getClass().getResource("/main/java/catalogEditor/EditorLayout2.fxml"));
        Scene scene2 = new Scene(newScene);
        stage.setScene(scene2);
    }

    /**
     * If the user clicks on the cancel button the stage is closed.
     */
    public void onCancelButtonPressed() {
        ((Stage)cancelbtn.getScene().getWindow()).close();
    }

    /**
     * Coming soon
     */
    public void onSubmitButtonPressed() {

    }

    /**
     * Shows a fancy tooltip if you hover over the timetracking field.
     * We decided to have timetracking enabled at any time, so we don't need the option to disable it.
     */
    public void showTimetrackingTooltip() {
        Tooltip tooltip = new Tooltip("Please purchase the full version of this product to disable tracking!");
        Tooltip.install(ttwrapper,tooltip);
        timetrackingfield.setTooltip(tooltip);
    }
}
