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

package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.ExerciseChooser.ExerciseChooser;
import main.java.xmlHandler.InvalidFileException;
import main.java.xmlHandler.XMLLoader;

import java.io.File;
import java.io.IOException;

public class Controller {
	//TODO thinking about ExceptionHandler for Dialogspawning and way smaller Controller and Compiler class
	//TODO String backup Wrapper, used for GUI-flow and Babysteps aswell.
	//for Babysteps backup and state at the same time
	//might aswell take Tracker for backup tho

	String	  backup;  //backup of Phase 1 code for prevPhase() on phase = 2;
	Phase	  phase;
	//#### Babysteps babysteps;
	Thread 	  t;
	TDDTCompiler compiler;

	//ChartTracker chartTracking;
	//Tracker tracking;

	@FXML private Pane pane;
	@FXML private TextArea txtCode;
	@FXML private TextArea txtTest;
	@FXML private Button btnNextStep;
	@FXML private Button btnPrevStep;
	@FXML private ImageView imgTest;
	@FXML private ImageView imgCode;
	@FXML private ImageView imgRefactor;
    private static File file;
    private static int exerciseIDX;
    private static XMLLoader xmlLoader;

    //Interactable objects are disabled until an exercise is loaded
    @FXML
	public void initialize() {
		phase = new Phase(0, 2, 1);
		compiler = new TDDTCompiler();
        txtTest.setEditable(false);
        btnNextStep.setDisable(true);
		//#### babysteps = new Babysteps();

		//chartTracking = new ChartTracker();
		//tracking = new Tracker(txtCode.getText(), txtTest.getText());
	}

    //change phase if code meets requirements
	@FXML
	public void nextPhase() {
		boolean passed = false;
		switch (phase.get()) {
			case 0: passed = checkTest();
					break;
			case 1: passed = checkCode();
					break;
			case 2: passed = checkRefactor();
					break;
		}

		if (passed) {
			//chartTracking.nextPhase(phase.get());		

			if (phase.get() == 0) {
				backup = txtCode.getText();
				//tracking.callDump(txtTest.getText(), 0, false);
			} //else tracking.callDump(txtCode.getText(), phase.get(), false);

			phase.next();
			updateGUIElements(phase);
		}
	}

	@FXML
	public void prevPhase() {
		//chartTracking.greenBack(1);
		//tracking.callDump("", 1, true);

		if (phase.get() == 1) {
			txtCode.setText(backup);
		}
		phase.previous();
		updateGUIElements(phase);

	}

   /*
   * Open Catalog chooser window, prepare main window for user interaction.
   * Program supports only 1 class / test per exercise for now. XML-Loader supports multiple classes per exercise.
   * */
	@FXML
	public void newTask() throws IOException{
        ExerciseChooser exercisechooser = new ExerciseChooser();
        String[] x = exercisechooser.showStage((Stage)txtCode.getScene().getWindow());
        if(x[0] != null) {
            try {
                file = new File(x[0]);
                exerciseIDX = Integer.parseInt(x[1]);
                xmlLoader = new XMLLoader(file);
                txtCode.setText(xmlLoader.getClass(exerciseIDX, 0));
                txtTest.setText(xmlLoader.getTest(exerciseIDX, 0));
                txtTest.setEditable(true);
                btnNextStep.setDisable(false);
                if (xmlLoader.isBabystepsActive(exerciseIDX)) {
                    //turnBabystepsOn();
                    //setBabystepsTime(xmlLoader.getBabyStepsTime(exerciseIDX));
                }
            }catch (InvalidFileException e){
                TDDTDialog.showException(e);
            }
            //#### babysteps.startPhase();
            phase.reset();
            updateGUIElements(phase);
        }else {
			//This popup is annoying as f...
            //new TDDTDialog("alert", "Received an empty catalog path.");
        }
    }

	@FXML
	public void turnBabystepsOn() {
		//TODO proper loop
		/*
  		t = new Thread(
  			() -> {
  				try {
  					Thread.sleep(1000);
  				} catch (InterruptedException e) {}
  				if (babysteps.isEnabled() | babysteps.timeLeft()) { //save calc-time with | instead of ||
  					//TODO restore Backup, maybe with Tracker
  					prevPhase();
  				}
  			}
  		);
  		*/
		//#### babysteps.enable();
	}

	@FXML
	public void turnBabystepsOff() {
		//#### t.kill
		//#### babysteps.disable();
	}

	@FXML
	public void setBabystepsTime() {
		TDDTDialog dialog = new TDDTDialog(
				"textInput", "babysteps duration in sec. (Between  1 and 180):"
		);
		int result = Integer.parseInt( (String)dialog.getValue() );
		if (result >= 1 && result <= 180) {
			//####babysteps.setDuration(result);
		} else {
			new TDDTDialog("alert", "Input not accepted. It has to be between 1 and 180");
        }
	}

	private boolean checkTest() {
		String code = txtTest.getText();
        String testname = xmlLoader.getTestName(exerciseIDX, 0);
		//check if compilable
		if (!checkIfCompilableClass(code)) return false;
		//try to compile and run showStage
		compiler.compile(code, true, testname);
		//settings for next phase
		btnPrevStep.setDisable(false);
		//#### babysteps.startPhase();
		return true;
	}

	private boolean checkCode() {
		String code = txtCode.getText();
        String classname = xmlLoader.getClassName(exerciseIDX,0);
		//check if compilable
		if (!checkIfCompilableClass(code)) return false;
		//try to compile code
		boolean passed = compiler.compile(txtCode.getText(), false, classname);
		if (!passed) {
			new TDDTDialog("compileError", compiler.getInfo());
			return false;
		}
		//try to compile and run showStage
		passed = compiler.compile(txtTest.getText(), true, classname);
		//display in Dialog if failed
		if (!passed) {
			new TDDTDialog("testFail", compiler.getInfo());
			return false;
		}
		//settings for next phase
		//#### babysteps.startPhase();
		return true;
	}

	private boolean checkRefactor() {
		String code = txtTest.getText();
        String classname = xmlLoader.getTestName(exerciseIDX,0);
		//check if compilable
		if (!checkIfCompilableClass(code)) return false;
		//try to compile code
		boolean passed = compiler.compile(txtCode.getText(), false, classname);
		if (!passed) {
			new TDDTDialog("compileError", compiler.getInfo());
			return false;
		}
		//try to compile and run showStage
		passed = compiler.compile(txtTest.getText(), true, classname);
		//display in Dialog if failed
		if (!passed) {
			new TDDTDialog("testFail", compiler.getInfo());
			return false;
		}
		//settings for next phase
		//#### babysteps.startPhase();
		return true;
	}

	private boolean checkIfCompilableClass(String code) {
		//checking if className can be determined
		if (!code.contains("class") & !code.contains("{")) {
			new TDDTDialog("alert", "Could not recognize compilable java classes.");
			return false;
		}
		return true;
	}

	private void updateGUIElements(Phase phase) {
		switch (phase.get()) {
			case 0: imgTest.setOpacity(1.0);
					imgCode.setOpacity(0.2);
					imgRefactor.setOpacity(0.2);
					txtTest.setDisable(false);
					txtCode.setDisable(true);
					btnPrevStep.setDisable(true);
					break;
			case 1: imgTest.setOpacity(0.2);
					imgCode.setOpacity(1.0);
					imgRefactor.setOpacity(0.2);
					txtTest.setDisable(true);
					txtCode.setDisable(false);
					break;
			case 2: imgTest.setOpacity(0.2);
					imgCode.setOpacity(0.2);
					imgRefactor.setOpacity(1.0);
					txtTest.setDisable(true);
					txtCode.setDisable(false);
					break;
		}
	}
}
