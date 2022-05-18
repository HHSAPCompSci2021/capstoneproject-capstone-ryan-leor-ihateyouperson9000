import java.util.ArrayList;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

public class AlphaVantageConnector {
	
	public String ticker;
	
	public AlphaVantageConnector() {
	}
	
	public void setTicker(String s) {
		ticker = s;
	}
	public String getTicker() {
		return ticker;
	}
	

	
	
	

	
}
