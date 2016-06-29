<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
package projekt7;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

interface Chart{
	static File file = new File("TrackingData.txt");
	static Stage stage = new Stage();
	
	
	int leseZeiten(int info);	//Liest aus der durch Tracking erstellten Textdatei die Zeiten der Phasen 
								//aus. Je nach �bergebenem int wird "red" = 1 zur�ckgegeben, "green" = 2 usw.
	
	PieChart erstellePieChart();				//Erstellt aus den Zeiten ein PieChart 
	
	Scene erstelleScene();			//F�gt den PieChart zu einer Scene hinzu und gibt diese zur�ck
	
	String setzteTitel();				//Gibt die �bergebene Scene auf der Stage aus
}
=======
package projekt7;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

interface Chart{
	static File file = new File("TrackingData.txt");
	static Stage stage = new Stage();
	
	
	int leseZeiten(int info);	//Liest aus der durch Tracking erstellten Textdatei die Zeiten der Phasen 
								//aus. Je nach �bergebenem int wird "red" = 1 zur�ckgegeben, "green" = 2 usw.

	PieChart erstellePieChart();				//Erstellt aus den Zeiten ein PieChart 
	
	Scene erstelleScene(PieChart chart);			//F�gt den PieChart zu einer Scene hinzu und gibt diese zur�ck
	
	void gebeAus(Scene scene);				//Gibt die �bergebene Scene auf der Stage aus
}
>>>>>>> a55a274de2f524c3f9d0b9add30efd24ee2a4291
=======
package projekt7;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

interface Chart{
	static File file = new File("TrackingData.txt");
	static Stage stage = new Stage();
	
	
	int leseZeiten(int info);	//Liest aus der durch Tracking erstellten Textdatei die Zeiten der Phasen 
								//aus. Je nach �bergebenem int wird "red" = 1 zur�ckgegeben, "green" = 2 usw.

	PieChart erstellePieChart();				//Erstellt aus den Zeiten ein PieChart 
	
	Scene erstelleScene(PieChart chart);			//F�gt den PieChart zu einer Scene hinzu und gibt diese zur�ck
	
	void gebeAus(Scene scene);				//Gibt die �bergebene Scene auf der Stage aus
}
>>>>>>> origin/chart
=======
package projekt7;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

interface Chart{
	static File file = new File("TrackingData.txt");
	static Stage stage = new Stage();
	
	
	int leseZeiten(int info);	//Liest aus der durch Tracking erstellten Textdatei die Zeiten der Phasen 
								//aus. Je nach �bergebenem int wird "red" = 1 zur�ckgegeben, "green" = 2 usw.

	PieChart erstellePieChart();				//Erstellt aus den Zeiten ein PieChart 
	
	Scene erstelleScene(PieChart chart);			//F�gt den PieChart zu einer Scene hinzu und gibt diese zur�ck
	
	void gebeAus(Scene scene);				//Gibt die �bergebene Scene auf der Stage aus
}
>>>>>>> a55a274de2f524c3f9d0b9add30efd24ee2a4291
