package projekt7;


import javafx.scene.Scene;
import javafx.scene.chart.*;

interface Chart{
	
	int leseZeiten(int info);	//Liest aus der durch Tracking erstellten Textdatei die Zeiten der Phasen 
								//aus. Je nach �bergebenem int wird "red" = 1 zur�ckgegeben, "green" = 2 usw.
	
	PieChart erstellePieChart();				//Erstellt aus den Zeiten ein PieChart 
	
	Scene erstelleScene();			//F�gt den PieChart zu einer Scene hinzu und gibt diese zur�ck
	
	String gebeProzenteAus();				//Gibt die �bergebene Scene auf der Stage aus
	
	double berechneProzente(double i, double x);     //berechnet den Prozentwert von i im Grundwert x.
}
