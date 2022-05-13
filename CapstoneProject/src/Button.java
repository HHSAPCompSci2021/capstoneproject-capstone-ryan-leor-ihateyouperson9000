/**
 * Represents a rectangular button that can be pressed
 * 
 * Author: Leor Porat, Ryan Xu
 * Version: 5/6/22
 */

import processing.core.PApplet;
import processing.core.PImage;
import rxu770.shapes.Rectangle;

public class Button {

	private Rectangle border;
	// private PImage img;
	private boolean isPressed;
	
	/**
	 * Creates a new Button that isn't pressed at (0,0)
	 */
	public Button() {
		border = new Rectangle();
		isPressed = false;
		// img = null;
	}
	
	/**
	 * Creates a new Button with the inputted values
	 * @param x the x coordinate for the Button to be created at
	 * @param y the y coordinate for the Button to be created at
	 * @param width the width of the button
	 * @param height the height of the button
	 */
	public Button(double x, double y, double width, double height, String imageName, PApplet drawer) {
		border = new Rectangle(x, y, width, height);
		isPressed = false;
	//	img = drawer.loadImage(imageName);
	}
	
	/**
	 * Returns the Rectangle that is the border of the Button
	 * @return border
	 */
	public Rectangle getBorder() {
		return border;
	}
	
	/**
	 * Moves the button to a given location
	 * @param x x coordinate to move to
	 * @param y y coordinate to move to
	 * @post sets x coordinate to given x
	 * @post sets y coordinate to given y
	 */
	public void moveTo(double x, double y) {
		border.setX(x);
		border.setY(y);
	}
	
	
	/**
	 * Draws this button to the PApplet drawer
	 * @param drawer the surface the button is being drawn on
	 */
	public void draw(PApplet drawer) {
		drawer.rect((float)border.getX(), (float)border.getY(), (float)border.getWidth(), (float)border.getHeight());
		// drawer.image(img, (float)border.getX(), (float)border.getY());
	}
	
	/**
	 * Checks if the Button is pressed or not
	 * @return true if pressed, false if not
	 */
	public boolean isPressed() {
		return isPressed;
	}
	
	/**
	 * Sets the button to pressed
	 */
	public void press() {
		isPressed = true;
		// System.out.println("PRESSED");
	}
	
	/**
	 * Sets the button to unpressed
	 */
	public void unpress() { 
		isPressed = false;
	}
	
}
