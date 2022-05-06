import rxu770.shapes.Rectangle;

public class Button {

	private Rectangle border;
	private boolean isPressed;
	
	/**
	 * Creates a new Button that isn't pressed at (0,0)
	 */
	public Button() {
		border = new Rectangle();
		isPressed = false;
	}
	
	/**
	 * Creates a new Button with the inputted values
	 * @param x the x coordinate for the Button to be created at
	 * @param y the y coordinate for the Button to be created at
	 * @param width the width of the button
	 * @param height the height of the button
	 */
	public Button(double x, double y, double width, double height) {
		border = new Rectangle(x, y, width, height);
		isPressed = false;
	}
	
	/**
	 * Returns the Rectangle of the Button
	 * @return rectangle Rectangle of button
	 */
	public Rectangle getBorder() {
		return border;
	}
	
	public void moveTo(double x, double y) {
		border.setX(x);
		border.setY(y);
	}
	
	/**
	 * Checks if the Button is Pressed or not
	 * @return boolean stating whether this Button is pressed
	 */
	public boolean isPressed() {
		return isPressed;
	}
	
	/**
	 * Sets the button to pressed
	 */
	public void press() {
		isPressed = true;
	}
	
	/**
	 * Sets the button to unpressed
	 */
	public void unpress() { 
		isPressed = false;
	}
	
}
