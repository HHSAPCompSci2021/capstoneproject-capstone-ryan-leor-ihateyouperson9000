import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;

public class ApiConnector {

	private String[] keys;
	private String currentKey;
	private int index;

	public ApiConnector() {
		try {
			extractKeys();
		} catch (IOException e) {
			System.out.println("ApiConnector: " + e.toString());
		}
		index = -1;
	}


	/**
	 * Configures the API
	 */
	public void configure() {
		Config cfg = Config.builder()
				.key(currentKey) 
				.timeOut(10)
				.build();
		AlphaVantage.api().init(cfg);
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
			System.out.println(keys.toString());
		}
	}

	public String getCurrentKey() {
		return currentKey;
	}

	public void incrementKey() {
		if (index == keys.length-1) {
			index = 0;
		}
		else {
			index++;
		}
		currentKey = keys[index];
	}

}
