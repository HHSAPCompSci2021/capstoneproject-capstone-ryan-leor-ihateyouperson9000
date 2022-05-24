/**
 * Represents the ticker symbol of a stock
 * 
 * Author: Ryan Xu
 * Revision: 5/24/22
 */
import java.util.ArrayList;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

public class Ticker {
	
	public static String ticker;
	
	/**
	 * Creates a ticker
	 */
	public Ticker() {
		
	}
	
	/**
	 * Creates a ticker with given string
	 * @param s given string
	 */
	public Ticker(String s) {
		ticker = s;
	}
	
	/**
	 * Returns the ticker
	 * @return ticker of the stock
	 */
	public String getTicker() {
		return ticker;
	}
	
	/**
	 * Sets the ticker to a given string
	 * @param s string to set ticker to
	 */
	public void setTicker(String s) {
		// System.out.println("set");
		ticker = s;
	}


	
	
	

	
}
