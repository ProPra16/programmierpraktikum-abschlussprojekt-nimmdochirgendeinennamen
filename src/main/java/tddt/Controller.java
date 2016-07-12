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

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.backup.Backup;
import main.java.catalogEditor.CatalogEditor;
import main.java.chart.TrackingChart;
import main.java.exerciseChooser.ExerciseChooser;
import main.java.tracker.ChartTracker;
import main.java.tracker.Tracker;
import main.java.xmlHandler.InvalidFileException;
import main.java.xmlHandler.XMLLoader;

/**
 * This is the controller class for the main stage.
 * @author Dominik Kuhnen
 * @version unknown
 */
public class Controller {
    // TODO thinking about ExceptionHandler for Dialogspawning and way smaller
    // TODO String backup Wrapper, used for GUI-flow and Babysteps aswell.
    // TODO proper Thead killing/interrupting on javafx stage close
	/* might use something like: Sijo Jose on http://stackoverflow.com/questions/22576261/how-get-close-event-of-stage-in-javafx
	 * 	stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	 * 		public void handle(WindowEvent we) {
	 * 			t.interrupt();
	 *		}
	 *	});
	 * not sure if this will include the "X"-Button in the upper right corner...probably tho
	 **/

    @FXML public MenuItem babystpsOnBtn;
    @FXML public MenuItem babystpsOffBtn;
    @FXML public TextArea txtCode;
    @FXML public TextArea txtTest;
    @FXML private Button btnNextStep;
    @FXML private Button btnPrevStep;
    @FXML private ImageView imgTest;
    @FXML private ImageView imgCode;
    @FXML private ImageView imgRefactor;

    private Backup codeBackup;
	private Backup testBackup;
    private Phase phase;
	private Babysteps babysteps;
	private Thread t;
	private TDDTCompiler compiler;
	private ChartTracker chartTracker;
	private Tracker tracker;
    private static XMLLoader xmlLoader;

    private static int exerciseIDX;


    /**
     * This method initializes some objects and disables the test textarea and the next step button.
     */
	@FXML
	public void initialize() {
		phase = new Phase(0, 2, 1);
		compiler = new TDDTCompiler();
		codeBackup = new Backup();
		testBackup = new Backup();
		txtTest.setEditable(false);
		btnNextStep.setDisable(true);
		babysteps = new Babysteps();
	}


    /**
     * Open Catalog chooser window, prepare main window for user interaction.
     * Program supports only 1 class / test per exercise for now. XML-Loader
     * supports multiple classes per exercise.
     *
     */
    @FXML
    public void newTask() throws IOException {
        turnBabystepsOff();

        ExerciseChooser exercisechooser = new ExerciseChooser();
        String[] x = exercisechooser.showStage((Stage) txtCode.getScene().getWindow());
        if (x[0] != null) {
            try {
                File file = new File(x[0]);
                exerciseIDX = Integer.parseInt(x[1]);
                xmlLoader = new XMLLoader(file);
                txtCode.setText(xmlLoader.getClass(exerciseIDX, 0));
                txtTest.setText(xmlLoader.getTest(exerciseIDX, 0));
                txtTest.setEditable(true);
                btnNextStep.setDisable(false);
                if (xmlLoader.isBabystepsActive(exerciseIDX)) {
                    babysteps.setDuration(xmlLoader.getBabyStepsTime(exerciseIDX));
                    turnBabystepsOn();
                }
            } catch (InvalidFileException e) {
                TDDTDialog.showException(e);
            }
            babysteps.startPhase();
            phase.reset();
            updateGUIElements(phase);
        }/*else {
			 new TDDTDialog("alert", "Received an empty catalog path.");
		}*/
        chartTracker = new ChartTracker();
        tracker = new Tracker(txtCode.getText(), txtTest.getText());
        testBackup.setNewBackup(txtTest.getText());
        codeBackup.setNewBackup(txtCode.getText());
    }

                                            /*BABYSTEPS METHODS*/
    /**
     * Creates a new Thread for babysteps. Checks for changes each second.
     */
	@FXML
	public void turnBabystepsOn() {
		t = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(1000);
                    if (babysteps.isEnabled() && !babysteps.timeLeft() && phase.get() != 2) {
                        if (phase.get() == 0)
                            txtTest.setText(testBackup.getLastBackup());
                        if (phase.get() == 1)
                            txtCode.setText(codeBackup.getLastBackup());
                        prevPhase();
                        babysteps.startPhase();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
		t.start();
		babysteps.enable();
	}

    /**
     * Interrupts the babysteps thread and sets babysteps to disabled.
     */
	@FXML
	public void turnBabystepsOff() {
		if (babysteps.isEnabled()) {
			t.interrupt();
		}
		babysteps.disable();
	}

    /**
     * Opens a window in which the user can enter a new babysteps time. Input can be between 1 and 180 seconds.
     */
	@FXML
	public void setBabystepsTime() {
		TDDTDialog dialog = new TDDTDialog("textInput", "babysteps duration in sec. (Between  1 and 180):");
		int result = Integer.parseInt((String) dialog.getValue());
		if (result >= 1 && result <= 180) {
			babysteps.setDuration(result);
		} else {
			new TDDTDialog("alert", "Input not accepted. It has to be between 1 and 180");
		}
	}

                                            /*TRACKING*/

    /**
     * Opens a new window in which the TrackingChart is shown.
     * @see TrackingChart The TrackingChart implementation.
     */
	@FXML
	public void showTrackingChart() {
		TrackingChart tc = new TrackingChart();
        tc.erstelleStage();
	}

    /**
     * Opens a new window in which the tracking log is shown.
     * @see Tracker The Tracker implementation.
     */
	@FXML
	public void showTrackerLog() {
        //TODO Tracker using logHandler
        if (tracker != null)
            tracker.showOutput();
        else
            new TDDTDialog("alert", "Please first load an exercise");
	}

                                            /*CATALOG EDITOR*/

    /**
     * Opens a new stage in which the CatalogEditor is shown.
     * @see CatalogEditor The CatalogEditor implementation.
     * @throws IOException If the FXML for the CatalogEditor stage is corrupt.
     */
	@FXML
    public void onNewExerciseClicked() throws IOException {
        CatalogEditor ce = new CatalogEditor();
        ce.showStage((Stage) txtCode.getScene().getWindow());
    }

                                            /*PHASE LOGIC*/

    /**
     * This method switches the program into the next TDD phase if the entered code meets the requirements.
     * Each time the Phase is switched a new backup and a new tracker dump is created.
     */
    @FXML
    public void nextPhase() {
        boolean passed = false;
        int currentPhase = phase.get();

        if (currentPhase == 0) {
            passed = checkTest();
        } else if (currentPhase == 1 | currentPhase == 2) {
            passed = checkCodeAndTest();
        }

        if (passed) {
            currentPhase = phase.get();
            chartTracker.nextPhase(currentPhase);
            if (currentPhase == 0) {
                testBackup.setNewBackup(txtTest.getText());
                tracker.callDump(txtTest.getText(), 0, false);
            } else {
                codeBackup.setNewBackup(txtCode.getText());
                tracker.callDump(txtCode.getText(), currentPhase, false);
            }
            phase.next();
            updateGUIElements(phase);
        }
    }

    /**
     * Switches the program into the previous phase. Can only happen while in phase 1.
     */
    @FXML
    public void prevPhase() {
        //not relying on GUI-flow
        if (phase.get() == 1) {
            txtCode.setText(codeBackup.getLastBackup());
            chartTracker.greenBack();
            tracker.callDump("", 1, true);
            phase.previous();
            updateGUIElements(phase);
        }
    }

                                            /*INTERNAL METHODS*/

    /**
     * Checks if the test is compilable.
     * @return True if test is compilable. Otherwise false.
     */
	private boolean checkTest() {
		String code = txtTest.getText();
		String testname = xmlLoader.getTestName(exerciseIDX, 0);
		// check if compilable
		if (!checkIfCompilableClass(code))
			return false;
		// try to compile and run tests
		compiler.compile(code, true, testname);
		// settings for next phase
		btnPrevStep.setDisable(false);
		babysteps.startPhase();
		return true;
	}

    /**
     * @return True if code and test are compilable
     */
	private boolean checkCodeAndTest() {
		String code = txtCode.getText();
		String classname = xmlLoader.getClassName(exerciseIDX, 0);
        String testname = xmlLoader.getTestName(exerciseIDX,0);
		// check if compilable
		if (!checkIfCompilableClass(code))
			return false;
		// try to compile code
		boolean passed = compiler.compile(txtCode.getText(), false, classname);
		if (!passed) {
			new TDDTDialog("compileError", compiler.getInfo());
			return false;
		}
		// try to compile and run tests
		passed = compiler.compile(txtTest.getText(), true, testname);
		// display in Dialog if failed
		if (!passed) {
			new TDDTDialog("testFail", compiler.getInfo());
			return false;
		}
		// settings for next phase
		babysteps.startPhase();
		return true;
	}

    /**
     * Checks if the code meets the minimum requirements of a Java class.
     * @param code The code that's checked.
     * @return True if the code meets the requirements.
     */
	private boolean checkIfCompilableClass(String code) {
		// checking if className can be determined
		if (!code.contains("class") & !code.contains("{")) {
			new TDDTDialog("alert", "Could not recognize compilable java classes.");
			return false;
		}
		return true;
	}

    /**
     * Updates the GUI elements to the new phases' requirements.
     * @param phase The new phase.
     */
	private void updateGUIElements(Phase phase) {
		switch (phase.get()) {
		case 0:
			imgTest.setOpacity(1.0);
			imgCode.setOpacity(0.2);
			imgRefactor.setOpacity(0.2);
			txtTest.setDisable(false);
			txtCode.setDisable(true);
			btnPrevStep.setDisable(true);
			break;
		case 1:
			imgTest.setOpacity(0.2);
			imgCode.setOpacity(1.0);
			imgRefactor.setOpacity(0.2);
			txtTest.setDisable(true);
			txtCode.setDisable(false);
			btnPrevStep.setDisable(false);
			break;
		case 2:
			imgTest.setOpacity(0.2);
			imgCode.setOpacity(0.2);
			imgRefactor.setOpacity(1.0);
			txtTest.setDisable(true);
			txtCode.setDisable(false);
			btnPrevStep.setDisable(true);
			break;
		}
	}
}
