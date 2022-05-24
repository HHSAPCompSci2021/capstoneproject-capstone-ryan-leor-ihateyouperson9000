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
import processing.event.MouseEvent;
import shapes.Line;
import shapes.Rectangle;
import shapes.Shape;



/**
 * Represents the surface that the Program is run on
 * @author Leor Porat, Ryan Xu
 *
 */
public class DrawingSurface extends PApplet {
	private DcfCalculator dcf;
	private GImageButton eraserButton;
	private GImageButton lineButton;
	private GImageButton pointerButton;
	private GImageButton rectangleButton;
	private GTextArea tickerBox;
	private GTextArea timeBox;
	private GTextField tickerDisplay;
	private GTextField tickerInstructions;
	private GTextField timeInstructions;
	private GTextField dcfCalculate;
	private GTextField clickToDateVals;
	private String[] eraserFiles;
	private String[] lineFiles;
	private String[] cursorFiles;
	private String[] rectangleFiles;
	private StockChart chart;
	private Point pointOne, pointTwo;
	private Rectangle outerFrame;
	private ArrayList<Shape> shapes;
	private boolean tickerSet;
	private boolean rectActive;
	private boolean lineActive;
	private int pointCount;
	
	private final int FIVE_Y, ONE_Y, SIX_M, THREE_M, ONE_M, FIVE_D, ONE_D; //might be able to use interval.java instead
	
	/**
	 * Creates a DrawingSurface object
	 */
	public DrawingSurface() {
		dcf = new DcfCalculator(); //dcf needs to be instantiated before chart
		chart = new StockChart(150, 125, 600, 525);
		// outerFrame = new Rectangle(150, 125, 600, 525);
		pointOne = null;
		pointTwo = null;
		shapes = new ArrayList<Shape>();
		tickerSet = false;
		rectActive = false;
		lineActive = false;
		pointCount = 0;
		
		FIVE_Y = 0; //one data point per week
		ONE_Y = 365; //one data point per day
		SIX_M = 0; //one data point per 2hr
		THREE_M = 0; //one data point per 1hr
		ONE_M = 0; //one data point per 30min
		FIVE_D = 0; //one data point per 5min
		ONE_D = 0; //one data point per 1min	
		
		
	}
	
	/**
	 * Executes when the program begins
	 */
	public void setup() {
		eraserFiles = new String[]{"eraser.png", "eraser_HOVER.png"};
		lineFiles = new String[]{"line.png", "line_HOVER.png"};
		cursorFiles = new String[]{"cursor.png", "cursor_HOVER.png"};
		rectangleFiles = new String[]{"rectangle.png", "rectangle_HOVER.png"};
		eraserButton = new GImageButton(this, 0, 125, 150, (float)(chart.getFrame().getHeight()/4.0), eraserFiles);
		lineButton = new GImageButton(this, 0, (float)(125+131.25), 150, (float)(chart.getFrame().getHeight()/4.0), lineFiles);
		pointerButton = new GImageButton(this, 0, (float)(125+(2.0*(chart.getFrame().getHeight()/4.0))), 150, (float)(chart.getFrame().getHeight()/4.0), cursorFiles);
		rectangleButton = new GImageButton(this, 0, (float)(125+(3.0*(chart.getFrame().getHeight()/4.0))), 150, (float)(chart.getFrame().getHeight()/4.0), rectangleFiles);
		
		
		
		tickerBox = new GTextArea(this, 0, 20, 75, 105);
		timeBox = new GTextArea(this, 75, 20, 75, 105);
		clickToDateVals = new GTextField(this, 150, 0, 350, 125);
		tickerInstructions = new GTextField(this, 0, 0, 75, 20);
		timeInstructions = new GTextField(this, 75, 0, 75, 20);
		tickerDisplay = new GTextField(this, 150, 125, 120, 20);
		dcfCalculate = new GTextField(this, 500, 0, 250, 50);
	}
	
	/**
	 * Executed repetitively until the program is stopped.
	 */
	public void draw() {
//		System.out.println(dcf.getApi().getIndex());
		
		tickerBox.setPromptText("choose ticker");
		timeBox.setPromptText("choose time");
		tickerInstructions.setText("Set ticker");
		timeInstructions.setText("Set time");
		
		background(255);
		fill(0);
		textAlign(LEFT);
		textSize(12);
		stroke(255);
		// tickerDisplay.setWrapWidth(tickerDisplay.getWrapWidth());
		chart.drawFrame(this);
		chart.drawGraph(this);
		drawAxes();
		
		if (!tickerSet) {
			// outerFrame.draw(this);
			tickerDisplay.setText("CHOOSE TICKER");
		} else {
			tickerDisplay.setText(chart.getTicker() + " for " + chart.getTimeSpan() + " days");
			dcfCalculate.setPromptText("Estimated Share Price: " + String.format("%.2f", dcf.calcEstSharePrice()));
		}
		drawShapes();
		

		
	}

	/**
	 * Called if a button is pressed
	 * @param button the button that is pressed
	 * @param event what is done to the button
	 */
	public void handleButtonEvents(GImageButton button, GEvent event) {
		if (button.getY() == 125) { // ERASER BUTTON
			if (shapes.size() > 0) {
				shapes.get(shapes.size()-1).setStrokeColors(0,  0,  0);
				shapes.get(shapes.size()-1).draw(this);
				shapes.remove(shapes.size()-1);
				chart.update();
				for (int i = 0; i < shapes.size(); i++) {
					shapes.get(i).draw(this);
				}
			} else {
				System.out.println("NOTHING TO ERASE");
			}
		} else if (button.getY() == (float)(125+(chart.getFrame().getHeight()/4.0))) { // LINE BUTTON
			if (!lineActive) {
				lineActive = true;
				rectActive = false;
			} else {
				lineActive = false;
			}
			System.out.println("LINEACTIVE: " + lineActive);
		} else if (button.getY() == (float)(125+(2.0*(chart.getFrame().getHeight()/4.0)))) { // CURSOR BUTTON
			rectActive = false;
			lineActive = false;
			System.out.println("LINEACTIVE: " + lineActive);
			System.out.println("RECTACTIVE: " + rectActive);
		} else if (button.getY() == (float)(125+(3.0*(chart.getFrame().getHeight()/4.0)))) { // RECTANGLE BUTTON
			if (!rectActive) {
				rectActive = true;
				lineActive = false;
			} else {
				rectActive = false;
			}
			System.out.println("RECTACTIVE: " + rectActive);
		}
	}
	
	/**
	 * Called if a textbox is interacted with
	 * @param textcontrol the text in the box
	 * @param event what is done to the text in the box
	 */
	public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) {
		if (event == GEvent.ENTERED) {
			try {
				int input = Integer.parseInt(textcontrol.getText());
				System.out.println(input);
				chart.setTimeSpan(input);
				chart.update();
			} catch (NumberFormatException e) {
				chart.setTicker(textcontrol.getText());
				tickerSet = true;
				chart.getData();
//				dcf.getData();
				
			} finally {
				// textcontrol.
			}
		}
	}
	
	/**
	 * Called if the mouse is pressed
	 */
	public void mousePressed() {
		if (150 < mouseX && mouseX < 750 && 125 < mouseY && mouseY < 650 && !rectActive && !lineActive) {
			System.out.println("RECT NOR LINE ACTIVE");
			Line l = new Line(mouseX, 125, mouseX, (double)650); // WORKS
			l.setStrokeColors(255, 255, 255);
			//l.draw(this);
			Line intersecting = null;
			String date = "";
			double val = 0;
			double xDif = Math.abs(chart.getLines().get(0).getX2()-chart.getLines().get(0).getX());
				for (int i = 0; i < chart.getLines().size(); i++) {
					if (l.intersects(chart.getLines().get(i)) && Math.abs(l.getX()-chart.getLines().get(i).getX()) < xDif) {
						System.out.println(i);
						intersecting = chart.getLines().get(i);
						date = chart.getStockData().get(chart.getNumDataPoints()-i).getDate();
						val = chart.getStockData().get(chart.getNumDataPoints()-i).getClose();
					}
				}
			if (intersecting != null) {
				System.out.println("LINES INTERSECTED");
			}
			System.out.println(intersecting.getX());
		 	Line drawn = new Line(intersecting.getX(), 125, intersecting.getX(), (double)650);
			drawn.setStrokeColors(255, 255, 255);
			drawn.draw(this);
			clickToDateVals.setText("DATE SELECTED: " + date + " VALUE: $" + val);
			
			
		} else if (rectActive && 150 < mouseX && mouseX < 750 && 125 < mouseY && mouseY < 650 && !lineActive) {
			if (pointCount == 0) {
				pointOne = new Point(mouseX, mouseY);
				pointCount++;
			} else if (pointCount == 1) {
				pointTwo = new Point(mouseX, mouseY);
				if (pointTwo.x < pointOne.x) {
					int temp = pointOne.x;
					pointOne.x = pointTwo.x;
					pointTwo.x = temp;
				}
				if (pointTwo.y < pointOne.y) {
					int temp = pointOne.y;
					pointOne.y = pointTwo.y;
					pointTwo.y = temp;
				}
				Rectangle rect = new Rectangle(pointOne.x, pointOne.y, pointTwo.x-pointOne.x, pointTwo.y-pointOne.y);
				rect.setStrokeColors(255,  255,  255);
				rect.removeFill();
				rect.draw(this);
				shapes.add(rect);
				pointCount = 0;
				pointOne = null;
				pointTwo = null;
			}
			
		} else if (lineActive && 150 < mouseX && mouseX < 750 && 125 < mouseY && mouseY < 650 && !rectActive) {
			if (pointCount == 0) {
				pointOne = new Point(mouseX, mouseY);
				pointCount++;
			} else if (pointCount == 1) {
				pointTwo = new Point(mouseX, mouseY);
				Line l = new Line(pointOne.x, pointOne.y, pointTwo.x, (double)pointTwo.y);
				l.setStrokeColors(255,  255,  255);
				l.draw(this);
				shapes.add(l);
				pointCount = 0;
				pointOne = null;
				pointTwo = null;
			}
		}
	}
	
	/**
	 * Called if the mouse wheel is moved
	 */
	public void mouseWheel(MouseEvent event) {
		if (50 < mouseX && mouseX < 650 && 50 < mouseY && mouseY < 525 && chart.getNumDataPoints() >= 1) {
			float e = event.getCount();
			int sizeAmount = (int)e;
			chart.setNumDataPoints(chart.getNumDataPoints()+sizeAmount);
			chart.setTimeSpan(chart.getTimeSpan()+sizeAmount);
			chart.update();
		}
	}
	
	/**
	 * Called if a key is pressed
	 */
	public void keyPressed() {
		
	}
	
	public void drawShapes() {
		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw(this);
		}
	}
	
	public void drawAxes() {
		int yDiff = chart.getMaxYDrawn()-chart.getMinYDrawn();
		double increment = yDiff/5.0;
		for (int i = 0; i < 6; i++) {
			Line l = new Line(150, chart.getMinYDrawn()+(i*increment), 750, chart.getMinYDrawn()+(i*increment));
			// System.out.println(chart.getMaxYDrawn());
//			System.out.println(chart.getMaxYDrawn());
			// System.out.println(chart.getMaxYDrawn()-(i*increment) + ": " + i);
			l.setStrokeColors(255, 255, 255);
			l.draw(this);
		}
	}
}










