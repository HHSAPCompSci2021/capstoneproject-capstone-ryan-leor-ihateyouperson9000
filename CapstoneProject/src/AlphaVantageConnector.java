import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;

public class AlphaVantageConnector {

	private Config cfg;
	private String stockTicker;
	
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
		stockTicker = s;
	}
	public String getTicker() {
		return stockTicker;
	}
	
	
	
}
