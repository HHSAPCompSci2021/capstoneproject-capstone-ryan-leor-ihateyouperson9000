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
	
	private Ticker ticker;
	private boolean dataGood;
	private double shares;
	
		//Unlevered free cash flow
		private double netIncome;
		private double depreciationOrAmortization;
		private double deltWorkingCapital;
		private double deltWorkingCapital_optimized;
		private double capitalExpenditures;
		
	private double discountRate;
	
		//OptimizedDCF
		private double cash;
		private double debt;
	
	public DcfCalculator() {
			ticker = new Ticker();
			ticker.setTicker("IBM");
		
			discountRate = 0.1;
			
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
		    .forSymbol(ticker.getTicker())
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
	    .forSymbol(ticker.getTicker())
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
	    .forSymbol(ticker.getTicker())
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
		
		cash = b;
		debt = d;
		
		shares = bs.get(0).getCommonStockSharesOutstanding();
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

		System.out.println("Net Income: " + netIncome);
		System.out.println("Depr/Amort: " + depreciationOrAmortization);
		System.out.println("Working Capital: " + deltWorkingCapital);
		System.out.println("Capital Expenditures: " + capitalExpenditures);
		return netIncome + depreciationOrAmortization - deltWorkingCapital - capitalExpenditures;
	}
	
	private double calcOptimizedUnleveredCashFlow() { 
		return netIncome + depreciationOrAmortization - deltWorkingCapital_optimized - capitalExpenditures;
	}
	
	public double calcDCF() {
		return calcUnleveredCashFlow()/(1+Math.pow(discountRate, 2));
	}
	
	public double calcOptimizedDCF() {
		return calcOptimizedUnleveredCashFlow()/(1+Math.pow(discountRate, 2));
	}
	
	public double calcEstSharePrice() {
		return calcDCF()/shares;
	}
	
	public double calcOptimizedEstSharePrice() {
		return (calcOptimizedDCF()+cash-debt)/shares;
	}
	
	

	public double getNetWorkingCapital() {
		return deltWorkingCapital;
	}
}
