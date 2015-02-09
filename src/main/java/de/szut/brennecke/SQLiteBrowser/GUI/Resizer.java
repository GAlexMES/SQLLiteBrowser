package de.szut.brennecke.SQLiteBrowser.GUI;


public class Resizer implements Runnable {

	private GUI mainFrame;
	public Resizer(GUI mainFrame){
		this.mainFrame = mainFrame;
	}
    public void run() {
    	int w = mainFrame.getWidth();
        int h = mainFrame.getHeight();
        while(true)
        {
            if(w != mainFrame.getWidth() || h!= mainFrame.getHeight())
            {
            	mainFrame.updateComponents();
                w = mainFrame.getWidth();
                h = mainFrame.getHeight();
            }
            try{Thread.sleep(1);} catch(InterruptedException e) {}
        }
    }

}
