/**
 * Creates a new StockChart, which takes data from an API and uses it to draw a Line Graph of stock data based on which Ticker is selected
 * 
 * Author: Leor Porat
 * Version: 5/19/22
 */
import java.util.ArrayList;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import processing.core.PApplet;
import shapes.Line;
import shapes.Rectangle;

public class StockChart {

	private Ticker ticker;
	private Rectangle frame;
	private ArrayList<StockUnit> data;
	private ArrayList<Line> lines;
	private int numDataPoints;
	private double minY, maxY;
	private int timespan;
	private ApiConnector api;

	/**
	 * Creates a new StockChart
	 * @param x the x of the top left corner of the StockChart
	 * @param y the y of the top left corner of the StockChart
	 * @param width the width of the StockChart
	 * @param height the height of the StockChart
	 */
	public StockChart(double x, double y, double width, double height) {
		api = new ApiConnector();
		api.incrementKey();
		api.configure();
		ticker = new Ticker();
		lines = new ArrayList<Line>();
		numDataPoints = 261; //default 1 yr timespan
		frame = new Rectangle(x, y, width, height); //50 50 600 525
		timespan = 365;
	}

	/**
	 * Gets data from the API based on which Ticker is selected
	 */
	public void getData() {
		System.out.println("getData()");
		api.getAlpha()
		.timeSeries()
		.daily()
		.forSymbol(ticker.getTicker())
		.outputSize(OutputSize.FULL)
		.onSuccess(e->handleSuccess(e))
		.onFailure(e->handleFailure(e))
		.fetch();
	}

	/**
	 * Instructions for what to do if the API was successfully fetched
	 * @param e the object passed from onSuccess() in the api
	 */
	private void handleSuccess(Object e) {
		data = (ArrayList<StockUnit>) ((TimeSeriesResponse) e).getStockUnits();
		if (data.size() > 0)
			update();
	}

	/**
	 * Instructions for what to do if the API was unsuccessfully fetched
	 * @param error the error that occurred
	 */
	private void handleFailure(AlphaVantageException error) {
		System.out.println("CHART: " + error.toString());
		api.incrementKey();
		getData();
	}

	/**
	 * Draws the StockChart based on the current ticker and numDataPoints
	 */
	public void update() {
		lines.clear();

		findMinMax();
		for (int e = numDataPoints; e > 0; e--) { 

			if (data.get(e).getClose() >= 0) {
				// 150, 125, 600, 525
				double x1 = 750-(double)e*(frame.getWidth()/numDataPoints);
				double y1 = frame.getHeight()-(data.get(e).getClose()-minY)/(maxY-minY)*300;
				double x2 = 750-(double)e*(frame.getWidth()/numDataPoints)+(frame.getWidth()/numDataPoints);
				double y2 = frame.getHeight()-(data.get(e-1).getClose()-minY)/(maxY-minY)*300;				
				Line l = new Line(x1, y1, x2, y2);
				lines.add(l);
			}

		}
		
		// drawAxes();

	}

	/**
	 * Finds and sets maximum and minimum y of the StockChart
	 */
	public void findMinMax() { 

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

	public void drawGraph(PApplet app) {
		for (int i=0; i<lines.size(); i++) {
			lines.get(i).setStrokeColors(255,255,255);
			lines.get(i).draw(app);
		}
	}

	public void drawFrame(PApplet app) {
		app.stroke(0);
		frame.draw(app);
	}

	/**
	 * Gets ArrayList of data from the API
	 * @return ArrayList of StockUnits containing the stock data from the API
	 */
	public ArrayList<StockUnit> getStockData() {
		return data;
	}

	/**
	 * Gets number of trading days (5 days/week) shown on the StockChart
	 * @return number of data points
	 */
	public int getNumDataPoints() {
		return numDataPoints;
	}

	/**
	 * Sets the number of data points that will be shown on the StockChart
	 * @param n number of data points to be set
	 */
	public void setNumDataPoints(int n) {
		if (n > 0) {
			numDataPoints = n;
		}
	}

	/**
	 * Gets the timespan displayed by the StockChart
	 * @return the timespan of the StockChart
	 */
	public int getTimeSpan() {
		return timespan;
	}

	/**
	 * Sets the timespan of the StockChart
	 * @param n days
	 */
	public void setTimeSpan(int n) {
		if (n > 0) {
			timespan = n;
			numDataPoints = (int)((double)n /365.0*261.0);
		}
	}

	/**
	 * Sets the Ticker to extract data from
	 * @param s name of Ticker to set
	 */
	public void setTicker(String s) {
		ticker.setTicker(s);
	}

	/**
	 * Gets the Ticker that is extracting data
	 * @return name of the Ticker
	 */
	public String getTicker() {
		return ticker.getTicker();
	}

	/**
	 * Returns the closing value of the current Ticker at the inputed date
	 * @param year the year of the date
	 * @param month the month of the date
	 * @param day the day of the date
	 * @return closing value of the stock at the inputed date
	 */
	public double getValAtTime(String year, String month, String day) {

		String s = year+"-"+month+"-"+day;
		for (int e = numDataPoints; e > 0; e--) {
			if (data.get(e).getDate().equals(s)) {
				return data.get(e).getClose();
			}
		}
		return -1;
	}

	/**
	 * Gets closing value from an inputed element of the data ArrayList
	 * @param n the element of the ArrayList to get data from
	 * @return the closing value at the specified element
	 */
	public double getSpecificVal(int n) {
		return data.get(n).getClose();
	}

	/**
	 * Returns the Rectangle that the StockChart is confined to
	 * @return Rectangle around StockChart
	 */
	public Rectangle getFrame() {
		return frame;
	}

	public ArrayList<Line> getLines() {
		return lines;
	}

	public ApiConnector getApi() {
		return api;
	}
	
	public void drawAxes(PApplet p) {
		double maxYDrawn = 0;
		double minYDrawn = 0;
		for (int i = 0; i < lines.size(); i++) {
			if (data.get(i).getClose() == maxY) {
				maxYDrawn = lines.get(numDataPoints-i).getY2();
			}
		}
		for (int i = 0; i < lines.size(); i++) {
			if (data.get(i).getClose() == minY) {
				minYDrawn = lines.get(numDataPoints-i).getY2();
			}
		}
		double yDrawnDiff = maxYDrawn-minYDrawn;
		double yDiff = maxY-minY;
		double yIncrement = yDrawnDiff/5.0;
		double valIncrement = yDiff/5.0;
		for (int i = 0; i < 5; i++) {
			p.text(maxY-(i*valIncrement)+"", 150, (float)(maxYDrawn-(i*yIncrement)));
			System.out.println(maxY-(i*valIncrement)+"");
		}
		
	}
}
