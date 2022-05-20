package shapes;
/**
 * Rectangle is a shape that is formed from all points that are a width and or height away from a set of coordinates
 * 
 * @author: Ryan Xu
 * @version: 10/8/21
 */

import processing.core.PApplet;

public class Rectangle extends Shape {

	//FIELDS
	private double width, height;
	
	//CONSTRUCTORS
	/**
	 * Creates a Rectangle with parameters set to 0
	 */
	public Rectangle() {
		super(0, 0);
		width = 0;
		height = 0;
	}
	
	/**
	 * Creates a Rectangle with absolute value of given parameters
	 * 
	 * @param x x coordinate of rectangle
	 * @param y y coordinate of rectangle
	 * @param width of rectangle
	 * @param height of rectangle
	 * @pre x > 0
	 * @pre y > 0
	 * @post parameters are set to the absolute value of given ones
	 */
	public Rectangle(double x, double y, double width, double height) {
		super(x, y);
		this.width = Math.abs(width);
		this.height = Math.abs(height);
	}
	
	/*
	public Rectangle(double x, double y, double x2, float y2) {
		super(x, y);
		this.width = x2-x;
		this.height = y2-y;
	}
	*/
	
	//METHODS
	/**
	 * Gets width of a rectangle 
	 * 
	 * @return double: width 
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Gets height of a rectangle 
	 * 
	 * @return double: height 
	 */
	public double getHeight() {
		return height;
	}
	
	@Override
	/**
	 * Gets perimeter of a rectangle
	 * 
	 * @return double: perimeter
	 */
	public double getPerimeter() {
		return 2*(width+height);
	}
	
	@Override
	/**
	 * Gets area of a rectangle
	 * 
	 * @return double: area
	 */
	public double getArea() {
		return (width*height);
	}
	
	@Override
	/**
	 * Draws a rectangle from the upper left corner
	 *
	 * @param marker surface to draw the rectangle on
	 * @pre Rectangle will have graphical properties of those set on the drawer
	 * @post marker's rectMode is set to CORNER
	 */
	public void draw(PApplet marker) {
		super.draw(marker);
		marker.rectMode(marker.CORNER);
		marker.rect((float)getX(),(float)getY(),(float)width,(float)height);
	}
	
	@Override
	/**
	 * Turns circle data into words
	 * 
	 * @return string: x, y, width, and height of a rectangle
	 */
	public String toString() {
		super.toString(); //?
		return ", Width: " + width + ", Height: " + height;
	}
	
	/**
	 * Checks if a point is inside a rectangle
	 * 
	 * @param x x coordinate of point
	 * @param y y coordinate of point
	 * @return true if the point is inside the rectangle, false if not
	 */
	public boolean isPointInside(double x, double y) {
		if (getX()+width >= x && x >= getX() && getY()+height >= y && y >= getY()) {
			return true;
		}
		return false;
	}
	
	@Override
	/**
	 * Checks if two rectangles are touching
	 * 
	 * @param other other rectangle
	 * @return true if the rectangles are touching, false if not
	 */
	public boolean intersects(Shape other) {
		Rectangle r2 = (Rectangle)other;
		if (getX() >= r2.getX() && r2.getX()+r2.width >= getX() && 
				getY() >= r2.getY() && r2.getY()+r2.height >= getY()) {
			return true;
		}
		else if (r2.getX() >= getX() && getX()+width >= r2.getX() &&
					r2.getY() >= getY() && getY()+height >= r2.getY()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if rectangle is a square
	 * 
	 * @return true if the rectangle is a square, false if not
	 */
	public boolean isSquare() {
		if (width==height && width>0 && height>0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Makes rectangle grow larger by 10
	 * 
	 * @post width and height are increased by 10
	 */
	public void grow() {
		width+=10;
		height+=10;
	}
}

