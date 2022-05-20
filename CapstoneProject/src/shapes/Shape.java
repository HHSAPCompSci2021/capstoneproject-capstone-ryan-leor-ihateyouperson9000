/**
 * Shape is a superclass that defines enclosed figures that are created by combining points lines and curves
 * 
 * @author: Ryan Xu
 * @version: 10/8/21
 */

package shapes;

import processing.core.PApplet;

public abstract class Shape {

	//FIELDS
	private double x, y;
	private int fillcolor1, fillcolor2, fillcolor3,
		strokecolor1, strokecolor2, strokecolor3,
		strokeweight;
	private boolean fill;
	
	//CONSTRUCTORS
	public Shape(double x, double y) {
		this.x = x;
		this.y = y;
		fillcolor1 = 0;
		fillcolor2 = 0;
		fillcolor3 = 0;
		strokecolor1 = 0;
		strokecolor2 = 0;
		strokecolor3 = 0;
		strokeweight = 1;
		fill = true;
	}
	
	//METHODS

	/**
	 * Gets the x coordinate
	 * 
	 * @return double: x 
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Gets the y coordinate
	 * 
	 * @return double: y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Gets the first RGB value of stroke color
	 * 
	 * @return int: strokecolor1
	 */
	public int getStrokeColor1() {
		return strokecolor1;
	}
	
	/**
	 * Gets the second RGB value of stroke color
	 * 
	 * @return int: strokecolor2
	 */
	public int getStrokeColor2() {
		return strokecolor2;
	}
	
	/**
	 * Gets the third RGB value of stroke color
	 * 
	 * @return int: strokecolor3
	 */
	public int getStrokeColor3() {
		return strokecolor3;
	}
	
	/**
	 * Gets the first RGB value of fill color
	 * 
	 * @return int: fillcolor1
	 */
	public int getFillColor1() {
		return fillcolor1;
	}
	
	/**
	 * Gets the second RGB value of fill color
	 * 
	 * @return int: fillcolor2
	 */
	public int getFillColor2() {
		return fillcolor2;
	}
	
	/**
	 * Gets the third RGB value of fill color
	 * 
	 * @return int: fillcolor3
	 */
	public int getFillColor3() {
		return fillcolor3;
	}
	
	/**
	 * Gets the stroke width
	 * 
	 * @return int: strokeweight
	 */
	public int getStrokeWeight() {
		return strokeweight;
	}
	
	/**
	 * Gets area of a shape
	 * 
	 * @return double: area
	 */
	public abstract double getArea();
	
	/**
	 * Gets perimeter of a shape
	 * 
	 * @return double: perimeter
	 */
	public abstract double getPerimeter();
	
	/**
	 * Sets X value 
	 * 
	 * @param x 
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Sets Y value 
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Sets the stroke color of shape
	 * 
	 * @param a int value of first RGB value
	 * @param b int value of second RGB value
	 * @param c int value of third RGB value
	 */
	public void setStrokeColors(int a, int b, int c) {
		strokecolor1 = a;
		strokecolor2 = b;
		strokecolor3 = c;
	}
	
	/**
	 * Sets the fill color of shape
	 * 
	 * @param a int value of first RGB value
	 *  @param b int value of second RGB value
	 * @param c int value of third RGB value
	 */
	public void setFillColors(int a, int b, int c) {
		fillcolor1 = a;
		fillcolor2 = b;
		fillcolor3 = c;
	}
	
	/**
	 * Sets the stroke width of shape
	 * 
	 * @param c int value of color
	 */
	public void setStrokeWeight(int c) {
		strokeweight = c;
	}
	
	/**
	 * Turns data into words
	 * 
	 * @return string: x and y coordinates
	 */
	public String toString() {
		return "SHAPE: X: " + x + ", Y: " + y;
	}
	
	/**
	 * Draws a shape
	 *
	 * @param app surface to draw the shape on
	 * @pre shape will have graphical presets of the drawer
	 */
	public void draw(PApplet app) {
		app.stroke(getStrokeColor1(), getStrokeColor2(), getStrokeColor3());
		app.strokeWeight(getStrokeWeight());
		if (!fill) {
			app.noFill();
		} else {
			app.fill(getFillColor1(), getFillColor2(), getFillColor3());
		}
	}
	
	public void removeFill() {
		fill = false;
	}
	
	public void addFill() {
		fill = true;
	}

	/**
	 * Checks if a point is inside the shape
	 * 
	 * @param x x coordinate of point
	 * @param y y coordinate of point
	 * @return true if point is inside, false if not
	 */
	public abstract boolean isPointInside(double x, double y);
	
	public void move(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Checks if two shapes intersect
	 * 
	 * @param other other shape
	 * @return true if they intersect, false if not
	 * @pre the shapes have to be the same
	 */
	public abstract boolean intersects(Shape other);
	
	/**
	 * Moves shape to coordinates mouse click
	 * 
	 * @param x x coordinate of point
	 * @param y y coordinate of point
	 * @param app surface in which the mouse is clicked
	 * @pre mouse is clicked
	 * @post sets x to x coordinate of mouse click
	 * @post sets y to y coordinate of mouse click
	 */
	public void moveTo(double x, double y, PApplet app) {
		//app.pushStyle();
		x = app.mouseX;
		y = app.mouseY;
		//app.popStyle();
	}
	
	/**
	 * Moves shape horizontally
	 * 
	 * @param amount the amount for the shape to move
	 * @post x is increased by the amount given
	 */
	public void moveX(double amount) {
		x+=amount;
	}
	
	/**
	 * Moves shape vertically
	 * 
	 * @param amount the amount for the shape to move
	 * @post y is increased by the amount given
	 */
	public void moveY(double amount) {
		y+=amount;
	}
	
	/**
	 * Converts a value from degrees into radians
	 *
	 * @param n angle in degrees
	 * @return angle in radians
	 */
	public double toRadians(double n) {
		return n/180d*Math.PI;
	}

}
