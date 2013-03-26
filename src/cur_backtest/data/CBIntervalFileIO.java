package cur_backtest.data;

import java.io.*;
import java.util.*;
/**
 * @author Cody Moore
 */
public class CBIntervalFileIO{
	// [------------------- FOR TESTING ONLY -------------------]
	private static String PATH_CONST = "./setup/test001.csv";
	// [--------------------------------------------------------]
	public static CBIntervalTable loadTable(File file) throws IOException{
		CBIntervalTable intervalTable = new CBIntervalTable();
		
		BufferedReader in = new BufferedReader(
				new FileReader(file.getAbsolutePath()));
		String str;
		int i=0;
		double tempHigh = 0.0;
		double tempLow = 99999.99;
		CBIntervalRecord prevRecord = null;
		while ((str = in.readLine()) != null) {
			// Parse table intervals
			CBIntervalRecord nextRecord = parseIntervalToTable(intervalTable, str);
			
			// Link records
			if(prevRecord != null){
				prevRecord.setNextRecord(nextRecord);
				nextRecord.setPrevRecord(prevRecord);
			}
			
			// Parse table highs and lows
			if(intervalTable.getRecord(i).getHighValue() > tempHigh){
				tempHigh = intervalTable.getRecord(i).getHighValue();
			}
			if(intervalTable.getRecord(i).getLowValue() < tempLow){
				tempLow = intervalTable.getRecord(i).getLowValue();
			}
			prevRecord = nextRecord;
			
			i++;
		}
		in.close();
		intervalTable.setTableHigh(tempHigh);
		intervalTable.setTableLow(tempLow);
		return intervalTable;
	}
	
	public static CBIntervalRecord parseIntervalToTable(CBIntervalTable table, String interval){
		// - FULL DATE
		String fullDate = interval.substring(interval.indexOf('\"')+1, interval.lastIndexOf('\"'));
		//System.out.println(fullDate);
		
		// GET DAY
		String day = fullDate.substring(0, fullDate.indexOf('.'));
		int dayInt = Integer.parseInt(day);
		//System.out.println("Day:	" + day);
		
		// GET MONTH
		String month = fullDate.substring(fullDate.indexOf('.')+1, fullDate.lastIndexOf('.'));
		int monthInt = Integer.parseInt(month);
		//System.out.println("Month:	" + month);
		
		// GET MONTH
		String year = fullDate.substring(fullDate.lastIndexOf('.')+1);
		int yearInt = Integer.parseInt(year);
		//System.out.println("Year:	" + year);
		//System.out.println();
		
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
		//System.out.println("Hour:	" + hour);
		String min = fullTime.substring(dateCut.indexOf(':')+1, fullTime.indexOf(' '));
		int minInt = Integer.parseInt(min);
		//System.out.println("Min:	" + min);
		//System.out.println();
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
		//System.out.println("High:	" + highVal);
		
		// GET LOW VALUE
		String lowVal = timeCut.substring(timeCut.indexOf(',')+1);
		double lowValD = Double.parseDouble(lowVal);
		//System.out.println("Low:	" + lowVal);
		//System.out.println();
		
		// - CREATE CALENDAR VARIABLE FOR TIMESTAMP OF INTERVAL
		GregorianCalendar newCal = new GregorianCalendar(yearInt, monthInt, dayInt, 
				hourInt, minInt, secInt);
		newCal.set(Calendar.AM_PM, ampmInt);
		
		// - CREATE NEW INTERVAL RECORD
		CBIntervalRecord newRecord = new CBIntervalRecord(newCal, highValD, lowValD);
		
		// - ADD RECORD TO INTERVAL RECORD VECTOR IN INTERVAL TABLE
		table.addRecord(newRecord);
		return newRecord;
	}
	
	// [------------------- FOR TESTING ONLY -------------------]
	/*
	public static void main(String[] args){
		try {
			CBIntervalFileIO.loadTable(new File(PATH_CONST));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	// [--------------------------------------------------------]
}