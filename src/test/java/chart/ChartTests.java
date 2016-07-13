package chart;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ChartTests {

	@Test
	public void testeProzentRechung(){
		TrackingChart tc = new TrackingChart();
		assertEquals(tc.berechneProzente(25, (100.0/100.0)), 25.0, 0.05);
	}

	@Test
	public void testeProzentRechnung2(){
		TrackingChart tc = new TrackingChart();
		assertEquals(4.251 ,tc.berechneProzente(23, (100.0/541.0)), 0.05);
	}

	@Test
	public void testeProzentRechnung3(){
		TrackingChart tc = new TrackingChart();
		assertEquals(tc.berechneProzente(0, (100.0/300.0)), 0, 0.05);
	}
}
