package cur_backtest.data;

import java.util.*;

/**
 * @author Cody Moore
 */
public class IntervalTable {
	// ROW DATA
	private ArrayList<IntervalRecord> records;
	private double tableHigh;
	private double tableLow;
	
	/**
	 * This default constructor simply sets up the fields and records vectors.
	 */
	public IntervalTable(){
		records = new ArrayList<IntervalRecord>();
	}

	// ACCESSOR METHODS
	public IntervalRecord		getRecord(int index)				{ return records.get(index);			}
	public int 					getNumRecords()						{ return records.size();				}
	public double				getTableHigh()						{ return tableHigh;						}
	public double				getTableLow()						{ return tableLow;						}
	
	// ITERATOR METHODS
	// - THESE WILL RETURN ITERATORS FOR GOING THROUGH ALL THE DATA IN THE TABLE
	public Iterator<IntervalRecord> 	recordsIterator()	{ return records.iterator(); 	}
	
	// MUTATOR METHODS	
	public void addRecord(IntervalRecord newRecord)	{ records.add(newRecord);		}
	public void setTableHigh(double newHigh)		{ tableHigh = newHigh;			}
	public void setTableLow(double newLow)			{ tableLow = newLow;			}
	
}