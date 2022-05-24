/**
 * Represents a calculator implementing discounted cash flow analysis based on the past 1 year to give a fair share value
 * 
 * Author: Ryan Xu
 * Version: 5/24/22
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
	
	private ApiConnector api;
	
	private Ticker ticker;
	private double shares;
	
		//Unlevered free cash flow
		private double netIncome;
		private double depreciationOrAmortization;
		private double deltWorkingCapital;
		private double capitalExpenditures;
		
	private double discountRate;
	
	/**
	 * Creates a DcfCalculator 
	 */
	public DcfCalculator() {
		api = new ApiConnector();
		
		ticker = new Ticker();
		
		discountRate = 0.1; 
	}
	
	/**
	 * Returns the ApiConnector object
	 * @return api 
	 */
	public ApiConnector getApi() {
		return api;
	}
	
	/**
	 * Calls the methods to get necessary financial data
	 */
	public void getData() {
		getBSData();
		getISData();
		getCFData();
	}
	
	/**
	 * Instructions for what to do if the data was unsuccessfully fetched
	 * 
	 * @param error the error that occurred
	 */
	private void handleFailure(AlphaVantageException error) {
	    System.out.println("DCF: " + error.toString());
//	    api.incrementKey();
//	    getData();
	}
	
	/**
	 * Sets up parameters for Balance Sheet API call
	 */
	private void getBSData() {
		api.getAlpha()
		    .fundamentalData()
		    .balanceSheet()
		    .forSymbol(ticker.getTicker())
		    .onSuccess(e->handleBSSuccess(e))
		    .onFailure(e->handleFailure(e))
		    .fetch();
	}
	
	/**
	 * Sets up parameters for Income Statement API call
	 */
	private void getISData() {
		AlphaVantage.api()
	    .fundamentalData()
	    .incomeStatement()
	    .forSymbol(ticker.getTicker())
	    .onSuccess(e->handleISSuccess(e))
	    .onFailure(e->handleFailure(e))
	    .fetch();
	}
	
	/**
	 * Sets up parameters for Cash Flow API call
	 */
	private void getCFData() {
		AlphaVantage.api()
	    .fundamentalData()
	    .cashFlow()
	    .forSymbol(ticker.getTicker())
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
		double workingCap1, workingCap2;
		BalanceSheet bs1 = ((ArrayList<BalanceSheet>) ((BalanceSheetResponse) e).getAnnualReports()).get(0);
		BalanceSheet bs2 = ((ArrayList<BalanceSheet>) ((BalanceSheetResponse) e).getAnnualReports()).get(1);
		
		double a = bs1.getTotalCurrentAssets();
		double b = bs1.getCashAndCashEquivalentsAtCarryingValue();
		double c = bs1.getTotalCurrentLiabilities();
		double d = bs1.getCurrentDebt();
		
		double f = bs2.getTotalCurrentAssets();
		double g = bs2.getCashAndCashEquivalentsAtCarryingValue();
		double h = bs2.getTotalCurrentLiabilities();
		double i = bs2.getCurrentDebt();
	
		workingCap1 = a-c;
		workingCap2 = f-h; 
		deltWorkingCapital = workingCap1 - workingCap2;
		
		shares = bs1.getCommonStockSharesOutstanding();
	}
	
	/**
	 * Instructions for what to do if the Cash Flow data was successfully fetched
	 * 
	 * @param e the object passed from onSuccess() in the api
	 */
	private void handleISSuccess(Object e) {
		IncomeStatement is = ((ArrayList<IncomeStatement>) ((IncomeStatementResponse) e).getAnnualReports()).get(0);
		netIncome = is.getNetIncome();
		depreciationOrAmortization = is.getDepreciationAndAmortization();
	}
	
	/**
	 * Instructions for what to do if the Cash Flow data was successfully fetched
	 * 
	 * @param e the object passed from onSuccess() in the api
	 */
	private void handleCFSuccess(Object e) {
		CashFlow cf = ((ArrayList<CashFlow>) ((CashFlowResponse) e).getAnnualReports()).get(0);
		capitalExpenditures = cf.getCapitalExpenditures();
	}
	
	/**
	 * Calculates the unlevered cash flow of the stock
	 * @return the unlevered cash flow of the stock
	 */
	private double calcUnleveredCashFlow() {

//		System.out.println("Net Income: " + netIncome);
//		System.out.println("Depr/Amort: " + depreciationOrAmortization);
//		System.out.println("Working Capital: " + deltWorkingCapital);
//		System.out.println("Capital Expenditures: " + capitalExpenditures);
		return netIncome + depreciationOrAmortization - deltWorkingCapital - capitalExpenditures;
	}
	
	/**
	 * Calculates the estimated fair value for the company
	 * @return the estimated fair value for the company
	 */
	public double calcDCF() {
		return calcUnleveredCashFlow()/(1+Math.pow(discountRate, 2));
	}
	
	/**
	 * Calculates the estimated fair price per share for the stock
	 * @return the estimated fair price per share for the stock
	 */
	public double calcEstSharePrice() {
		return calcDCF()/shares;
	}
	
	
}
