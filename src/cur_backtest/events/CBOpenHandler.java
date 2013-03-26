package cur_backtest.events;

import java.awt.event.*;

import cur_backtest.CBFileManager;

public class CBOpenHandler implements ActionListener{
	public void actionPerformed(ActionEvent ae){
		CBFileManager.processOpenFileRequest();
	}
}
