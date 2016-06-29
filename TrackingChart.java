package projekt7;

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

public class TrackingChart extends Application implements Chart {
	
    @Override 
    public void start(Stage stage) {
	     stage.setTitle("Tracking Analyse");
	     stage.setWidth(520);
	     stage.setHeight(500);
		 stage.setScene(erstelleScene());
	     stage.show();		
    }
    	
	@Override
	public int leseZeiten(int info) {
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
		
	@Override
	public PieChart erstellePieChart() {
		ObservableList<PieChart.Data> daten = FXCollections.observableArrayList(
                new PieChart.Data("Red " + leseZeiten(1) + " Sekunden", leseZeiten(1)),                	
                new PieChart.Data("Green " + leseZeiten(2) + " Sekunden", leseZeiten(2)),	                
                new PieChart.Data("Refactor " + leseZeiten(3) + " Sekunden", leseZeiten(3)));
        PieChart pieChart = new PieChart(daten);
        pieChart.setTitle(setzteTitel());
        pieChart.setLegendVisible(false);
       
        int i = 0;        
        for (PieChart.Data data : daten) {
        	if(i == 0) data.getNode().setStyle("-fx-pie-color: #FF0000;");
        	if(i == 1) data.getNode().setStyle("-fx-pie-color: #008000;");
        	if(i == 2) data.getNode().setStyle("-fx-pie-color: #4682B4;");
            i++;
        }         
        return pieChart;
	}
	
	@Override
	public String setzteTitel() {
		double red = leseZeiten(1);
		double green = leseZeiten(2);
		double refactor = leseZeiten(3);
		double übergang = red + green + refactor;
		double prozent = 100 / übergang;
		red = red * prozent;
		red = Math.round(red * 1000)/1000.0;
		green = green * prozent;
		green = Math.round(green * 1000)/1000.0;
		refactor = refactor * prozent;
		refactor = Math.round(refactor * 1000)/1000.0;
		
		String ausgabe = ("Sie haben " + red + "% ihrer Zeit in der Phase RED verbracht, \n                 "
		+ green + "% in der Phase GREEN und \n                 "
		+ refactor +"% in der REFACTOR-Phase.");
		
		return ausgabe;
	}
	
	@Override
	public Scene erstelleScene() {
		Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(erstellePieChart());       
		return scene;
	}

	public static void main(String[] args) {
	        launch(args);
	}
}
