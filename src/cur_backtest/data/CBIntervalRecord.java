package cur_backtest.data;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Cody Moore
 */
public class CBIntervalRecord {
	private GregorianCalendar dateAndTime;
	private double highValue;
	private double lowValue;
	private CBIntervalRecord nextRecord;
	private CBIntervalRecord prevRecord;
	private boolean increasing;

	public CBIntervalRecord(GregorianCalendar initDateAndTime, 
			double initHighValue, double initLowValue){
		dateAndTime = initDateAndTime;
		highValue = initHighValue;
		lowValue = initLowValue;
	}
	
	// ACCESSOR METHODS
	public int 		getDay() 			{ return dateAndTime.get(Calendar.DAY_OF_MONTH); 	}
	public int		getMonth()			{ return dateAndTime.get(Calendar.MONTH);			}
	public int		getYear()			{ return dateAndTime.get(Calendar.YEAR); 			}
	public String	getDate(){
		String month = "";
		if(Integer.toString(dateAndTime.get(Calendar.MONTH)).equalsIgnoreCase("0")){
			month = "12";
		}else{
			month =Integer.toString(dateAndTime.get(Calendar.MONTH));
		}
		return month + "/" 
	+ Integer.toString(dateAndTime.get(Calendar.DAY_OF_MONTH)) + "/" 
	+ Integer.toString(dateAndTime.get(Calendar.YEAR));
	}
	
	// NOTE:  HOUR OF DAY IS BASED ON 24 HOUR CLOCK
	public int		getHourOfDay()		{ return dateAndTime.get(Calendar.HOUR_OF_DAY);		}
	// NOTE:  HOUR IS BASED IN A 12 HOUR CLOCK
	public int 		getHour() 			{ return dateAndTime.get(Calendar.HOUR);			}
	public int 		getMin() 			{ return dateAndTime.get(Calendar.MINUTE);			}
	public int 		getSecond() 		{ return dateAndTime.get(Calendar.SECOND);			}
	public int 		getMilliSecond()	{ return dateAndTime.get(Calendar.MILLISECOND);		}
	public int		getAMPM()			{ return dateAndTime.get(Calendar.AM_PM);			}
	public String	getTime(){
		String ampm = "";
		if(dateAndTime.get(Calendar.AM_PM) == Calendar.PM){
			ampm = "PM";
		}
		else{
			ampm = "AM";
		}
		String hour = "";
		if(Integer.toString(dateAndTime.get(Calendar.HOUR)).equalsIgnoreCase("0")){
			hour = "12";
		}
		else{
			hour = Integer.toString(dateAndTime.get(Calendar.HOUR));
		}
		String min = "";
		if(Integer.toString(dateAndTime.get(Calendar.MINUTE)).equalsIgnoreCase("0")){
			min = "00";
		}
		else{
			min = Integer.toString(dateAndTime.get(Calendar.MINUTE));
		}
		return hour + ":" + min + " " + ampm;
	}
	
	public double	getHighValue()		{ return highValue;									}
	public double	getLowValue()		{ return lowValue;									}
	public boolean	getIsIncreasing()	{ return increasing;								}
	
	public CBIntervalRecord	getNext()	{ return nextRecord;								}
	public CBIntervalRecord	getPrev()	{ return prevRecord;								}
	
	// MUTATOR METHODS
	public void setIncreasing(boolean initIncreasing){
		increasing = initIncreasing;
	}
	public void	setPrevRecord(CBIntervalRecord newPrevRecord){
		prevRecord = newPrevRecord;
		// set increasing
		if(prevRecord.getHighValue() < highValue){
			increasing = true;
		}
		else if(prevRecord.getHighValue() > highValue){
			increasing = false;
		}
		else{
			// optimistic solution if both are equal
			increasing = true;
		}
	}
	public void setNextRecord(CBIntervalRecord newNextRecord){
		nextRecord = newNextRecord;
	}
	
}