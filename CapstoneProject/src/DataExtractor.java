/**
 * Extracts desired financial data from the text file named "financials.txt"
 * 
 * Author: Ryan Xu
 * Version: 5/5/22
 */
import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DataExtractor {

	private String[] keywords;
	private final String DOC_NAME = "financials.txt";
	private String docInfo;
	
	/**
	 * Creates a DataExtractor object
	 */
	public DataExtractor() {
		
	}
	
	/**
	 * Adds list of desired financial info to keywords
	 * 
	 * @param words list of desired financial info
	 * @post sets keywords to given list
	 */
	public void addWords(String[] words) {
		for (int i=0; i<words.length; i++) {
			keywords[i] = words[i];
		}
	}
	
	/**
	 * Adds file data with given name to docInfo, can add multiple files just replace the data file each time and call
	 * 
	 * @pre must import earnings data to folder and rename it "financials.txt"
	 * @post docInfo is appended by the data in the file
	 */
	public void addFile() {
		
		Path path = Paths.get("DOC_NAME");
		try {
			docInfo += Files.readString(path, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			System.out.println(DOC_NAME + " not found");
		}
        
	}
	
	/**
	 * Returns the parsed String version of the file
	 * 
	 * @return docInfo
	 */
	public String getFile() {
		return docInfo;
	}
	
	/**
	 * Clears the data
	 * 
	 * @post docInfo is set to ""
	 */
	public void clearData() {
		docInfo = "";
	}
	
	/**
	 * Extracts the desired data from docInfo
	 * 
	 * @pre addFile() should be called
	 * @return the desired financial data
	 */
	public ArrayList<String> extractData() {
		ArrayList<String> data = new ArrayList<String>();
		
		for (String word : keywords) {
			if (docInfo.indexOf(word) != 0) {
				
				if (word.indexOf("\t") != 0) {
					addLineData(data, word);
				}
				else {
					addData(data, word);
				}
			}
			else {
				data.add("0"); //0 might not be appropriate if some values can reasonably exist as 0
			}
		}
		
		return data;
	}
	
	/**
	 * Adds a singular desired data value to data in extractData()
	 * 
	 * @param data the ArrayList of Strings from extractData()
	 * @param word the desired word
	 * @post the desired data is added to data
	 */
	private void addData(ArrayList<String> data, String word) {
		int index = docInfo.indexOf(word) + word.length();
		String str = docInfo.substring(index);
		if (str.indexOf("\t") != 0) //if there's more data further 
			data.add(word + ": " + docInfo.substring(index, str.indexOf("\t")));
		else if (str.indexOf("\n") != 0) //if the data is the last of the row
			data.add(word + ": " + docInfo.substring(index, str.indexOf("\n")));
		else { //if data is at the end of the file
			data.add(word + ": " + docInfo.substring(index, str.length()));
		}
	}
	
	/**
	 * Adds all data in the row containing desired data to data in extractData()
	 * 
	 * @param data the ArrayList of Strings from extractData()
	 * @param word the desired word
	 * @post the row of desired data is added to data
	 */
	private void addLineData(ArrayList<String> data, String word) {
		
		int index = docInfo.indexOf(word) + word.length();
		String str = docInfo.substring(index);
			
			while (str.indexOf("\t") != 0) { //if there's more data further (separated by tab)
	
				String temp = docInfo.substring(index, str.indexOf("\t"));
				data.add(word + ": " + temp);
				
				index += temp.length();
				str = docInfo.substring(index);
			}
			if (str.indexOf("\n") != 0) //if the data is the last of the row
				data.add(word + ": " + docInfo.substring(index, str.indexOf("\n")));
			else { //if data is at the end of the file
				data.add(word + ": " + docInfo.substring(index, str.length()));
			}
	}
}
