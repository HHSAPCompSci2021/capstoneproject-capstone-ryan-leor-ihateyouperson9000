
import java.awt.Point; 
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import processing.core.PApplet;



public class DrawingSurface extends PApplet {

	
	public DrawingSurface() {
		
	}
	
	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
	}
	
	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() { 
		background(255);   // Clear the screen with a white background
		fill(0);
		textAlign(LEFT);
		textSize(12);
		
		text("Enter: Run 1 step\nSpace: Start/Stop\nUp arrow: Increase speed\nDown arrow: Decrease speed\n\nSpeed: " + (60.0/speed) + " per sec", height+20, 30);
		
	}
	
	/**
	 * (Graphical UI)
	 * Determines which element of the grid matches with a particular pixel coordinate.
	 * This supports interaction with the grid using mouse clicks in the window.
	 * 
	 * @param p A Point object containing a graphical pixel coordinate.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 * @return A Point object representing a coordinate within the game of life grid.
	 */
	public Point clickToIndex(Point p, float x, float y, float width, float height) {

		int j = 0;
		int i = 0;
		
		if (!(p.x > x + width || p.y > y+height || p.x < x || p.y < y)) { //range check
			j = (int) (p.y * grid.length / height);
			i = (int) (p.x * grid[0].length / width);
		}
		
		Point result = new Point(i, j);
		return result;
	}
	
	public void mousePressed() {
		if (mouseButton == LEFT) {
			Point click = new Point(mouseX,mouseY);
			float dimension = height;
			Point cellCoord = board.clickToIndex(click,0,0,dimension,dimension);
			if (cellCoord != null) {
				board.toggleCell(cellCoord.x, cellCoord.y);
				prevToggle = cellCoord;
			}
		} 
	}
	
	
	public void mouseDragged() {
		if (mouseButton == LEFT) {
			Point click = new Point(mouseX,mouseY);
			float dimension = height;
			Point cellCoord = board.clickToIndex(click,0,0,dimension,dimension);
			if (cellCoord != null && !cellCoord.equals(prevToggle)) {
				board.toggleCell(cellCoord.x, cellCoord.y);
				prevToggle = cellCoord;
			}
		} 
	}
	
	
	public void keyPressed() {
		if (keyCode == KeyEvent.VK_SPACE) {
			if (runCount >= 0)
				runCount = -1;
			else
				runCount = 0;
		} else if (keyCode == KeyEvent.VK_DOWN) {
			speed = Math.min(MAX_SPEED, speed*2);
		} else if (keyCode == KeyEvent.VK_UP) {
			speed = Math.max(MIN_SPEED, speed/2);
			runCount = Math.min(runCount, speed);
		} else if (keyCode == KeyEvent.VK_ENTER) {
			board.step();
		}
	}

	
}










