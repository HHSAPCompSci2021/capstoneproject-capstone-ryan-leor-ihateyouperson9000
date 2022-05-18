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
import com.crazzyghost.alphavantage.fundamentaldata.response.IncomeStatement;
import com.crazzyghost.alphavantage.fundamentaldata.response.IncomeStatementResponse;

public class DcfCalculator {
	
	private boolean dataGood;
	
		//Calculating Unlevered free cash flow
		private double netIncome;
		private double depreciationOrAmortization;
		private double deltWorkingCapital;
		private double deltWorkingCapital_optimized;
		private double capitalExpenditures;
	
	public DcfCalculator() {
		getBSData();
		getISData();
		getCFData();
	}
	
	/**
	 * Sets up parameters for Balance Sheet API call
	 */
	public void getBSData() {
		AlphaVantage.api()
		    .fundamentalData()
		    .balanceSheet()
		    .forSymbol()
		    .onSuccess(e->handleBSSuccess(e))
		    .onFailure(e->handleFailure(e))
		    .fetch();
		dataGood = true;
	}
	
	/**
	 * Sets up parameters for Income Statement API call
	 */
	public void getISData() {
		AlphaVantage.api()
	    .fundamentalData()
	    .incomeStatement()
	    .forSymbol(alpha.getTicker())
	    .onSuccess(e->handleISSuccess(e))
	    .onFailure(e->handleFailure(e))
	    .fetch();
		dataGood = true;
	}
	
	/**
	 * Sets up parameters for Cash Flow API call
	 */
	public void getCFData() {
		AlphaVantage.api()
	    .fundamentalData()
	    .cashFlow()
	    .forSymbol(alpha.getTicker())
	    .onSuccess(e->handleCFSuccess(e))
	    .onFailure(e->handleFailure(e))
	    .fetch();
		dataGood = true;
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
	 * Instructions for what to do if the Balance Sheet data was successfully fetched
	 * 
	 * @param e the object passed from onSuccess() in the api
	 */
	private void handleBSSuccess(Object e) {
		double workingCap1, workingCap2;
		ArrayList<BalanceSheet> bs = (ArrayList<BalanceSheet>) ((BalanceSheetResponse) e).getAnnualReports();
		
		double a = bs.get(0).getTotalCurrentAssets();
		double b = bs.get(0).getCashAndCashEquivalentsAtCarryingValue();
		double c = bs.get(0).getTotalCurrentLiabilities();
		double d = bs.get(0).getCurrentDebt();
		
		double f = bs.get(1).getTotalCurrentAssets();
		double g = bs.get(1).getCashAndCashEquivalentsAtCarryingValue();
		double h = bs.get(1).getTotalCurrentLiabilities();
		double i = bs.get(1).getCurrentDebt();
	
		workingCap1 = a-c;
		workingCap2 = f-h; 
		deltWorkingCapital = workingCap1 - workingCap2;
		workingCap1 = (a-b) - (c-d);
		workingCap2 = (f-g) - (h-i); 
		deltWorkingCapital_optimized = workingCap1 - workingCap2;
	}
	
	/**
	 * Instructions for what to do if the Cash Flow data was successfully fetched
	 * 
	 * @param e the object passed from onSuccess() in the api
	 */
	private void handleISSuccess(Object e) {
		ArrayList<IncomeStatement> is = (ArrayList<IncomeStatement>) ((IncomeStatementResponse) e).getAnnualReports();
		netIncome = is.get(0).getNetIncome();
		depreciationOrAmortization = is.get(0).getDepreciationAndAmortization();
	}
	
	/**
	 * Instructions for what to do if the Cash Flow data was successfully fetched
	 * 
	 * @param e the object passed from onSuccess() in the api
	 */
	private void handleCFSuccess(Object e) {
		ArrayList<CashFlow> cf = (ArrayList<CashFlow>) ((CashFlowResponse) e).getAnnualReports();
		capitalExpenditures = cf.get(0).getCapitalExpenditures();
	}
	
	private double calcUnleveredCashFlow() {
		return netIncome + depreciationOrAmortization - deltWorkingCapital - capitalExpenditures;
	}
	
	private double calcOptimizedUnleveredCashFlow() { 
		return netIncome + depreciationOrAmortization - deltWorkingCapital_optimized - capitalExpenditures;
	}
	
	private double calcDCF() {
		if (dataGood) {
			
			
			
		}
		return 0.0;
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
