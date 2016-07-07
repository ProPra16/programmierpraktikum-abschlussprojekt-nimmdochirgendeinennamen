package main.java;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

	@FXML private Pane pane;
	@FXML private TextArea txtCode;
	@FXML private TextArea txtTest;
	@FXML private Button btnNextStep;
	@FXML private Button btnPrevStep;
	@FXML private ImageView imgTest;
	@FXML private ImageView imgCode;
	@FXML private ImageView imgRefactor;

	@FXML
	public void initialize() {
		phase = new Phase(0, 2, 1);
		compiler = new TDDTCompiler();
		//#### babysteps = new Babysteps();

		ChartTracker chartTracking = new ChartTracker();
		Tracker tracking = new Tracker(txtCode.getText(), txtTest.getText());
	}

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
			chartTracking.nextPhase();		

			if (phase.get() == 0) {
				backup = txtCode.getText();
				tracking.dump(txtTest.getText(), 0);
			} else tracking.dump(txtCode.getText(), phase.get());

			phase.next();
			updateGUIElements(phase);
		}
	}

	@FXML
	public void prevPhase() {
		chartTracking.greenBack();
		//tracking

		if (phase.get() == 1) {
			txtCode.setText(backup);
		}
		phase.previous();
		updateGUIElements(phase);

	}

	@FXML
	public void newTask() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose a catalog-folder");
		File file = fc.showOpenDialog( (Stage) pane.getScene().getWindow() );
		if (file == null) {
			new TDDTDialog("alert", "File could not get read properly");
			return;
		}

		TDDTTask task = new TDDTTask(file);

		if (task.getCode() == null && task.getCode() == null) {
			new TDDTDialog("alert", "The chosen file is not a Task");
		}

		txtCode.setText(task.getCode());
		txtTest.setText(task.getTest());
		//#### babysteps.startPhase();
		phase.reset();
		updateGUIElements(phase);
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
			new TDDTDialog("alert", "Input not acceptet. It has to be between 1 and 180");
			return;
		}
	}

	private boolean checkTest() {
		String code = txtTest.getText();
		//check if compilable
		if (!checkIfCompilableClass(code)) return false;
		//try to compile and run test
		compiler.compile(code, true);
		//settings for next phase
		btnPrevStep.setDisable(false);
		//#### babysteps.startPhase();
		return true;
	}

	private boolean checkCode() {
		String code = txtTest.getText();
		//check if compilable
		if (!checkIfCompilableClass(code)) return false;
		//try to compile code
		boolean passed = compiler.compile(txtCode.getText(), false);
		if (!passed) {
			new TDDTDialog("compileError", compiler.getInfo());
			return false;
		}
		//try to compile and run test
		passed = compiler.compile(txtTest.getText(), true);
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
		//check if compilable
		if (!checkIfCompilableClass(code)) return false;
		//try to compile code
		boolean passed = compiler.compile(txtCode.getText(), false);
		if (!passed) {
			new TDDTDialog("compileError", compiler.getInfo());
			return false;
		}
		//try to compile and run test
		passed = compiler.compile(txtTest.getText(), true);
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
