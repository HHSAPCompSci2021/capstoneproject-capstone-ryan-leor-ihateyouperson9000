/**
 * Represents a PApplet that draws the stock chart and UI
 * 
 * Author: Leor Porat, Ryan Xu
 * Version: 5/6/22
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.TimeSeries;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import g4p_controls.GButton;
import g4p_controls.GEditableTextControl;
import g4p_controls.GEvent;
import g4p_controls.GImageButton;
import g4p_controls.GTextArea;
import g4p_controls.GTextField;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import rxu770.shapes.Line;
import rxu770.shapes.Rectangle;



/**
 * Represents the surface that the Program is run on
 * @author Leor Porat, Ryan Xu
 *
 */
public class DrawingSurface extends PApplet {

	private AlphaVantageConnector alpha;
	private GImageButton eraserButton;
	private GImageButton lineButton;
	private GImageButton cursorButton;
	private GImageButton rectangleButton;
	private GTextArea tickerBox;
	private GTextArea timeBox;
	private GTextField tickerDisplay;
	private String[] eraserFiles;
	private String[] lineFiles;
	private String[] cursorFiles;
	private String[] rectangleFiles;
	private Rectangle frame;
	private ArrayList<StockUnit> data;
	private int timespan;
	private int numDataPoints;
	private final int FIVE_Y, ONE_Y, SIX_M, THREE_M, ONE_M, FIVE_D, ONE_D; //might be able to use interval.java instead
	//private PImage line, rectangle, eraser, cursor, calculator;
	int a = 0;
	
	//private TimeSeries stockTimeSeries;
	private double minY, maxY;
	private boolean dataGood;
	
	/**
	 * Creates a DrawingSurface object
	 */
	public DrawingSurface() {
		
		alpha = new AlphaVantageConnector();
		dataGood = false;
		FIVE_Y = 0; //one data point per week
		ONE_Y = 365; //one data point per day
		SIX_M = 0; //one data point per 2hr
		THREE_M = 0; //one data point per 1hr
		ONE_M = 0; //one data point per 30min
		FIVE_D = 0; //one data point per 5min
		ONE_D = 0; //one data point per 1min
		timespan = ONE_Y;
		numDataPoints = 261;
		
		alpha.setTicker("AAPL");
		
		getData();
	}
	
	/**
	 * Sets up parameters for data from the API
	 */
	public void getData() {
		 AlphaVantage.api()
		    .timeSeries()
		    .daily() //change based on timespan
		    .forSymbol(alpha.getTicker())
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
	
	
	/**
	 * Executes when the program begins
	 */
	public void setup() {
		eraserFiles = new String[]{"eraser.png"};
		lineFiles = new String[]{"line.png"};
		cursorFiles = new String[]{"cursor.png"};
		rectangleFiles = new String[]{"rectangle.png"};
		eraserButton = new GImageButton(this, 0, 0, 50, 50, eraserFiles);
		lineButton = new GImageButton(this, 0, 50, 50, 50, lineFiles);
		cursorButton = new GImageButton(this, 0, 100, 50, 50, cursorFiles);
		rectangleButton = new GImageButton(this, 0, 150, 50, 50, rectangleFiles);
		tickerBox = new GTextArea(this, 650, 0, 100, 50);
		timeBox = new GTextArea(this, 650, 50, 100, 50);
		tickerDisplay = new GTextField(this, 325, 100, 100, 20);
		
		frame = new Rectangle(50, 50, 600, 525);
	}
	
	public void handleButtonEvents(GImageButton button, GEvent event) {
		if (button.isEnabled()) {
			System.out.println("ENABLED");
		} else {
		}
		// getData();
	}
	
	/**
	 * Executed repetitively until the program is stopped.
	 */
	public void draw() { 
		background(255);   // Clear the screen with a white background
		fill(0);
		textAlign(LEFT);
		textSize(12);
		frame.draw(this);
		
		tickerDisplay.setText(alpha.getTicker() + " for " + timespan + " days");
		
//		lineButton.draw(this);
//		eraserButton.draw(this);
//		pointerButton.draw(this);
//		boxButton.draw(this);
//		calculateDCF.draw(this);
		
		if (dataGood) {
			
			findMinMax();
			for (int e = numDataPoints; e > 0; e--) { //261 days of stock trading per year
				
				double x1 = 650-(double)e*(600.0/numDataPoints); //300 to give space for buttons on left side
				double y1 = 525-(data.get(e).getClose()-minY)/(maxY-minY)*300; //575 is the max y val of the jframe
				double x2 = 650-(double)e*(600.0/numDataPoints)+(600.0/numDataPoints);
				double y2 = 525-(data.get(e-1).getClose()-minY)/(maxY-minY)*300;				
				
				Line l = new Line(x1, y1, x2, y2);
				l.setStrokeColors(255,255,255);
				l.draw(this);
			}
			
		}
		
	}
	
	/**
	 * Finds the min and maximum stock prices in the desired span
	 * 
	 * @post sets minY to the minimum stock price of the interval
	 * @post sets maxY to the maximum stock price of the interval
	 */
	private void findMinMax() { //only for 1 year
		
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

	}
	
	/**
	 * Runs if a key is pressed
	 */
	public void keyPressed() {
		
	}
	
	public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) {
		if (event == GEvent.ENTERED) {
			// System.out.println("YO");
			alpha.setTicker(textcontrol.getText());
			numDataPoints = parseInt(textcontrol.getText());
			getData();
		} 
	}
	
	/**
	 * Runs if user's mouse is held and dragged across the screen
	 */
	public void mouseDragged() {
		/*
		if (eraserButton.isPressed()) {
			this.stroke(255);
			this.circle(mouseX, mouseY, 5);
		} else if (lineButton.isPressed()) {
			Point first = new Point(mouseX, mouseY);
			
		}
		*/
	}

	
}










