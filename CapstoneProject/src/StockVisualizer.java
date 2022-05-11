/**
 * Represents a stock visualizer that includes a chart, UI, various tools, and a DCF calculator
 * 
 * Author: Ryan Xu
 * Version: 5/5/22
 */

import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class StockVisualizer {
//	com.crazzyghost.alphavantage.
	public static void main(String[] args) {
	// TODO Auto-generated method stub
	DrawingSurface drawing = new DrawingSurface();
	PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setSize(1280, 1920);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setVisible(true);
//		String str = "Gross Profit (ttm)	197.48B\r\n"
//				+ "EBITDA	55.45B\r\n"
//				+ "Net Income Avi to Common (ttm)	21.41B\r\n"
//				+ "Diluted EPS (ttm)	41.69\r\n"
//				+ "Quarterly Earnings Growth (yoy)	N/A\r\n"
//				+ "";
//		String s = "\n47";
//		System.out.println(s.indexOf("\t"));
//		System.out.println(s.lastIndexOf("\t"));
//		System.out.println(Double.parseDouble(s)+3);
		
	}

}
