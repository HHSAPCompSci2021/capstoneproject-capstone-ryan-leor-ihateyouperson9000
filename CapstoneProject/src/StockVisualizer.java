/**
 * Represents a stock visualizer that includes a chart, UI, various tools, and a DCF calculator 
 * 
 * Author: Ryan Xu
 * Version: 5/5/22
 */

import java.awt.Dimension;  

import javax.swing.JFrame;

import com.crazzyghost.alphavantage.timeseries.TimeSeries;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;


public class StockVisualizer {
	public static void main(String[] args) {
	// TODO Auto-generated method stub
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setSize(750, 690);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setVisible(true);
		
	
	}

}
