package de.szut.brennecke.SQLiteBrowser.DataHandling;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;

public class CSVImporter {
	
	/**
	 * This method reads the .csv file
	 */
	public static ArrayList<String[]> read(String filePath) {
		CSVReader csvReader;
		ArrayList<String[]> retval = new ArrayList<>();
		try {
			csvReader = new CSVReader(new FileReader(filePath), ';','"', 0);
			String [] nextLine;
			while ((nextLine = csvReader.readNext()) != null) {
				retval.add(nextLine);
			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retval;
	}

}
