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
	 * @param name the filename
	 * @throws FileNotFoundException 
	 * @post docInfo is appended by the data in the file
	 */
	public void addFile() {
		
		Path path = Paths.get("financials.txt");
		try {
			docInfo = Files.readString(path, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			
		}
		
		
        
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
				int index = docInfo.indexOf(word) + word.length();
				String str = docInfo.substring(index);
				data.add(word + ": " + docInfo.substring(index, str.indexOf(" ")) + "\n");
			}
		}
		
		return data;
	}
}
