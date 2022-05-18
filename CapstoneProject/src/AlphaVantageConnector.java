import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;

public class AlphaVantageConnector {

	private Config cfg;
	
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
	
	
}
