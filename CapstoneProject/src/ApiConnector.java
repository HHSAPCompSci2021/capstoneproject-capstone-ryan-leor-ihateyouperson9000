/**
 * Connects the project to the AlphaVantage API
 * 
 * Author: Ryan Xu
 * Revision: 5/24/22
 */

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
	private static String key = "";
	private static String inputKey = "";
	private static int index;
	private static AlphaVantage alpha;
	
	/**
	 * Creates an api connector
	 */
	public ApiConnector() {
		alpha = AlphaVantage.api();
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
				.key("PASTE_KEY_HERE")  //PASTE YOUR KEY HERE
				.timeOut(10)
				.build();
		alpha.init(cfg);
		System.out.println("configured");
	}

	/**
	 * Returns the key
	 * @return key
	 */
	public String getCurrentKey() {
		return key;
	}
	
	/**
	 * Returns the index
	 * @return index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Returns all keys in a csv format
	 * @return keys
	 */
	public String getKeys() {
		String s = "";
		for(int i=0; i<keys.length; i++) {
			s += keys[i] + ", ";
		}
		return s;
	}
	
	/**
	 * Returns the alphavantage object
	 * @return alpha
	 */
	public AlphaVantage getAlpha() {
		return alpha;
	}

	/**
	 * Sets the key to a given string
	 * @param s given string
	 */
	public void setKey(String s) {
		inputKey = s;
	}

	/**
	 * DECOMMISSIONED Extracts the keys from the file named keys.txt
	 * @throws IOException when file isn't found
	 */
	private void extractKeys() throws IOException {
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
	
	/**
	 * DECOMMISSIONED Changes the key
	 */
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
