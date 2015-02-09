package de.szut.brennecke.SQLiteBrowser.DataHandling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Properties;

public class FrameProperties {
	URL filePath = this.getClass().getResource("/de/szut/brennecke/SQLiteBrowser/Resources/props.ini");
	
	private int mainFrameWidth;
	private int mainFrameHeight;
	private int mainFrameLocationX;
	private int mainFrameLocationY;
	private int splitPaneLocation;
	
	private Properties properties = new Properties();
	
	public FrameProperties(){
		read();
	}
	
	public void read(){
			try {
				properties.load(new FileInputStream(filePath.getPath()));
				this.mainFrameWidth = Integer.valueOf(properties.getProperty("mainFrameWidth"));
				this.mainFrameHeight = Integer.valueOf(properties.getProperty("mainFrameHeight"));
				this.mainFrameLocationX = Integer.valueOf(properties.getProperty("mainFrameLocationX"));
				this.mainFrameLocationY = Integer.valueOf(properties.getProperty("mainFrameLocationY"));
				this.splitPaneLocation = Integer.valueOf(properties.getProperty("splitPaneLocation"));
			} catch (Exception eoh) {
				System.out.println(eoh);
			}
	}
	
	public void write(){
		try {
			properties.load(new FileInputStream(filePath.getPath()));
			properties.put("mainFrameWidth", String.valueOf(mainFrameWidth));
			properties.put("mainFrameHeight", String.valueOf(mainFrameHeight));
			properties.put("mainFrameLocationX", String.valueOf(mainFrameLocationX));
			properties.put("mainFrameLocationY", String.valueOf(mainFrameLocationY));
			properties.put("splitPaneLocation", String.valueOf(splitPaneLocation));
			FileOutputStream out = new FileOutputStream(filePath.getPath());
			properties.store(out,"");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public int getMainFrameWidth() {
		return mainFrameWidth;
	}

	public void setMainFrameWidth(int mainFrameWidth) {
		this.mainFrameWidth = mainFrameWidth;
	}

	public int getMainFrameHeight() {
		return mainFrameHeight;
	}

	public void setMainFrameHeight(int mainFrameHeight) {
		this.mainFrameHeight = mainFrameHeight;
	}

	public int getSplitPaneLocation() {
		return splitPaneLocation;
	}

	public void setSplitPaneLocation(int splitPaneLocation) {
		this.splitPaneLocation = splitPaneLocation;
	}

	public int getMainFrameLocationX() {
		return mainFrameLocationX;
	}

	public void setMainFrameLocationX(int mainFrameLocationX) {
		this.mainFrameLocationX = mainFrameLocationX;
	}

	public int getMainFrameLocationY() {
		return mainFrameLocationY;
	}

	public void setMainFrameLocationY(int mainFrameLocationY) {
		this.mainFrameLocationY = mainFrameLocationY;
	}	

}
