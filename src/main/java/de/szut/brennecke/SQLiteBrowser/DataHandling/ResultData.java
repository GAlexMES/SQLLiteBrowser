package de.szut.brennecke.SQLiteBrowser.DataHandling;

public class ResultData {
	
	Object[] columnNames;
	Object[][] data;
	
	public ResultData(Object[] columnNames, Object[][] data){
		this.columnNames=columnNames;
		this.data=data;
	}
	public Object[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(Object[] columnNames) {
		this.columnNames = columnNames;
	}
	public Object[][] getData() {
		return data;
	}
	public void setData(Object[][] data) {
		this.data = data;
	}
	
	

}
