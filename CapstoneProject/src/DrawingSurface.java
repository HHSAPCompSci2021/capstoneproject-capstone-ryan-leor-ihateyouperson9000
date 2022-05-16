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
	private String tickerSymbol;
	private ArrayList<StockUnit> data;
	private int timespan;
	private final int FIVE_Y, ONE_Y, SIX_M, THREE_M, ONE_M, FIVE_D, ONE_D;
	private PImage line, rectangle, eraser, cursor, calculator;
	private Config cfg;
	private TimeSeries stockTimeSeries;
	
	private boolean dataGood;
	
	/**
	 * Creates a DrawingSurface object
	 */
	public DrawingSurface() {
		stockTimeSeries = new TimeSeries(null);
		dataGood = false;
		FIVE_Y = 0; //one data point per week
		ONE_Y = 365; //one data point per day
		SIX_M = 0; //one data point per 2hr
		THREE_M = 0; //one data point per 1hr
		ONE_M = 0; //one data point per 30min
		FIVE_D = 0; //one data point per 5min
		ONE_D = 0; //one data point per 1min
		timespan = ONE_Y;
		
		tickerSymbol = "AAPL";
		
		
		cfg = Config.builder()
			    .key("K3GVRKJIDYNUZPZM")
			    .timeOut(10)
			    .build();
		AlphaVantage.api().init(cfg);
		
		getData();
	}
	
	/**
	 * Sets up parameters for data from the API
	 */
	public void getData() {
		 AlphaVantage.api()
		    .timeSeries()
		    .daily() //change based on timespan
		    .forSymbol(tickerSymbol)
		    .outputSize(OutputSize.FULL)
		    .onSuccess(e->handleSuccess(e))
		    .onFailure(e->handleFailure(e))
		    .fetch();
	}
	
	public void handleSuccess(Object e) {
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
//		lineButton.draw(this);
//		eraserButton.draw(this);
//		pointerButton.draw(this);
//		boxButton.draw(this);
//		calculateDCF.draw(this);
	
	
		
		if (dataGood) {
		
		/*
			for (int e=0; e<data.size()-1; e++) {
				
				StockUnit[] units = new StockUnit[365]; //change based on timespan
				for (int i=0; i<365; i++) {
					units[i] = data.get(i);
				}
				
		*/	
			// shows graph data for one year
			for (int e = 365; e > 0; e--) {
				System.out.println("RAN");
				double x1 = (double)e*(500.0/365); //500 is length of jframe
				double y1 = data.get(e).getClose();
				double x2 = (double)e*(500.0/365)+1.0;
				double y2 = data.get(e-1).getClose();
				
				Line l = new Line(x1, y1, x2, y2);
				l.draw(this);
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
	 * Runs if a key is pressed
	 */
	public void keyPressed() {
		
	}
	
	/**
	 * Runs if user's mouse is held and dragged across the screen
	 */
	public void mouseDragged() {
		if (eraserButton.isPressed()) {
			this.stroke(255);
			this.circle(mouseX, mouseY, 5);
		} else if (lineButton.isPressed()) {
			Point first = new Point(mouseX, mouseY);
			
		}
	}
	
	

	
}










