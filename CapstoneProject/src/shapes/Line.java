package shapes;



import processing.core.PApplet;

public class Line extends Shape {

	private double x2, y2;
	private double length;
	
	/**
	 * Creates a Line with given parameters
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @pre x1 > 0
	 * @pre y1 > 0
	 * @pre x2 > 0
	 * @pre y2 > 0
	 */
	public Line(double x1, double y1, double x2, double y2) {
		super(x1, y1);
		this.x2 = x2;
		this.y2 = y2;
		length = Math.sqrt(Math.pow(Math.abs(x2-x1), 2) + Math.pow(Math.abs(y2-y1), 2));
	}
	
	/**
	 * Creates a line with angle above x in degrees and given parameters
	 * 
	 * @param x1
	 * @param y1
	 * @param angle
	 * @param length
	 * @pre x1 > 0
	 * @pre y1 > 0
	 * @pre length > 0
	 */
	public Line(double x1, double y1, double angle, float length) {
		super(x1, y1);
		x2 = x1+length*Math.cos(toRadians(angle));
		y2 = y1-length*Math.sin(toRadians(angle));
		this.length = length;
	}
	

	/**
	 * Gets the x coordinate of the second point in a line
	 * 
	 * @return double: x2
	 */
	public double getX2() {
		return x2;
	}
	
	/**
	 * Gets the y coordinate of the second point in a line
	 * 
	 * @return double: y2
	 */
	public double getY2() {
		return x2;
	}
	
	/**
	 * Gets the length of the line
	 * 
	 * @return double: length
	 */
	public double getLength() { 
		return length;
	}
	
	@Override
	/**
	 * Gets perimeter of a line
	 * 
	 * @return double: distance
	 */
	public double getPerimeter() {
		// TODO Auto-generated method stub
		return Math.sqrt(Math.pow(x2-getX(), 2.0) + Math.pow(y2-getY(), 2.0));
	}
	
	@Override
	/**
	 * Gets area of a line
	 * 
	 * @return 0
	 */
	public double getArea() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	/**
	 * Draws a line
	 * 
	 * @param app surface to draw on
	 * @pre Line will have graphical properties of those set on the drawer 
	 */
	public void draw(PApplet app) {
		super.draw(app);
		app.line((float)getX(), (float)getY(), (float)x2, (float)y2);
	}
	
	@Override
	/**
	 * Turns line data into words
	 * 
	 * @return string: x, y, x2, and y2 of a line
	 */
	public String toString() {
		super.toString();
		return ", X2: " + x2 + ", Y2: " + y2;
	}
	
	@Override
	/**
	 * Tests if point is in contact with a line
	 * 
	 * @param x x coordinate of point
	 * @param y y coordinate of point
	 * @return true if the point is in contact, false if not
	 */
	public boolean isPointInside(double x, double y) {
		// TODO Auto-generated method stub
		double slopeLine = (y2-getY())/(x2-getX());
		double slopePoint = (y-getY())/(x-getX());
		if (slopeLine == slopePoint) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the x coordinate of the intersection point
	 * 
	 * @param line other line
	 * @return the x coordinate of the intersection point
	 */
	public double getIntersectionX(Line line) {
		double x3 = line.getX();
		double y3 = line.getY();
		double x4 = line.x2;
		double y4 = line.y2;
		
		double num = (getX()*y2 - getY()*x2)*(x3-x4) - (getX()-x2)*(x3*y4 - y3*x4);
		double denom = (getX()-x2)*(y3-y4) - (getY()-y2)*(x3-x4);
		return (num/denom);
	}
	
	/**
	 * Gets the y coordinate of the intersection point
	 * 
	 * @param line other line
	 * @return the y coordinate of the intersection point
	 */
	public double getIntersectionY(Line line) {
		double x3 = line.getX();
		double y3 = line.getY();
		double x4 = line.x2;
		double y4 = line.y2;
		
		double num = (getX()*y2 - getY()*x2)*(y3-y4) - (getY()-y2)*(x3*y4 - y3*x4);
		double denom = (getX()-x2)*(y3-y4) - (getY()-y2)*(x3-x4);
		return (num/denom);
	}
	
	@Override
	/**
	 * Checks if two lines intersect
	 * 
	 * @param other other line
	 * @return true if the two lines are touching, false if not
	 */
	public boolean intersects(Shape other) {
		
		Line l2 = (Line)other;
		
		double interX = getIntersectionX(l2);
		double interY = getIntersectionY(l2);
		
		double x3 = l2.getX();
		double y3 = l2.getY();
		double x4 = l2.x2;
		double y4 = l2.y2;
		
		double width1 = getY()-y2; //north
		double length1 = x2-getX(); //east
		double width2 = y3-y4; //north
		double length2 = x4-x3; //east
		
		//for x2>x1 and y2>y1 SE 
		if (width1 < 0 && length1 > 0 || width2 < 0 && length2 > 0) {
			if ((x2 >= interX && interX >= getX() && y2 >= interY && interY >= getY()) 
					|| (x4 >= interX && interX >= x3 && y4 >= interY && interY >= y3)) {
				return true;
			}
		}
		//for x2>x1 and y2>y1 SW
		else if (width1 < 0 && length1 < 0 || width2 < 0 && length2 < 0) {
			if ((getX() >= interX && interX >= x2 && y2 >= interY && interY >= getY()) 
					|| (x3 >= interX && interX >= x4 && y4 >= interY && interY >= y3)) {
				return true;
			}		
		}
		//for x2>x1 and y2>y1 NE
		else if (width1 > 0 && length1 > 0 || width2 > 0 && length2 > 0) {
			if ((x2 >= interX && interX >= getX() && getY() >= interY && interY >= y2) 
					|| (x4 >= interX && interX >= x3 && y3 >= interY && interY >= y4)) {
				return true;
			}	
		}
		//for x2>x1 and y2>y1 NW
		else if (width1 > 0 && length1 < 0 || width2 > 0 && length2 < 0) {
			if ((getX() >= interX && interX >= x2 && getY() >= interY && interY >= y2) 
					|| (x3 >= interX && interX >= x4 && y3 >= interY && interY >= y4)) {
				return true;
			}		
		} 
		
		return false;
	}
	
	/**
	 * Sets second coordinate point of line to mouse coordinates
	 * 
	 * @param mouseX
	 * @param mouseY
	 */
	public void setPoint2(float mouseX, float mouseY) {
		x2 = mouseX;
		y2 = mouseY;
	}
	
}
