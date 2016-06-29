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
	//TODO thinking about a Phase manager class
	//for Babysteps backup and state at the same time
	//might aswell take Tracker for backup tho

	int  	  phase; //0 = Test, 1 = Code, 2 = Refactor
	Babysteps babysteps;
	Thread 	  t;

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
		phase = 0;
		babysteps = new Babysteps();
	}
	
	@FXML
	public void nextPhase() {
		boolean passed = false;
		switch (phase) {
			case 0: passed = checkTest();
					break;
			case 1: passed = checkCode();
					break;
			case 2: passed = checkRefactor();
					break;
		}

		if (passed) {
			if (phase < 2) phase++;
			else 		   phase = 0;

			updateGUIElements(phase);
		}
	}

	@FXML
	public void prevPhase() {
		if (phase > 0) {
			phase--;
			if (phase == 0)
				btnPrevStep.setDisable(true);
		}
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
		txtCode.setText(task.getCode());
		txtTest.setText(task.getTest());
		babysteps.startPhase();
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
		babysteps.enable();
	}
	
	@FXML
	public void turnBabystepsOff() {
		//t.kill
		babysteps.disable();
	}
	
	@FXML
	public void setBabystepsTime() {
		TDDTDialog dialog = new TDDTDialog(
				"textInput", "babysteps duration in sec. (Between  1 and 180):"
		);
		int result = Integer.parseInt( (String)dialog.getValue() );
		System.out.println(result);
		if (result >= 1 && result <= 180) {
			babysteps.setDuration(result);
		} else {
			new TDDTDialog("alert", "Input not acceptet. It has to be between 1 and 180");
			return;
		}
	}

	private boolean checkTest() {

		//return false if not succeeded
		//settings for next phase
		btnPrevStep.setDisable(false);
		babysteps.startPhase();
		return true;
	}

	private boolean checkCode() {
		//return false if not succeeded
		//settings for next phase
		babysteps.startPhase();
		return true;
	}

	private boolean checkRefactor() {
		//return false if not succeeded
		//settings for next phase
		btnPrevStep.setDisable(true);
		babysteps.startPhase();
		return true;
	}

	private void updateGUIElements(int phase) {
		switch (phase) {
			case 0: imgTest.setOpacity(1.0);
					imgCode.setOpacity(0.2);
					imgRefactor.setOpacity(0.2);
					txtTest.setDisable(false);
					txtCode.setDisable(true);
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

	public void test() {
		/*
		//CompilationUnit cuCode = new CompilationUnit("task1", txtCode.getText(), false);
		CompilationUnit cuTest = new CompilationUnit("task1", txtTest.getText(), true);

		//JavaStringCompiler jscCode = CompilerFactory.getCompiler(cuCode);
		JavaStringCompiler jscTest = CompilerFactory.getCompiler(cuTest);

		//jscCode.compileAndRunTests();
		jscTest.compileAndRunTests();

		//CompilerResult cr = jscCode.getCompilerResult();
		TestResult tr = jscTest.getTestResult();
		CompilerResult cr = jscTest.getCompilerResult();

		int error = tr.getNumberOfSuccessfulTests();
		boolean bool = cr.hasCompileErrors();
		System.out.println(error + " " + bool);
		*/
	}
}