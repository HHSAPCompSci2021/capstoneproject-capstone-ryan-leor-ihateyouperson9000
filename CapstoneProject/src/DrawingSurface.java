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
	private DcfCalculator dcf;
	private GImageButton eraserButton;
	private GImageButton lineButton;
	private GImageButton cursorButton;
	private GImageButton rectangleButton;
	private GTextArea tickerBox;
	private GTextArea timeBox;
	private GTextField tickerDisplay;
	private GTextField tickerInstructions;
	private GTextField timeInstructions;
	private String[] eraserFiles;
	private String[] lineFiles;
	private String[] cursorFiles;
	private String[] rectangleFiles;
	private StockChart chart;
	private final int FIVE_Y, ONE_Y, SIX_M, THREE_M, ONE_M, FIVE_D, ONE_D; //might be able to use interval.java instead
	
	/**
	 * Creates a DrawingSurface object
	 */
	public DrawingSurface() {
		
		configure();
		
		chart = new StockChart();
		chart.setTicker("AAPL");
		
		dcf = new DcfCalculator();
		
		FIVE_Y = 0; //one data point per week
		ONE_Y = 365; //one data point per day
		SIX_M = 0; //one data point per 2hr
		THREE_M = 0; //one data point per 1hr
		ONE_M = 0; //one data point per 30min
		FIVE_D = 0; //one data point per 5min
		ONE_D = 0; //one data point per 1min		
	}
	
	public void configure() {
		Config cfg = Config.builder()
			    .key("K3GVRKJIDYNUZPZM")
			    .timeOut(10)
			    .build();
		AlphaVantage.api().init(cfg);
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
		
		tickerBox = new GTextArea(this, 650, 20, 100, 50);
		timeBox = new GTextArea(this, 650, 90, 100, 50);
		tickerInstructions = new GTextField(this, 650, 0, 100, 20);
		timeInstructions = new GTextField(this, 650, 70, 100, 20);
		tickerDisplay = new GTextField(this, 325, 100, 100, 20);

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
		fill(0);
		textAlign(LEFT);
		textSize(12);
		
		tickerDisplay.setText(chart.getTicker() + " for " + chart.getNumDataPoints() + " days");
		tickerInstructions.setText("Set ticker");
		timeInstructions.setText("Set time");
		
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
			try {
				int intCheck = Integer.parseInt(textcontrol.getText());
				chart.setNumDataPoints(intCheck);
				chart.update(this);
			} catch (NumberFormatException e) {
				chart.setTicker(textcontrol.getText());
				chart.getData();
				chart.update(this);
			}
			
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










