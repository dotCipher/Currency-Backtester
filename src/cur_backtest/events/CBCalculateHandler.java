package cur_backtest.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cur_backtest.CBFileManager;
import cur_backtest.CBIntervalCalculation;

public class CBCalculateHandler implements ActionListener{
	public void actionPerformed(ActionEvent ae){
		CBFileManager.setupCalculation();
		CBIntervalCalculation.runCalculations();
	}
}
