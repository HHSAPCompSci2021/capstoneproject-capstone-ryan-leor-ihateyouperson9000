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

import processing.core.PApplet;
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
	private ArrayList<Line> chart;
	
	/**
	 * Creates a DrawingSurface object
	 */
	public DrawingSurface() {
		lineButton = new Button(0, 0, 50, 50, "line.png", this);
		boxButton = new Button(0, 150, 50, 50, "rectangle.png", this);
		eraserButton = new Button(0, 50, 50, 50, "eraser.png", this);
		pointerButton = new Button(0, 100, 50, 50, "cursor.png", this);
		chart = new ArrayList<Line>();
	}
	
	/**
	 * Executes when the program begins
	 */
	public void setup() {
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
		
//		if (!(p.x > x + width || p.y > y+height || p.x < x || p.y < y)) { //range check
//			j = (int) (p.y * grid.length / height);
//			i = (int) (p.x * grid[0].length / width);
//		}
		
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
//		if (keyCode == KeyEvent.VK_SPACE) {
//			if (runCount >= 0)
//				runCount = -1;
//			else
//				runCount = 0;
//		} else if (keyCode == KeyEvent.VK_DOWN) {
//			speed = Math.min(MAX_SPEED, speed*2);
//		} else if (keyCode == KeyEvent.VK_UP) {
//			speed = Math.max(MIN_SPEED, speed/2);
//			runCount = Math.min(runCount, speed);
//		} else if (keyCode == KeyEvent.VK_ENTER) {
//			board.step();
//		}
	}

	
}










