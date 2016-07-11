

package main.java.chart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

//Das PieChart wird ueber TrackingChart.main(null); erstellt und aufgerufen
//Die Datei TrackingData.txt wird ausgelesen und verarbeitet die enthaltenen Informationen
//zu einem PieChart. 

public class TrackingChart extends Application{
	
    @Override 
    public void start(Stage stage) {
	     stage.setTitle("Tracking Analyse");
	     stage.setWidth(520);
	     stage.setHeight(500);
		 stage.setScene(erstelleScene());
	     stage.show();		
    }

	private int leseZeiten(int info) {
		try(BufferedReader read = new BufferedReader(new FileReader("TrackingData.txt"))){
			read.readLine();
			String green = read.readLine();
			read.readLine();
			String red = read.readLine();
			read.readLine();
			String refactor = read.readLine();

			int gre = Integer.parseInt(green);
			int re  = Integer.parseInt(red);
			int ref = Integer.parseInt(refactor);

			if(info == 1) return gre;
			if(info == 2) return re;
			if(info == 3) return ref;

			read.close();
		}
		catch (IOException e){
				e.printStackTrace();
			}
		return 0;
	}

	private PieChart erstellePieChart() {
		ObservableList<PieChart.Data> daten = FXCollections.observableArrayList(
                new PieChart.Data("Red " + leseZeiten(1) + " Sekunden", leseZeiten(1)),
                new PieChart.Data("Green " + leseZeiten(2) + " Sekunden", leseZeiten(2)),
                new PieChart.Data("Refactor " + leseZeiten(3) + " Sekunden", leseZeiten(3)));
        PieChart pieChart = new PieChart(daten);
        pieChart.setTitle(gebeProzenteAus());
        pieChart.setLegendVisible(false);

        int i = 0;
        for (PieChart.Data data : daten) {
        	if(i == 0) data.getNode().setStyle("-fx-pie-color: #FF0000;");
        	if(i == 1) data.getNode().setStyle("-fx-pie-color: #008000;");
        	if(i == 2) data.getNode().setStyle("-fx-pie-color: #000000;");
            i++;
        }
        return pieChart;
	}

	private String gebeProzenteAus() {
		double red = leseZeiten(1);
		double green = leseZeiten(2);
		double refactor = leseZeiten(3);
		double uebergang = red + green + refactor;
		double prozent = 100 / uebergang;

		red = berechneProzente(red, prozent);
		green = berechneProzente(green, prozent);
		refactor = berechneProzente(refactor, prozent);

		String ausgabe = ("Sie haben " + red + "% ihrer Zeit in der Phase RED verbracht, \n                 "
		+ green + "% in der Phase GREEN und \n                 "
		+ refactor +"% in der REFACTOR-Phase.");

		return ausgabe;
	}

	public static double berechneProzente(double i, double p){
		double prozent = i * p;
		prozent = Math.round(prozent * 1000)/1000.0;
		return prozent;
	}

	private Scene erstelleScene() {
		Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(erstellePieChart());       
		return scene;
	}

	public static void main(String[] args) {
	        launch(args);
	}
}

