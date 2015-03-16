package de.szut.brennecke.SQLiteBrowser.GUI;

import java.sql.ResultSet;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import de.szut.brennecke.SQLiteBrowser.DataHandling.ResultWorkup;

@SuppressWarnings("serial")
public class DatabaseTablePanel extends JTable{
	
	private JTable resultTable;
	private JScrollPane scroll;
	
	
	public DatabaseTablePanel(GUI gui){
		init();
	}
	
	private void init(){
		resultTable = new JTable();
		resultTable.setAutoCreateRowSorter(true);
	}
	
	/**
	 * This method exchanges the TableModel with a new one
	 * 
	 * @param rs
	 * @param mainFrame
	 */
	public void updateTable(ResultSet rs) {
		DefaultTableModel resultDataModel = null;
		resultDataModel = ResultWorkup.getTabularDatas(rs);
		resultTable.setModel(resultDataModel);
		scroll = new JScrollPane(resultTable);
	}
	
	public JScrollPane getPane(){
		return scroll;
	}

}
