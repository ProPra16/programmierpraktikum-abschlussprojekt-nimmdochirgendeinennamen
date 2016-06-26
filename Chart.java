package projekt7;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

interface Chart{
	static File file = new File("TrackingData.txt");
	static Stage stage = new Stage();
	
	
	int leseZeiten(int info);	//Liest aus der durch Tracking erstellten Textdatei die Zeiten der Phasen 
								//aus. Je nach übergebenem int wird "red" = 1 zurückgegeben, "green" = 2 usw.

	PieChart erstellePieChart();				//Erstellt aus den Zeiten ein PieChart 
	
	Scene erstelleScene(PieChart chart);			//Fügt den PieChart zu einer Scene hinzu und gibt diese zurück
	
	void gebeAus(Scene scene);				//Gibt die übergebene Scene auf der Stage aus
}
