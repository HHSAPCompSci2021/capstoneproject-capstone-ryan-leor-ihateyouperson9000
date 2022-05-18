import java.util.ArrayList;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

public class Ticker {
	
	public String ticker;
	
	public Ticker() {
		ticker = "AAPL";
	}
	
	public void setTicker(String s) {
		System.out.println("set");
		ticker = s;
	}
	public String getTicker() {
		return ticker;
	}
	

	
	
	

	
}
