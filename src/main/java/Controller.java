package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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

			updatePictures(phase);
		}
	}

	@FXML
	public void prevPhase() {
		if (phase > 0) {
			phase--;
			if (phase == 0)
				btnPrevStep.setDisable(true);
		}
		updatePictures(phase);
	}

	@FXML
	public void newTask() {
		Task task = new Task((Stage) pane.getScene().getWindow());
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
		babysteps.disable();
	}
	
	@FXML
	public void setBabystepsTime() {
		Dialogs dialog = new Dialogs(
				"textInput", "babysteps duration in sec. (Between  1 and 180):"
		);
		int result = Integer.parseInt( (String)dialog.getValue() );
		System.out.println(result);
		if (result >= 1 && result <= 180) {
			babysteps.setDuration(result);
		} else {
			new Dialogs("alert", "Input not acceptet. It has to be between 1 and 180");
			return;
		}
	}

	private boolean checkTest() {

		//return if not succeeded
		btnPrevStep.setDisable(false);
		babysteps.startPhase();
		return true;
	}

	private boolean checkCode() {
		//return if not succeeded
		babysteps.startPhase();
		return true;
	}

	private boolean checkRefactor() {
		//return if not succeeded
		btnPrevStep.setDisable(true);
		babysteps.startPhase();
		return true;
	}

	private void updatePictures(int phase) {
		switch (phase) {
			case 0: imgTest.setOpacity(1.0);
					imgCode.setOpacity(0.2);
					imgRefactor.setOpacity(0.2);
					break;
			case 1: imgTest.setOpacity(0.2);
					imgCode.setOpacity(1.0);
					imgRefactor.setOpacity(0.2);
					break;
			case 2: imgTest.setOpacity(0.2);
					imgCode.setOpacity(0.2);
					imgRefactor.setOpacity(1.0);
					break;
		}
	}
}