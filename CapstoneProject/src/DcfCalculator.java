/**
 * Represents a calculator implementing discounted cash flow analysis to give a price target
 * 
 * Author: Ryan Xu
 * Version: 5/5/22
 */
import java.util.ArrayList;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.fundamentaldata.FundamentalData;
import com.crazzyghost.alphavantage.fundamentaldata.response.BalanceSheetResponse;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

public class DcfCalculator extends AlphaVantageConnector {
	
	public DcfCalculator() {
		super();
	}
	
	/**
	 * Instructions for what to do if the API was successfully fetched
	 * 
	 * @param e the object passed from onSuccess() in the api
	 */
	private void handleSuccess(Object e) {
	    data = (ArrayList<StockUnit>) ((TimeSeriesResponse) e).getStockUnits();
	    dataGood = true;
	}
	
	/**
	 * Instructions for what to do if the API was unsuccessfully fetched
	 * 
	 * @param error the error that occurred
	 */
	private void handleFailure(AlphaVantageException error) {
	    System.out.println(error.toString());
	}
	
	
	
	
			
//	private ArrayList<Double> data;
//	private final String[] NEEDED_DATA = {"Diluted EPS (ttm)", "Gross Profit\t", };
//											//in statistics       //in financials
//	private final double MARKET_RATE = 0.1;
//	private final int YEARS = 5; //depends on the mkt cap (intraday) [current] , statistics
//	private final double AFTER_RATE = 0;
//	
//	/**
//	 * Creates a DcfCalculator that instantiates data (field) to the desired data 
//	 * 
//	 * @pre only call when there exists a "financials.txt" in src
//	 * @post sets data to desired data
//	 */
//	public DcfCalculator() {
//		DataExtractor e = new DataExtractor();
//		e.addFile();
//		e.addWords(NEEDED_DATA);
//		for (String s : e.extractData()) {
//			data.add(Double.parseDouble(s)); //auto removes any \t etc
//		}
//	}
//	
//	/**
//	 * Returns the target price
//	 * 
//	 * @return target price
//	 */
//	public double calcPriceTarget() {
//		double eps = data.get(0); //expected 
//		double avgGrowth =  (data.get(2)/data.get(3)-1 + data.get(3)/data.get(4)-1 + data.get(4)/data.get(5)-1)/3;
//		
//		return 0.0;
//	}
	
}
