package cur_backtest.data;

import java.io.*;
import java.util.*;
/**
 * @author Cody Moore
 */
public class IntervalFileIO{
	private static String PATH_CONST = "./setup/test001.csv";
	public static IntervalTable loadTable(File file) throws IOException{
		IntervalTable intervalTable = new IntervalTable();
		
		BufferedReader in = new BufferedReader(
				new FileReader(file.getAbsolutePath()));
		String str;
		int i=0;
		double tempHigh = 0.0;
		double tempLow = 999.99;
		while ((str = in.readLine()) != null) {
			// Parse table intervals
			parseIntervalToTable(intervalTable, str);
			
			// Parse table highs and lows
			if(intervalTable.getRecord(i).getHighValue() > tempHigh){
				tempHigh = intervalTable.getRecord(i).getHighValue();
			}
			if(intervalTable.getRecord(i).getLowValue() < tempLow){
				tempLow = intervalTable.getRecord(i).getLowValue();
			}
			i++;
		}
		in.close();
		
		return intervalTable;
	}
	
	public static void parseIntervalToTable(IntervalTable table, String interval){
		// - FULL DATE
		String fullDate = interval.substring(interval.indexOf('\"')+1, interval.lastIndexOf('\"'));
		//System.out.println(fullDate);
		
		// GET DAY
		String day = fullDate.substring(0, fullDate.indexOf('.'));
		int dayInt = Integer.parseInt(day);
		//System.out.println(day);
		
		// GET MONTH
		String month = fullDate.substring(fullDate.indexOf('.')+1, fullDate.lastIndexOf('.'));
		int monthInt = Integer.parseInt(month);
		//System.out.println(month);
		
		// GET MONTH
		String year = fullDate.substring(fullDate.lastIndexOf('.')+1);
		int yearInt = Integer.parseInt(year);
		//System.out.println(year);
		
		// - - - - - PARSE OUT DATE, WE ARE DONE WITH IT - - - - - //
		String dateCut = interval.substring(interval.indexOf(',')+1);
		//System.out.println(dateCut);
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
		
		// - FULL TIME
		String fullTime = dateCut.substring(0, dateCut.indexOf(','));
		//System.out.println(fullTime);
		
		// GET TIME
		String hour = fullTime.substring(0, dateCut.indexOf(':'));
		int hourInt = Integer.parseInt(hour);
		//System.out.println(hour);
		String min = fullTime.substring(dateCut.indexOf(':')+1, fullTime.indexOf(' '));
		int minInt = Integer.parseInt(min);
		//System.out.println(min);
		// SECONDS CAN BE IMPLEMENTED HERE
		int secInt = 00;
		// MILLISECONDS CAN BE IMPLEMENTED HERE
		
		// GET AM/PM
		String ampm = fullTime.substring(fullTime.indexOf(' ')+1);
		int ampmInt;
		if(ampm.equalsIgnoreCase("PM")){
			ampmInt = Calendar.PM;
		}else{
			ampmInt = Calendar.AM;
		}
		//System.out.println(ampm);
		
		// - - - - - PARSE OUT TIME, WE ARE DONE WITH IT - - - - - //
		String timeCut = dateCut.substring(dateCut.indexOf(',')+1);
		//System.out.println(timeCut);
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
		
		// GET HIGH VALUE
		String highVal = timeCut.substring(0, timeCut.indexOf(','));
		double highValD = Double.parseDouble(highVal);
		//System.out.println(highVal);
		
		// GET LOW VALUE
		String lowVal = timeCut.substring(timeCut.indexOf(',')+1);
		double lowValD = Double.parseDouble(lowVal);
		//System.out.println(lowVal);
		
		// - CREATE CALENDAR VARIABLE FOR TIMESTAMP OF INTERVAL
		GregorianCalendar newCal = new GregorianCalendar(yearInt, monthInt, dayInt, 
				hourInt, minInt, secInt);
		newCal.set(Calendar.AM_PM, ampmInt);
		
		// - CREATE NEW INTERVAL RECORD
		IntervalRecord newRecord = new IntervalRecord(newCal, highValD, lowValD);
		
		// - ADD RECORD TO INTERVAL RECORD VECTOR IN INTERVAL TABLE
		table.addRecord(newRecord);
	}
	
	/*
	public static void main(String[] args){
		try {
			IntervalFileIO.loadTable(new File(PATH_CONST));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
}