/**
 * Represents a PApplet that draws the stock chart and UI
 * 
 * Author: Ryan Xu
 * Version: 5/6/22
 */
import java.awt.Point; 
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import processing.core.PApplet;



public class DrawingSurface extends PApplet {

	/**
	 * Creates a DrawingSurface object
	 */
	public DrawingSurface() {
		
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
//		if (mouseButton == LEFT) {
//			Point click = new Point(mouseX,mouseY);
//			float dimension = height;
//			Point cellCoord = board.clickToIndex(click,0,0,dimension,dimension);
//			if (cellCoord != null) {
//				board.toggleCell(cellCoord.x, cellCoord.y);
//				prevToggle = cellCoord;
//			}
//		} 
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










