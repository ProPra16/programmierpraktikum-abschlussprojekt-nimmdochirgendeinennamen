package chart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class creates the TrackingChart out of the data gathered by the tracker.
 * @author Kai Brandt
 */
public class TrackingChart implements Chart{

	/**
	 * This method reads the amount of time spent in each phase from a file.
	 * @param info The phase of which the needed data is returned (1 = green, 2 = red, 3 = refactor).
	 * @return The amount of time the user spent in the passed phase.
     */
	private int leseZeiten(int info) {
		try(BufferedReader read = new BufferedReader(new FileReader("TrackingData.txt"))){
			read.readLine();
			String green = read.readLine();
			read.readLine();
			String red = read.readLine();
			read.readLine();
			String refactor = read.readLine();
		
			int greenPhase = Integer.parseInt(green);
			int redPhase  = Integer.parseInt(red);
			int refactorPhase = Integer.parseInt(refactor);
		
			if(info == 1) return greenPhase;
			if(info == 2) return redPhase;
			if(info == 3) return refactorPhase;
						
			read.close();
		}
		catch (IOException ignored){}
		return 0;
	}

    /**
     * @param i The number of which we are calculating the share.
     * @param p  = 100 / (red+green+refactor)
     * @return The share of i
     */
    @Override
    public double berechneProzente(double i, double p){
        double prozent = i * p;
        prozent = Math.round(prozent * 10)/10.0;
        return prozent;
    }

    /**
     * @return A string which contains the shares of red, green and refactor in the total time.
     */
    private String gebeProzenteAus() {
        double red = leseZeiten(1);
        double green = leseZeiten(2);
        double refactor = leseZeiten(3);
        double uebergang = red + green + refactor;
        double prozent = 100 / uebergang;

        red = berechneProzente(red, prozent);
        green = berechneProzente(green, prozent);
        refactor = berechneProzente(refactor, prozent);

        return ("Sie haben " + red + "% ihrer Zeit in der Phase RED, \n                 "
                + green + "% in der Phase GREEN und \n                 "
                + refactor +"% in der REFACTOR-Phase verbracht.");
    }


    /**
     * This method is used to create a PieChart out of the information we got from
     * @see #leseZeiten(int) .
     * @return The created PieChart which contains the gathered information.
     */
    private PieChart erstellePieChart() {
		ObservableList<PieChart.Data> daten = FXCollections.observableArrayList(
                new PieChart.Data("Red " + leseZeiten(1) + " Sekunden", leseZeiten(1)),                	
                new PieChart.Data("Green " + leseZeiten(2) + " Sekunden", leseZeiten(2)),	                
                new PieChart.Data("Refactor " + leseZeiten(3) + " Sekunden", leseZeiten(3)));
        PieChart pieChart = new PieChart(daten);
        pieChart.setTitle(gebeProzenteAus());
               
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


    /**
     * This method creates the scene and the stage and shows it.
     */
	@Override
	public void erstelleStage(){
        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(erstellePieChart());
		Stage fenster = new Stage();
		fenster.setTitle("TDDT Client - TrackingChart");
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		fenster.setScene(scene);
        fenster.setResizable(false);
        fenster.getIcons().add(new Image("file:pictures/icon.png"));
		fenster.showAndWait();
	}		
}
