package de.szut.brennecke.SQLiteBrowser.DataHandling;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;


/**
 * 
 * @author Alexander Brennecke
 *
 *	This class saves the pathes to the current open databases to a .csv file.
 *	This gives the option to restore the opened databases after a restart of the software
 */
public class DatabaseProperties {
	
	//INITIALISATION
	////////////////
	private URL filePath = null;
	private Map<String, String> readedDbPathes = new HashMap<String, String>();
	private Map<String, String> newDbPathes = new HashMap<String, String>();

	
	//IMPORTANT FUNCTIONS
	/////////////////////
	/**
	 * Constructor
	 */
	public DatabaseProperties() {
		this.filePath = this.getClass().getResource("/de/szut/brennecke/SQLiteBrowser/Resources/dbProperties.csv");
	}

	/**
	 * This method writes the current paths to the .csv file
	 */
	public void write() {
		CSVWriter csvWriter;
		try {
			csvWriter = new CSVWriter(new FileWriter(filePath.getPath(),false));
			ArrayList<String> dbKeys = new ArrayList<String>(newDbPathes.keySet());
			for(int i= 0; i<newDbPathes.size();i++) {
				String key = dbKeys.get(i);
				String[] line = {key, newDbPathes.get(key)};
				csvWriter.writeNext(line);
			}
			csvWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method reads the .csv file and saves the paths to a ArrayList
	 */
	public void read() {
		CSVReader csvReader;
		try {
			csvReader = new CSVReader(new FileReader(filePath.getPath()), ',','"', 0);
			String[] nextLine;
			while ((nextLine = csvReader.readNext()) != null) {
				readedDbPathes.put(nextLine[0],nextLine[1]);
			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Replaces every \ with a \\
	 * @param path that should be modified
	 * @return modified path
	 */
	private String generateSlash (String path){
		path = path.replace("\\", "\\\\");
		return path;
	}

	//GETTER&SETTER
	///////////////
	public List<String> getReadedDBPathes() {
		List<String> dbPathesList = new ArrayList<>(readedDbPathes.values());
		return dbPathesList;
	}
	
	public void addDatabasse(String name, String path) {
		path = generateSlash(path);
		newDbPathes.put(name, path);
		write();
	}
	
	public void removeDatabase(String name){
		newDbPathes.remove(name);
		write();
	}
}
