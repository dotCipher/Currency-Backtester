package cur_backtest.data;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Cody Moore
 */
public class IntervalRecord {
	private static GregorianCalendar dateAndTime;
	private static double highValue;
	private static double lowValue;
	private IntervalRecord nextRecord;
	private IntervalRecord prevRecord;
	private boolean increasing;

	public IntervalRecord(GregorianCalendar initDateAndTime, 
			double initHighValue, double initLowValue){
		dateAndTime = initDateAndTime;
		highValue = initHighValue;
		lowValue = initLowValue;
	}
	
	// ACCESSOR METHODS
	public int 		getDay() 			{ return dateAndTime.get(Calendar.DAY_OF_MONTH); 	}
	public int		getMonth()			{ return dateAndTime.get(Calendar.MONTH);			}
	public int		getYear()			{ return dateAndTime.get(Calendar.YEAR); 			}
	
	// NOTE:  HOUR OF DAY IS BASED ON 24 HOUR CLOCK
	public int		getHourOfDay()		{ return dateAndTime.get(Calendar.HOUR_OF_DAY);		}
	// NOTE:  HOUR IS BASED IN A 12 HOUR CLOCK
	public int 		getHour() 			{ return dateAndTime.get(Calendar.HOUR);			}
	public int 		getMin() 			{ return dateAndTime.get(Calendar.MINUTE);			}
	public int 		getSecond() 		{ return dateAndTime.get(Calendar.SECOND);			}
	public int 		getMilliSecond()	{ return dateAndTime.get(Calendar.MILLISECOND);		}
	public int		getAMPM()			{ return dateAndTime.get(Calendar.AM_PM);			}
	
	public double	getHighValue()		{ return highValue;									}
	public double	getLowValue()		{ return lowValue;									}
	public boolean	getIsIncreasing()	{ return increasing;								}
	
	public IntervalRecord	getNext()	{ return nextRecord;								}
	public IntervalRecord	getPrev()	{ return prevRecord;								}
	
	// MUTATOR METHODS
	public void setIncreasing(boolean initIncreasing){
		increasing = initIncreasing;
	}
	public void	setPrevRecord(IntervalRecord newPrevRecord){
		prevRecord = newPrevRecord;
		// set increasing
		if(prevRecord.getHighValue() < this.getHighValue()){
			increasing = true;
		}
		else if(prevRecord.getHighValue() > this.getHighValue()){
			increasing = false;
		}
		else{
			// optimistic solution if both are equal
			increasing = true;
		}
	}
	public void setNextRecord(IntervalRecord newNextRecord){
		nextRecord = newNextRecord;
		// set increasing
		if(nextRecord.getHighValue() < this.getHighValue()){
			nextRecord.setIncreasing(false);
		}
		else if(nextRecord.getHighValue() > this.getHighValue()){
			nextRecord.setIncreasing(true);
		}
		else{
			// optimistic solution if both are equal
			increasing = true;
		}
	}
	
}