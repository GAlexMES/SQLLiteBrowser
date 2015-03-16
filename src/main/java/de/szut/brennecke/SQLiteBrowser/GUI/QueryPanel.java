package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.szut.brennecke.SQLiteBrowser.SQL.SQLConnection;


@SuppressWarnings("serial")
public class QueryPanel extends JPanel{
	private JTextArea textField;
	private GUI parentFrame;
	private JComboBox databaseComboBox;
	private final String WRONG_LIMIT_INPUT = "Falsche Eingabe im Limit Feld. Diese wird ignoriert!";
	private final String WRONG_OFFSET_INPUT = "Falsche Eingabe im Offset Feld. Diese wird ignoriert!";

	
	public QueryPanel(GUI gui){
		parentFrame = gui;
		init();
	}
	
	private void init(){
		this.setLayout(new GridBagLayout());
		textField = new JTextArea();
		GridBagConstraints c = new GridBagConstraints();

		// ////////
		// COMBOBOX
		// ////////
		databaseComboBox = new JComboBox<>();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;

		this.add(databaseComboBox, c);

		// ////////
		// TEXTAREA
		// ////////
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1.0;
		c.gridy = 1;
		

		JScrollPane textScrollPane = new JScrollPane(textField);

		this.add(textScrollPane, c);

		// ////////
		// LIMIT
		// ////////

		JPanel limitPane = new JPanel(new GridBagLayout());
		GridBagConstraints limitPaneC = new GridBagConstraints();
		limitPaneC.fill = GridBagConstraints.HORIZONTAL;

		// LIMIT CHECK BOX
		final JCheckBox limitCheck = new JCheckBox();
		limitPaneC.gridy = 0;
		limitPane.add(limitCheck, limitPaneC);

		// LIMIT TEXT FIELD
		JLabel limitText = new JLabel("Limit:");
		limitPaneC.gridx = 1;
		limitPane.add(limitText, limitPaneC);

		// LIMIT START VALUE INPUT FIELD
		final JTextField limitStartInputField = new JTextField();
		limitStartInputField.setText("0");
		limitPaneC.gridx = 2;
		limitPaneC.ipadx = 20;
		limitPane.add(limitStartInputField, limitPaneC);
		limitPaneC.ipadx = 0;

		// LIMIT CHECK BOX
		final JCheckBox offsetCheck = new JCheckBox();
		limitPaneC.gridx = 3;
		limitPane.add(offsetCheck, limitPaneC);

		// LIMIT START VALUE FIELD
		JLabel limitNumberText = new JLabel("Offsett");
		limitPaneC.gridx = 4;
		limitPane.add(limitNumberText, limitPaneC);

		// LIMIT START VALUE INPUT FIELD
		final JTextField limitNumberInputField = new JTextField();
		limitNumberInputField.setText("0");
		limitPaneC.ipadx = 20;
		limitPaneC.gridx = 5;
		limitPane.add(limitNumberInputField, limitPaneC);
		limitPaneC.ipadx = 0;

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.PAGE_END;

		this.add(limitPane, c);

		// ////////
		// BUTTON
		// ////////

		JButton executeButton = new JButton("execute");
		executeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int startValue = 0;
				int numberOfValues = 0;

				if (limitCheck.isSelected()) {
					try {
						startValue = Integer.valueOf(limitStartInputField.getText());
					} catch (NumberFormatException nfe) {
						GUIController.generateWrongQuerryInfoPane(WRONG_LIMIT_INPUT);
					}
				}
				if (offsetCheck.isSelected()) {
					try {
						numberOfValues = Integer.valueOf(limitNumberInputField.getText());
					} catch (NumberFormatException nfe) {
						GUIController.generateWrongQuerryInfoPane(WRONG_OFFSET_INPUT);
					}
				}
				int[] limitValues = { startValue, numberOfValues };
				parentFrame.getGUIController().getController().sendGUIQuery(limitValues);
			}
		});

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.PAGE_END;

		this.add(executeButton, c);
	}

	public void updateComboBoxes(ArrayList<SQLConnection> sqlConnections) {
		databaseComboBox.removeAllItems();
		for (SQLConnection sqlCon : sqlConnections) {
			databaseComboBox.addItem(sqlCon.getName());
		}
	}
	
	public String getQuery(){
		return textField.getText();
	}
	
	public String getChosenDatabase(){
		return databaseComboBox.getSelectedItem().toString();
	}
}
