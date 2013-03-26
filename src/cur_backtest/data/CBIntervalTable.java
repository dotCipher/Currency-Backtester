package cur_backtest.data;

import java.util.*;

/**
 * @author Cody Moore
 */
public class CBIntervalTable {
	// ROW DATA
	private ArrayList<CBIntervalRecord> records;
	private double tableHigh;
	private double tableLow;
	private String startDate;
	private String endDate;
	private int numFields;
	
	/**
	 * This default constructor simply sets up the fields and records vectors.
	 */
	public CBIntervalTable(){
		records = new ArrayList<CBIntervalRecord>();
		numFields = 4;
	}
	
	// ACCESSOR METHODS
	public int					getNumFields()						{ return numFields;						}
	public CBIntervalRecord		getRecord(int index)				{ return records.get(index);			}
	public int 					getNumRecords()						{ return records.size();				}
	public double				getTableHigh()						{ return tableHigh;						}
	public double				getTableLow()						{ return tableLow;						}
	public String				getStartDate()						{ return startDate;						}
	public String				getEndDate()						{ return endDate;						}
	
	// ITERATOR METHODS
	// - THESE WILL RETURN ITERATORS FOR GOING THROUGH ALL THE DATA IN THE TABLE
	public Iterator<CBIntervalRecord> 	recordsIterator()	{ return records.iterator(); 	}
	
	// MUTATOR METHODS	
	public void addRecord(CBIntervalRecord newRecord)	{ records.add(newRecord);		}
	public void setTableHigh(double newHigh)			{ tableHigh = newHigh;			}
	public void setTableLow(double newLow)				{ tableLow = newLow;			}
	public void setNumFields(int newNum)				{ numFields = newNum;			}
	public void initDateRanges()						{
		startDate = getRecord(0).getDate();
		endDate = getRecord(getNumRecords()-1).getDate();
	}
}