package de.szut.brennecke.SQLiteBrowser.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

public class TreeMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(SwingUtilities.isRightMouseButton(arg0)){
			System.out.println("POPUP!");
		}
			

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
