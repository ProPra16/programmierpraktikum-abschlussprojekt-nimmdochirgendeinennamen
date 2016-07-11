package projekt7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TrackingChartBeheben {

	private static int leseZeiten(int info) {
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
			//	e.printStackTrace();
				return 0;
			}
		return 0;
	}
		
	private static PieChart erstellePieChart() {
		ObservableList<PieChart.Data> daten = FXCollections.observableArrayList(
                new PieChart.Data("Red " + leseZeiten(1) + " Sekunden", leseZeiten(1)),                	
                new PieChart.Data("Green " + leseZeiten(2) + " Sekunden", leseZeiten(2)),	                
                new PieChart.Data("Refactor " + leseZeiten(3) + " Sekunden", leseZeiten(3)));
        PieChart pieChart = new PieChart(daten);
        pieChart.setTitle(gebeProzenteAus());
        pieChart.setLegendVisible(true);
        pieChart.setLegendSide(Side.BOTTOM);
               
        int i = 0;        
        for (PieChart.Data data : daten) {
        	if(i == 0) data.getNode().setStyle("-fx-pie-color: #FF0000;");
        	if(i == 1) data.getNode().setStyle("-fx-pie-color: #008000;");
        	if(i == 2) data.getNode().setStyle("-fx-pie-color: #000000;");
            i++;
        }         
        
        pieChart.setClockwise(false);
        pieChart.setStartAngle(180);
        return pieChart;
	}
	
	private static String gebeProzenteAus() {
		double red = leseZeiten(1);
		double green = leseZeiten(2);
		double refactor = leseZeiten(3);
		double übergang = red + green + refactor;
		double prozent = 100 / übergang;

		red = berechneProzente(red, prozent);
		green = berechneProzente(green, prozent);
		refactor = berechneProzente(refactor, prozent);
		
		String ausgabe = ("Sie haben " + red + "% ihrer Zeit in der Phase RED verbracht, \n                 "
		+ green + "% in der Phase GREEN und \n                 "
		+ refactor +"% in der REFACTOR-Phase.");
		
		return ausgabe;
	}
	
	static double berechneProzente(double i, double p){
		double prozent = i * p;
		prozent = Math.round(prozent * 1000)/1000.0;
		return prozent;
	}
	
	private static Scene erstelleScene() {
		Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(erstellePieChart());       
		return scene;
	}

	public static void erstelleStage(){
		Stage fenster = new Stage();
		fenster.initModality(Modality.APPLICATION_MODAL);
		fenster.setTitle("TrackingChart");
		
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		fenster.setScene(erstelleScene());
		fenster.showAndWait();
	}
		
}
