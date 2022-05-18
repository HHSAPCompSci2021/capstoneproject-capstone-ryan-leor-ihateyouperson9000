import java.util.ArrayList;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import processing.core.PApplet;
import rxu770.shapes.Line;
import rxu770.shapes.Rectangle;

public class StockChart {

	private Ticker ticker;
	private Config cfg;
	private Rectangle frame;
	private ArrayList<StockUnit> data;
	private int numDataPoints;
	private double minY, maxY;
	private boolean dataGood;
	
	public StockChart() {
		ticker = new Ticker();
		ticker.setTicker("AAPL");
		dataGood = false;
		numDataPoints = 261;
		frame = new Rectangle(50, 50, 600, 525);
		getData();
	}
	
	public void getData() {
			 AlphaVantage.api()
			    .timeSeries()
			    .daily() //change based on timespan
			    .forSymbol(ticker.getTicker())
			    .outputSize(OutputSize.FULL)
			    .onSuccess(e->handleSuccess(e))
			    .onFailure(e->handleFailure(e))
			    .fetch();
	}
	
	/**
	 * Instructions for what to do if the API was successfully fetched
	 * 
	 * @param e the object passed from onSuccess() in the api
	 */
	private void handleSuccess(Object e) {
	    data = (ArrayList<StockUnit>) ((TimeSeriesResponse) e).getStockUnits();
	    dataGood = true;
	}
	
	/**
	 * Instructions for what to do if the API was unsuccessfully fetched
	 * 
	 * @param error the error that occurred
	 */
	private void handleFailure(AlphaVantageException error) {
	    System.out.println(error.toString());
	}
	
	public void update(PApplet drawer) {
		drawer.background(255);
		drawer.stroke(0);
		frame.draw(drawer);
		drawer.stroke(255);
		if (dataGood) {
			findMinMax();
			for (int e = numDataPoints; e > 0; e--) { //261 days of stock trading per year
				
				double x1 = 650-(double)e*(frame.getWidth()/numDataPoints); //300 to give space for buttons on left side
				double y1 = frame.getHeight()-(data.get(e).getClose()-minY)/(maxY-minY)*300; //575 is the max y val of the jframe
				double x2 = 650-(double)e*(frame.getWidth()/numDataPoints)+(frame.getWidth()/numDataPoints);
				double y2 = frame.getHeight()-(data.get(e-1).getClose()-minY)/(maxY-minY)*300;				
				
				Line l = new Line(x1, y1, x2, y2);
				l.setStrokeColors(255,255,255);
				l.draw(drawer);
			}
			
		}
		
	}
	
	public void findMinMax() { //only for 1 year
		
		minY = data.get(0).getClose();
		maxY = 0;
		
		for (int e = numDataPoints; e > 0; e--) { 
			
			if (data.get(e).getClose() < minY) {
				minY = data.get(e).getClose();
			}
			if (data.get(e).getClose() > maxY) {
				maxY = data.get(e).getClose();
			}
		}
		
	}
	
	public ArrayList<StockUnit> getStockData() {
		return data;
	}
	
	public int getNumDataPoints() {
		return numDataPoints;
	}
	
	public void setNumDataPoints(int n) {
		numDataPoints = n;
	}
	
	public void setTicker(String s) {
		ticker.setTicker(s);
	}
	
	public String getTicker() {
		return ticker.getTicker();
	}
	
	
	
}
