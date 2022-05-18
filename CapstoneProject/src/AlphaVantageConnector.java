import java.util.ArrayList;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

public class AlphaVantageConnector {

	public Config cfg;
	public String ticker;
	
	public AlphaVantageConnector() {
		cfg = Config.builder()
			    .key("K3GVRKJIDYNUZPZM")
			    .timeOut(10)
			    .build();
		AlphaVantage.api().init(cfg);
	}
	
	public Config getConfig() {
		return cfg;
	}
	
	public void setTicker(String s) {
		ticker = s;
	}
	public String getTicker() {
		return ticker;
	}
	
	/**
	 * Sets up parameters for data from the API
	 */
	public void getTimeSeriesData() {
		 AlphaVantage.api()
		    .timeSeries()
		    .daily() //change based on timespan
		    .forSymbol(ticker)
		    .outputSize(OutputSize.FULL)
		    .onSuccess(e->handleSuccess(e))
		    .onFailure(e->handleFailure(e))
		    .fetch();
	}
	
	public void getFundamentalData() {
		AlphaVantage.api()
		    .fundamentalData()
		    .balanceSheet()
		    .forSymbol(ticker)
		    .onSuccess(e->handleSuccess(e))
		    .onFailure(e->handleFailure(e))
		    .fetch();
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
	
}
