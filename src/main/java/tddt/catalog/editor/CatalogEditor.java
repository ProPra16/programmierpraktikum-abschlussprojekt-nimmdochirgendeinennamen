package tddt.catalog.editor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tddt.TDDTCompiler;
import tddt.TDDTDialog;
import tddt.catalog.xmlhandler.XMLWriter;


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
    private static String[] firstScene = new String[5];

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
        if(babystepstimefield.getText().chars().allMatch( Character::isDigit )) {
            int i = Integer.parseInt(babystepstimefield.getText());
            if(((i < 181) && ( i > 0)) && (descfield.getText().length() > 15) && exnamefield.getText().length() > 3){
                firstScene[0] = exnamefield.getText();
                firstScene[1] = descfield.getText();
                firstScene[2] = babystepsfield.getText();
                firstScene[3] = babystepstimefield.getText();
                firstScene[4] = timetrackingfield.getText();
                Stage stage = (Stage) cancelbtn.getScene().getWindow();
                Parent newScene = FXMLLoader.load(getClass().getResource("/main/java/catalogEditor/EditorLayout2.fxml"));
                Scene scene2 = new Scene(newScene);
                stage.setScene(scene2);
            }else new TDDTDialog("alert", "The data you entered does not match the quality criteria.\n\n" +
                    "Please buy the full version of this program in order to create shitty exercises!");
        }else new TDDTDialog("alert", "Babystepstime needs to be numeric.");
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
    public void onSubmitButtonPressed() throws ParserConfigurationException, IOException, SAXException, TransformerException {

        TDDTCompiler tc = new TDDTCompiler();

        if((tc.compile(classTextArea.getText(),false,classnamefield.getText())) & tc.compile(testTextArea.getText(),false,testnamefield.getText())) {
            File dummy = null;
            FileChooser fc;
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
            int i;
            switch (i = howToSaveDialog()) {
                case 0:
                    fc = new FileChooser();
                    fc.getExtensionFilters().add(extFilter);
                    File newfile = dummy = fc.showSaveDialog(submitbtn.getScene().getWindow());
                    if(newfile != null) saveExercise(0, newfile);
                    break;
                case 1:
                    fc = new FileChooser();
                    fc.getExtensionFilters().add(extFilter);
                    File appendfile = dummy = fc.showOpenDialog(submitbtn.getScene().getWindow());
                    if (appendfile != null) saveExercise(1, appendfile);
                    break;
                default:
                    break;

            }
            if(i != -1 && dummy != null){
                new TDDTDialog("alert", "Files have been saved successfully.");
                ((Stage) cancelbtn.getScene().getWindow()).close();
            }
        }else{
            new TDDTDialog("alert", "The class / test have to be compilable. Please enter at least a class skeleton.");
        }
    }

    private void saveExercise(int mode, File file) throws TransformerException, ParserConfigurationException, IOException, SAXException {
        XMLWriter xmlWriter = new XMLWriter();
        for (String x: firstScene) {
            System.out.println(x);
        }
        switch(mode){
            case 0:
                xmlWriter.newXMLFile(
                        file, firstScene[0], firstScene[1], classTextArea.getText(), classnamefield.getText(), testTextArea.getText(), testnamefield.getText(), firstScene[2], firstScene[3], firstScene[4]);
                break;
            case 1: xmlWriter.appendXMLFile(
                        file, firstScene[0], firstScene[1], classTextArea.getText(), classnamefield.getText(), testTextArea.getText(), testnamefield.getText(), firstScene[2], firstScene[3], firstScene[4]);
                break;
            default:
        }
    }

    /**
     * Shows a dialog, which prompts the user to choose how he wants to save the exercise.
     * @return 0 == New catalog, 1 == Append a catalog, -1 == cancel or error.
     */
    private int howToSaveDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("What is your plan, master?");
        alert.setHeaderText("Dobby is a free elf, and Dobby has come to save your exercise!");
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:pictures/icon.png"));
        alert.setContentText("Do you want me to save the exercise to an existing catalog, or do you want to create a new one?");
        ButtonType newCatalog = new ButtonType("Create a new catalog");
        ButtonType appendCatalog = new ButtonType("Append an existing catalog");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(newCatalog, appendCatalog, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()) {
            if (result.get() == newCatalog) {
                return 0;
            } else if (result.get() == appendCatalog) {
                return 1;
            }
        }
        return -1;
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
