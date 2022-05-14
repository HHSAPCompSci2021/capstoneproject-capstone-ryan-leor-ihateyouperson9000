/**
 * Represents a PApplet that draws the stock chart and UI
 * 
 * Author: Ryan Xu
 * Version: 5/6/22
 */
import java.awt.Point;   
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Scanner;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.Fetcher;
import com.crazzyghost.alphavantage.UrlExtractor;
import com.crazzyghost.alphavantage.fundamentaldata.response.BalanceSheetResponse;
import com.crazzyghost.alphavantage.fundamentaldata.response.EarningsResponse;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.TimeSeries;
import com.crazzyghost.alphavantage.timeseries.request.DailyRequest;
import com.crazzyghost.alphavantage.timeseries.request.TimeSeriesRequest;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import processing.core.PApplet;
import processing.core.PImage;
import rxu770.shapes.Line;


/**
 * Represents the surface that the Program is run on
 * @author Leor Porat, Ryan Xu
 *
 */
public class DrawingSurface extends PApplet {

	
	private Button lineButton;
	private Button eraserButton;
	private Button pointerButton;
	private Button boxButton;
	private Button calculateDCF;
	private TextBox ticker;
	private ArrayList<StockUnit> data;
	private PImage line, rectangle, eraser, cursor, calculator;
	private Config cfg;
	private TimeSeries stockTimeSeries = new TimeSeries(null);
	
	private boolean dataGood;
	
	/**
	 * Creates a DrawingSurface object
	 */
	public DrawingSurface() {
		dataGood = false;
		
		cfg = Config.builder()
			    .key("K3GVRKJIDYNUZPZM")
			    .timeOut(10)
			    .build();
		AlphaVantage.api().init(cfg);
		
		getData();
	}
	
	
	public void getData() {
//		String expected = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&datatype=json&apikey=demo";
//        TimeSeriesRequest request = new DailyRequest.Builder()
//            .forSymbol("IBM")
//            .dataType(DataType.JSON)
//            .build();
//        assertEquals(expected, Config.BASE_URL + UrlExtractor.extract(request) + "demo");  

		 AlphaVantage.api()
		    .timeSeries()
		    .daily()
		    .forSymbol("AAPL")
		    .outputSize(OutputSize.FULL)
		    .onSuccess(e->handleSuccess(e))
		    .onFailure(e->handleFailure(e))
		    .fetch();
		 
		 System.out.println("Ran");
	}
	
	public void handleSuccess(Object e) {
		System.out.println("Run");
	    data = (ArrayList<StockUnit>) ((TimeSeriesResponse) e).getStockUnits();
	    dataGood = true;
	}
	public void handleFailure(AlphaVantageException error) {
	    System.out.println("API Failed in getData()");
	}
	
	
	/**
	 * Executes when the program begins
	 */
	public void setup() {
		// line = this.loadImage("line.png");
		// rectangle = this.loadImage("rectangle.png");
		// eraser = this.loadImage("eraser.png");
		// cursor = this.loadImage("cursor.png");
		// calculator = this.loadImage("calculator.png");
		
		lineButton = new Button(0, 0, 50, 50, "line.png", this);
		boxButton = new Button(0, 150, 50, 50, "rectangle.png", this);
		eraserButton = new Button(0, 50, 50, 50, "eraser.png", this);
		pointerButton = new Button(0, 100, 50, 50, "cursor.png", this);
		calculateDCF = new Button(0, 200, 50, 50, "calculator.png", this);
		
		
	}
	
	/**
	 * Executed repetitively until the program is stopped.
	 */
	public void draw() { 
		background(255);   // Clear the screen with a white background
		fill(0);
		textAlign(LEFT);
		textSize(12);
		lineButton.draw(this);
		eraserButton.draw(this);
		pointerButton.draw(this);
		boxButton.draw(this);
		calculateDCF.draw(this);
	
		if (dataGood) {
			for (int e=0; e<data.size(); e+=2) {
				int year1 = parseInt(data.get(e).getDate().substring(0,4));
				int month1 = parseInt(data.get(e).getDate().substring(5,7));
				int day1 = parseInt(data.get(e).getDate().substring(8,10));
				int year2 = parseInt(data.get(e+1).getDate().substring(0,4));
				int month2 = parseInt(data.get(e+1).getDate().substring(0,4));
				int day2 = parseInt(data.get(e+1).getDate().substring(0,4));
	//			Line l = new Line(data.get(e).getDate(), data.get(e).getClose(), data.get(e+1).getDate(), data.get(e+1).getClose());
			}
		}
	}
	
	private void draw(ArrayList<StockUnit> data) {
		
	}
	
	/**
	 * Returns the date and close price corresponding to the point clicked
	 * 
	 * @param p A Point object containing a graphical pixel coordinate.
	 * @param x The x pixel coordinate of the upper left corner of the frame.
	 * @param y The y pixel coordinate of the upper left corner of the grid frame.
	 * @param width The pixel width of the frame.
	 * @param height The pixel height of the frame.
	 * @return the date and close price corresponding to the point clicked
	 */
	public Point clickToDate(Point p, float x, float y, float width, float height) {

		int j = 0;
		int i = 0;
		
		Point result = new Point(i, j);
		return result;
	}
	
	/**
	 * Saves the coordinate that was clicked by the mouse
	 */
	public void mousePressed() {
		if (mouseButton == LEFT) {
			if (lineButton.getBorder().isPointInside(mouseX, mouseY)) {
				if (lineButton.isPressed()) {
					lineButton.unpress();
				} else {
					lineButton.press();
				}
			}
			if (eraserButton.getBorder().isPointInside(mouseX, mouseY)) {
				if (eraserButton.isPressed()) {
					eraserButton.unpress();
				} else {
					eraserButton.press();
				}
			}
			if (pointerButton.getBorder().isPointInside(mouseX, mouseY)) {
				if (pointerButton.isPressed()) {
					pointerButton.unpress();
				} else {
					pointerButton.press();
				}
			}
			if (boxButton.getBorder().isPointInside(mouseX, mouseY)) {
				if (boxButton.isPressed()) {
					boxButton.unpress();
				} else {
					boxButton.press();
				}
			}
			if (ticker.getBorder().isPointInside(mouseX, mouseY)) {
				Scanner reader = new Scanner(System.in);
				String n = reader.next();
				ticker.setString(n);
			}
		} 
	}
	
	/**
	 * Saves the string that was typed
	 */
	public void keyPressed() {
		
	}
	
	public void mouseDragged() {
		if (eraserButton.isPressed()) {
			this.stroke(255);
			this.circle(mouseX, mouseY, 5);
		} else if (lineButton.isPressed()) {
			Point first = new Point(mouseX, mouseY);
			
		}
	}
	
	

	
}










