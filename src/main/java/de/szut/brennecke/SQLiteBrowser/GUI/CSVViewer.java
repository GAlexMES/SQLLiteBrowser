package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class CSVViewer extends JFrame{
	
	private JTable table;
	private ArrayList<String[]> values;
	
	public CSVViewer(ArrayList<String[]> values){
		this.setVisible(true);
		this.values = values;
		this.setSize(500, 500);
		generateTable();
	}
	
	private void generateTable(){
		DefaultTableModel dtm = new DefaultTableModel();
		
		String[] identifiers = new String[values.get(0).length];
		dtm.setColumnIdentifiers(identifiers);
		
		for(String[] row : values){
			dtm.addRow(row);
		}
		
		table = new JTable(dtm);
		table.getTableHeader().addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent event){
				int coloumIndex = table.convertColumnIndexToModel(table.columnAtPoint(event.getPoint()));
				System.out.println(coloumIndex);
			}
		});
		JScrollPane scroll = new JScrollPane(table);
		
		this.getContentPane().add(scroll);
	}

}
