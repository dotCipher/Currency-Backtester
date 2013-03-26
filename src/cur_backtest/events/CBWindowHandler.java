package cur_backtest.events;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import cur_backtest.CBFileManager;

public class CBWindowHandler implements WindowListener{

	public void windowClosing(WindowEvent arg0) {
		CBFileManager.processExitProgramRequest();
	}
	
	public void windowActivated(WindowEvent arg0) 	{	}
	public void windowClosed(WindowEvent arg0) 		{	}
	public void windowDeactivated(WindowEvent arg0) {	}
	public void windowDeiconified(WindowEvent arg0) {	}
	public void windowIconified(WindowEvent arg0) 	{	}
	public void windowOpened(WindowEvent arg0) 		{	}
}
