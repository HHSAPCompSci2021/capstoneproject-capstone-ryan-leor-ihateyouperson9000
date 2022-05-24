import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;

public class ApiConnector {

	private static String[] keys;
	private static String key;
	private static int index;
	private static AlphaVantage alpha;
	
	public ApiConnector() {
		alpha = AlphaVantage.api();
		try {
			extractKeys();
		} catch (IOException e) {
			System.out.println("ApiConnector: " + e.toString());
		}
		index = -1;
	}

	public String getCurrentKey() {
		return key;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String printKeys() {
		String s = "";
		for(int i=0; i<keys.length; i++) {
			s += keys[i] + ", ";
		}
		return s;
	}
	
	public AlphaVantage getAlpha() {
		return alpha;
	}

	public void setKey(String s) {
		key = s;
		configure();
	}
	
	/**
	 * Configures the API
	 */
	public void configure() {
		Config cfg = Config.builder()
				.key(key) 
				.timeOut(10)
				.build();
		alpha.init(cfg);
		System.out.println("configured");
	}

	private void extractKeys() throws IOException {
		//		Path path = Paths.get("keys.txt");
		//		try {
		//			docInfo += Files.readString(path, StandardCharsets.ISO_8859_1);
		//		} catch (IOException e) {
		//			System.out.println(DOC_NAME + " not found");
		//		}
		List<String[]> keylist = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader("keys.txt"))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				keylist.add(line.split(","));
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} finally {
			keys = keylist.get(0);
		}
	}
	
	public void incrementKey() {
		if (index == keys.length-1) {
			index = 0;
		}
		else {
			index++;
		}
		key = keys[index];
	}

}
