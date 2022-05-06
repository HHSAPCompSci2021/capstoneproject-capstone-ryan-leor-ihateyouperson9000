/**
 * Represents a rectangular box that can display text and be typed into
 * 
 * Author: Ryan Xu
 * Version: 5/6/22
 */
import rxu770.shapes.Rectangle;

public class TextBox {

	private Rectangle border;
	private String input;
	private String text;
	
	/**
	 * Creates a new TextBox with no text at (0,0)
	 */
	public TextBox() {
		border = new Rectangle();
		text = "";
	}
	
	/**
	 * Creates a new TextBox with the inputted values
	 * @param x the x coordinate for the TextBox to be created at
	 * @param y the y coordinate for the TextBox to be created at
	 * @param width the width of the TextBox
	 * @param height the height of the TextBox
	 * @param str the text in the textbox telling the user what to type
	 */
	public TextBox(double x, double y, double width, double height, String str) {
		border = new Rectangle(x, y, width, height);
		text = str;
	}
	
	/**
	 * Returns the Rectangle that is the border of the TextBox
	 * @return border
	 */
	public Rectangle getBorder() {
		return border;
	}
	
	/**
	 * Returns the text that is to be displayed in the textbox for instructions on what to type
	 * 
	 * @return text
	 */
	public String getDisplayText() {
		return text;
	}
	
	/**
	 * Returns the last inputted text
	 * 
	 * @return input
	 */
	public String getInput() {
		return input;
	}
	
	/**
	 * Moves the TextBox to a given location
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
	 * Sets input (field) to a given string that was typed in the textbox
	 * 
	 * @post input is set equal to str
	 */
	public void input(String str) {
		input = str;
	}
	
}
