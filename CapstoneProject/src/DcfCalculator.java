/**
 * Represents a calculator implementing discounted cash flow analysis to give a price target
 * 
 * Author: Ryan Xu
 * Version: 5/5/22
 */
import java.util.ArrayList;

public class DcfCalculator {

	private double eag;
	private double wacc;
	private final int YEARS = 5;
	private ArrayList<String> data;
	private final String[] NEEDED_DATA = {"EAG", "WACC"};
	
	/**
	 * Creates a DcfCalculator that instantiates data (field) to the desired data 
	 * 
	 * @pre only call when there exists a "financials.txt" in src
	 * @post sets data to desired data
	 */
	public DcfCalculator() {
		DataExtractor e = new DataExtractor();
		e.addFile();
		e.addWords(NEEDED_DATA);
		data = e.extractData();
	}

	/**
	 * Sets each respective field of needed data to their correct values
	 * 
	 * @post eag is set to the eag
	 * @post wacc is set to the wacc
	 */
	private void setData() {
		
	}
	
	/**
	 * Returns the target price
	 * 
	 * @return target price
	 */
	public double calcPriceTarget() {
		return 0.0;
	}
	
}
