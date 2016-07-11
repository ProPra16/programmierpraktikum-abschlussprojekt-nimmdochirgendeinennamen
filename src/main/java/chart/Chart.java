package main.java.chart;


import javafx.scene.Scene;
import javafx.scene.chart.*;

interface Chart{
	
	int leseZeiten(int info);	//Liest aus der durch Tracking erstellten Textdatei die Zeiten der Phasen 
								//aus. Je nach uebergebenem int wird "red" = 1 zurueckgegeben, "green" = 2 usw.
	
	PieChart erstellePieChart();				//Erstellt aus den Zeiten ein PieChart 
	
	Scene erstelleScene();			//Fuegt den PieChart zu einer Scene hinzu und gibt diese zurueck
	
	String gebeProzenteAus();				//Gibt die uebergebene Scene auf der Stage aus
	
	double berechneProzente(double i, double x);     //berechnet den Prozentwert von i im Grundwert x.
}
