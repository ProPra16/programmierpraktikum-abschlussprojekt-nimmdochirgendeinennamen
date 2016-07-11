package projekt7;


import javafx.scene.Scene;
import javafx.scene.chart.*;

interface Chart{
	
	int leseZeiten(int info);	//Liest aus der durch Tracking erstellten Textdatei die Zeiten der Phasen 
								//aus. Je nach übergebenem int wird "red" = 1 zurückgegeben, "green" = 2 usw.
	
	PieChart erstellePieChart();				//Erstellt aus den Zeiten ein PieChart 
	
	Scene erstelleScene();			//Fügt den PieChart zu einer Scene hinzu und gibt diese zurück
	
	String gebeProzenteAus();				//Gibt die übergebene Scene auf der Stage aus
	
	double berechneProzente(double i, double x);     //berechnet den Prozentwert von i im Grundwert x.
}
