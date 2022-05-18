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
import com.crazzyghost.alphavantage.fundamentaldata.response.BalanceSheet;
import com.crazzyghost.alphavantage.fundamentaldata.response.BalanceSheetResponse;
import com.crazzyghost.alphavantage.fundamentaldata.response.CashFlow;
import com.crazzyghost.alphavantage.fundamentaldata.response.CashFlowResponse;

public class DcfCalculator {
	
	private AlphaVantageConnector alpha;
	
	public DcfCalculator() {
		alpha = new AlphaVantageConnector();
		getData();
	}
	
	public void getData() {
		AlphaVantage.api()
		    .fundamentalData()
		    .balanceSheet()
		    .forSymbol(alpha.getTicker())
		    .onSuccess(e->handleBSSuccess(e))
		    .onFailure(e->handleFailure(e))
		    .fetch();
		
		AlphaVantage.api()
	    .fundamentalData()
	    .cashFlow()
	    .forSymbol(alpha.getTicker())
	    .onSuccess(e->handleCFSuccess(e))
	    .onFailure(e->handleFailure(e))
	    .fetch();
	}

	/**
	 * Instructions for what to do if the Balance Sheet data was successfully fetched
	 * 
	 * @param e the object passed from onSuccess() in the api
	 */
	private void handleBSSuccess(Object e) {
		ArrayList<BalanceSheet> bs = (ArrayList<BalanceSheet>) ((BalanceSheetResponse) e).getAnnualReports();
		
	}
	
	/**
	 * Instructions for what to do if the data was unsuccessfully fetched
	 * 
	 * @param error the error that occurred
	 */
	private void handleFailure(AlphaVantageException error) {
	    System.out.println(error.toString());
	}
	
	/**
	 * Instructions for what to do if the Cash Flow data was successfully fetched
	 * 
	 * @param e the object passed from onSuccess() in the api
	 */
	private void handleCFSuccess(Object e) {
		ArrayList<CashFlow> cf = (ArrayList<CashFlow>) ((CashFlowResponse) e).getAnnualReports();
		
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
