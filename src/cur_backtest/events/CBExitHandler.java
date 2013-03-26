package cur_backtest.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cur_backtest.CBFileManager;

public class CBExitHandler implements ActionListener{
	public void actionPerformed(ActionEvent ae){
		CBFileManager.processExitProgramRequest();
	}
}
