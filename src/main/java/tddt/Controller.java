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

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tddt.backup.Backup;
import tddt.catalog.editor.CatalogEditor;
import tddt.catalog.exercisechooser.ExerciseChooser;
import tddt.catalog.xmlhandler.InvalidFileException;
import tddt.catalog.xmlhandler.XMLLoader;
import tddt.chart.TrackingChart;
import tddt.tracker.ChartTracker;
import tddt.tracker.Tracker;

/**
 * This is the controller class for the main stage.
 * @author unknown
 * @version unknown
 */
public class Controller {
    // TODO thinking about ExceptionHandler for Dialogspawning and way smaller


    @FXML public TextArea txtCode;
    @FXML public TextArea txtTest;
    @FXML private Button btnNextStep;
    @FXML private Button btnPrevStep;
    @FXML private ImageView imgTest;
    @FXML private ImageView imgCode;
    @FXML private ImageView imgRefactor;
    @FXML public Label lblTimeLeft;
    @FXML public Label lblCounter;

    private Backup codeBackup;
	private Backup testBackup;
    private Phase phase;
	private Babysteps babysteps;
	private Thread t;
	private Thread m;
	private TDDTCompiler compiler;
	private ChartTracker chartTracker;
	private Tracker tracker;
    private static XMLLoader xmlLoader;

    private static int exerciseIDX;

    /**
     * This method initializes some objects and disables the test textarea and the next step button.
	 * Teammember continued to change it to not being an initialize method. Now its never used so whatever...
     */
    @FXML
	public void initialize() {
		phase       = new Phase(0, 2, 1);
		compiler    = new TDDTCompiler();
		codeBackup  = new Backup();
		testBackup  = new Backup();
        babysteps   = new Babysteps();
		txtTest.setEditable(false);
		btnNextStep.setDisable(true);
	}


    			/*PHASE LOGIC*/

	/**
	 * This method switches the program into the next TDD phase if the entered
	 * code meets the requirements. Each time the Phase is switched a new backup
	 * and a new tracker dump is created.
	 */
	@FXML
	public void nextPhase() {
		boolean passed = false;
		int currentPhase = phase.get();

		// compile
		if (currentPhase == 0) {
			passed = checkTest();
		} else if (currentPhase == 1 | currentPhase == 2) {
			passed = checkCodeAndTest();
		}

		// actions if compiled properly and tests run
		if (passed) {
			currentPhase = phase.get();
			chartTracker.nextPhase(currentPhase);
			// TODO include branching to tracker
			if (currentPhase == 0) {
				testBackup.setNewBackup(txtTest.getText());
				tracker.callDump(txtTest.getText(), 0, false);
			} else {
				codeBackup.setNewBackup(txtCode.getText());
				tracker.callDump(txtCode.getText(), currentPhase, false);
			}
			phase.next();
			updateGUIElements(phase);
			if(babysteps.isEnabled()) babysteps.startPhase();
		}
	}

	/**
	 * Switches the program into the previous phase. Can only happen while in
	 * phase 1.
	 */
	@FXML
	public void prevPhase() {
		// not relying on GUI-flow
		if (phase.get() == 1) {
			txtCode.setText(codeBackup.getLastBackup());
			chartTracker.greenBack();
			tracker.callDump("", 1, true);
			phase.previous();
			updateGUIElements(phase);
			babysteps.startPhase();
		}
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
                File file   = new File(x[0]);
                exerciseIDX = Integer.parseInt(x[1]);
                xmlLoader   = new XMLLoader(file);
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

				/*BABYSTEPS METHODS*/
	/**
	 * Creates a new Thread for babysteps. Checks for changes each second.
	 */
	public void turnBabystepsOn() {

		t = new Thread(() -> {
			Thread thisThread = Thread.currentThread();
			while (t == thisThread) {
				try {
					Thread.sleep(1000);
					if (babysteps.isEnabled() && !babysteps.timeLeft() && phase.get() != 2) {
						if (phase.get() == 0) {
							txtTest.setText(testBackup.getLastBackup());
							babysteps.startPhase();
						}
						if (phase.get() == 1) {
							txtCode.setText(codeBackup.getLastBackup());
						prevPhase();
						}
					}
				} catch (InterruptedException e) {
					stop();
				}
			}
		});


	        m = new Thread(() -> {
				Thread thisThread = Thread.currentThread();
	            while (m == thisThread) {
	                try {
						Thread.sleep(1000);
						Platform.runLater(() -> {
							if(babysteps.timeLeft()) {
								babysteps.update();
								lblCounter.setText(babysteps.timeLeftR);
							}else{
								lblCounter.setText(String.valueOf((int)babysteps.duration));
								babysteps.update();
							}
						});
	                }
	                catch (InterruptedException e) {
	                    stopUpdate();
	                }
	            }
	        });

	        (txtCode.getScene().getWindow()).setOnCloseRequest(we ->  {
				stop();
				stopUpdate();
			});

	        m.start();
	        t.start();
	        babysteps.enable();
	    }

	    private void stop() {
	        Thread.currentThread().interrupt();
	        t = null;
	    }

	    private void stopUpdate() {
	        Thread.currentThread().interrupt();
	        m = null;
	    }



	/**
	 * Interrupts the babysteps thread and sets babysteps to disabled.
	 */
	private void turnBabystepsOff() {
		if (babysteps.isEnabled()) {
			stop(); stopUpdate();
		}
		babysteps.disable();
	}

	/**
	 * Opens a window in which the user can enter a new babysteps time. Input
	 * can be between 1 and 180 seconds.
	 * Deprecated: The time will be set over the XML-file in the newer versions
	 */
	@Deprecated
	public void setBabystepsTime() {
		TDDTDialog dialog = new TDDTDialog("textInput", "babysteps duration in sec. (Between  1 and 180):");
		int result = Integer.parseInt((String) dialog.getValue());
		if (result >= 1 && result <= 180) {
			babysteps.setDuration(result);
		} else {
			new TDDTDialog("alert", "Input not accepted. It has to be between 1 and 180");
		}
	}


				/*INTERNAL METHODS*/

    /**
     * Checks if the test is compilable and is failing.
     * @return True if test is compilable and is failing. Otherwise false.
     */
	private boolean checkTest() {
		String code       	    = txtCode.getText();
		String test				= txtTest.getText();
		String codeClassName    = xmlLoader.getClassName(exerciseIDX, 0);
        String testClassName    = xmlLoader.getTestName(exerciseIDX, 0);
		// check if compilable
		if (!checkIfCompilableClass(code))
			return false;
		// try to compile and run tests
		boolean passed = compiler.compileCode(test, testClassName);
		if (!passed) {
			//on compile failed
			new TDDTDialog("compileError", compiler.getInfo());
			return true;
		} else {
			//on compile succeeded
			passed = compiler.compileAndRunTests(code, codeClassName, test, testClassName);
			if (passed) {
				//on tests succeeded
				new TDDTDialog("alert", "At least one test has to fail!");
				return false;
			}
		}
		// settings for next phase
		btnPrevStep.setDisable(false);
		babysteps.startPhase();
		return true;
	}

    /**
     * @return True if code and test are compilable
     */
	private boolean checkCodeAndTest() {
		String code       	    = txtCode.getText();
		String test				= txtTest.getText();
		String codeClassName    = xmlLoader.getClassName(exerciseIDX, 0);
        String testClassName    = xmlLoader.getTestName(exerciseIDX, 0);
		// check if compilable
		if (!checkIfCompilableClass(code))
			return false;
		// try to compile code
		//boolean passed = compiler.compileCodeAndRunTests(code, codeClassName, testname, testClassName)
		boolean passed = compiler.compileCode(txtCode.getText(), codeClassName);
		if (!passed) {
			new TDDTDialog("compileError", compiler.getInfo());
			return false;
		}
		// try to compile and run tests
		passed = compiler.compileAndRunTests(code, codeClassName, test, testClassName);
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
