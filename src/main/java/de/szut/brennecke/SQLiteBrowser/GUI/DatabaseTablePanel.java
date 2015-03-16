package de.szut.brennecke.SQLiteBrowser.GUI;

import java.sql.ResultSet;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import de.szut.brennecke.SQLiteBrowser.DataHandling.ResultWorkup;

@SuppressWarnings("serial")
public class DatabaseTablePanel extends JPanel{
	
	private  JTable resultTable;
	private  JScrollPane scrollPane;
	private GUI parentFrame;
	
	public DatabaseTablePanel(GUI gui){
		parentFrame = gui;
		init();
	}
	
	private void init(){
		resultTable = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.add(resultTable);
		this.add(scrollPane);
	}
	
	/**
	 * This method exchanges the TableModel with a new one
	 * 
	 * @param rs
	 * @param mainFrame
	 */
	public void updateTable(ResultSet rs) {
		DefaultTableModel resultData = null;
		resultData = ResultWorkup.getTabularDatas(rs);
		resultTable.setAutoCreateRowSorter(true);
		resultTable.setModel(resultData);
		scrollPane.removeAll();
		scrollPane.add(resultTable);
		this.removeAll();
		this.add(scrollPane);
	}

}
