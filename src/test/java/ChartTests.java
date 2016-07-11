package projekt7;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class ChartTests {
	
	@Test
	public void testeProzentRechung(){
		assertEquals(TrackingChart.berechneProzente(25, (100.0/100.0)), 25.0, 0.05);
	}
	
	@Test
	public void testeProzentRechnung2(){
		assertEquals(4.251 ,TrackingChart.berechneProzente(23, (100.0/541.0)), 0.05);
	}
	
	@Test
	public void testeProzentRechnung3(){
		assertEquals(TrackingChart.berechneProzente(0, (100.0/300.0)), 0, 0.05);
	}
}
