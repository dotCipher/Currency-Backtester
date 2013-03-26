package cur_backtest;

import java.util.*;

import cur_backtest.data.*;

public class CBIntervalCalculation {
	private static CBIntervalTable table;
	private double scale;
	private double momentum;
	
	private int successfulLongs;
	private int failedLongs;
	private int successfulShorts;
	private int failedShorts;
	
	private double succussfulLongPercent;
	private double failedLongPercent;
	private double successfulShortPercent;
	private double failedShortPercent;
	
	public CBIntervalCalculation(CBIntervalTable initTable, 
			double initScale, double initMomentum){
		// Table that was loaded from file input
		table = initTable;
		
		// Values selection input: (1.0, 0.1, 0.01, or 0.001)
		// (default = 0.1)
		if(initScale == 1.0 || initScale == 0.1 
				|| initScale == 0.01 || initScale == 0.001){
			scale = initScale;
		}
		else{
			scale = 0.1;
		}
		
		// Values selection input: (0.01-0.99) correct final for 1% or 99%
		//	(default = 0.75 or 75%)
		if(initMomentum >= 0.01 && initMomentum <= 0.99){
			momentum = initMomentum;
		}
		else{
			momentum = 0.75;
		}
		momentum = initMomentum; 
	}
	
	public static void runCalculations(){
		if(table != null){
			int[] finalVals = calculateFinalIntValues();
			// TODO: Finish this method to set up all final values in object
			
			CBFileManager.outputCalculated();
		}
	}
	
	// Precondition:  table, scale, and momentum must be instantiated
	public static int[] calculateFinalIntValues(){
		// result[0] = successfulLongs
		// result[1] = failedLongs
		// result[2] = successfulShorts
		// result[3] = failedShorts
		int[] result = new int[4];
		Iterator<CBIntervalRecord> records = table.recordsIterator();
		CBIntervalRecord tempRecord = null;
		
		// Create buying or selling value arrayList
		ArrayList<Double> setValues = new ArrayList<Double>();
		
		// Go through each element
		while(records.hasNext()){
			tempRecord = records.next();
			// Last iteration?
			if(tempRecord.getNext() == null){
				break;
			}
			double lowCur = tempRecord.getLowValue();
			double highCur = tempRecord.getHighValue();
			double lowNext = tempRecord.getNext().getLowValue();
			double highNext = tempRecord.getNext().getHighValue();
			
			/* TODO:  THE PART OF CODE I AM UP TOO */
			// NOTE: isOrderOpen() will check if elements 
			//		are in the OrderStack
			// ALGORITHM USED:
			// 1a)  Remember starting point average of first high and low points
			// 1b)  point_index = 0
			// 2)  Calculate upwards and downwards scale points
			// 3)  If Low/High of the next value from array[point_index] hits
			//     "Watch" and isOrderOpen() returns true;
			//       do OrderStack++;
			//		 add to stack the new order
			// 4)  If Low/High of next value from array[point_index] hits 
			//     "Buy or Sell" Level,
			//        do buy_num++; or sell_num++; 
			//        and point_index++;
			//      end;
			// 5a)  If Low/High of next value from array[point_index] hits
			//	  	next scale level for any outstanding orders then,
			//			do good_buy_num++; or good_sell_num++;
			//			and point_index++;
			//		end;
			// 5b)	If Low/High value of the next value from array[point_index] hit
			//		original "Watch" value, then:
			//			do bad_buy_num++; or bad_sell_num++;
			//			and point_index++;
			//		end;
			// 6)	If NextCBInterval() == null then:
			//			If isOrderOpen() == true then:
			//				CloseAllOrders();
			//			ExitLoop();
			//			Display Results();
		}
		
		return null;
	}
}