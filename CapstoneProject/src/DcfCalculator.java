/**
 * Represents a calculator implementing discounted cash flow analysis to give a price target
 * 
 * Author: Ryan Xu
 * Version: 5/5/22
 */
import java.util.ArrayList;

public class DcfCalculator {

	private final int YEARS = 5;
	private ArrayList<Double> data;
	private final String[] NEEDED_DATA = {"Expected Annual Growth", "Weighted Average"};
	
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
		for (String s : e.extractData()) {
			data.add(Double.parseDouble(s));
		}
	}
	
	/**
	 * Returns the target price
	 * 
	 * @return target price
	 */
	public double calcPriceTarget() {
		double eag = data.get(0); //expected 
		double wacc = data.get(1);
		
		return 0.0;
	}
	
}
