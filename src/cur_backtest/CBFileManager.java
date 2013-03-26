package cur_backtest;

import java.awt.Component;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.*;

import cur_backtest.data.*;

public class CBFileManager {
	private static boolean outputGenerated = false;
	private static CBIntervalTable myTable;
	private static CBIntervalCalculation myCalc;
	
	public static void setIntervalTable(CBIntervalTable newTable){
		myTable = newTable;
	}
	
	public static void setIntervalCalculation(CBIntervalCalculation newCalc){
		myCalc = newCalc;
	}
	
	public static CBIntervalTable getIntervalTable(){
		return myTable;
	}
	
	public static CBIntervalCalculation getIntervalCalculation(){
		return myCalc;
	}
	
	public static void outputCleared(){
		outputGenerated = false;
	}
	
	public static void outputCalculated(){
		outputGenerated = true;
	}
	
	public static void processExitProgramRequest() {
		if(outputGenerated){
			verifyOutput();
			verifyQuit();
		}
		else{
			verifyQuit();
		}
	}
	
	private static void verifyQuit(){
		int selection = JOptionPane.showConfirmDialog(CBGUI.getWindow(),
				"Are you sure you want to quit?", 
				"Exit Currency Backtester?", JOptionPane.YES_NO_OPTION);
		if(selection == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	public static boolean verifyOutput(){
		int selection = JOptionPane.showConfirmDialog(
				CBGUI.getWindow(), "Would you like to save the ouput " +
						"that has been calculated from this table?", 
						"Save output?", 
						JOptionPane.YES_NO_CANCEL_OPTION);
		// YES
		if(selection == JOptionPane.YES_OPTION){
			try{
				saveOutput();
				return true;
			}catch(IOException ioe){
				JOptionPane.showMessageDialog(CBGUI.getWindow(), "Error saving file");
			}
		}
		// NO
		else if(selection == JOptionPane.NO_OPTION){
			return true;
		}
		// CANCEL
		return false;
	}
	
	public static void processOpenFileRequest(){
		promptToOpen();
	}
	
	private static void promptToOpen(){
		JFileChooser openDialog = new JFileChooser();
		openDialog.setFileFilter(new CBCSVFileFiler());
		int result = openDialog.showOpenDialog(CBGUI.getWindow());
		if(result==JFileChooser.APPROVE_OPTION){
			File file = openDialog.getSelectedFile();
			if(file==null){
				JOptionPane.showMessageDialog(
						CBGUI.getWindow(), "Error: No File Selected.", 
						"No File Selected", JOptionPane.ERROR_MESSAGE);
			}
			else{
				// read in data
				try{
					CBIntervalTable intervalTable =  CBIntervalFileIO.loadTable(file);
					CBFileManager.setIntervalTable(intervalTable);
					CBFileManager.loadTableIntoDisplay(intervalTable);
				}catch(Exception e){
					// Problem reading in data
					e.printStackTrace();
					JOptionPane.showMessageDialog(CBGUI.getWindow(), 
							"Error Loaing " + file.getName() + ": Either " +
									"the file is in the incorrect format " +
									"or it is being loaded improperly", 
							"Error Loading " + file.getName(), 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	public static void loadTableIntoDisplay(CBIntervalTable intTable){
		@SuppressWarnings("serial")
		DefaultTableModel tableModel = new DefaultTableModel(){
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};

		JTable table = CBGUI.getTable();
		table.setModel(tableModel);
		// TODO: CHANGE THESE VALUES IF NUMBER OF FIELDS
		// CHANGES FROM 4!!!!!!!!!!!!!!!
		// SET NUMBER OF FIELDS
		tableModel.addColumn("Date");
		tableModel.addColumn("Time");
		tableModel.addColumn("High");
		tableModel.addColumn("Low");
		// Set rows up
		int row = 0;
		while(row < intTable.getNumRecords()){
			CBIntervalRecord currentRecord = intTable.getRecord(row);
			Vector<String> rowData = new Vector<String>();
			for(int col = 0; col<intTable.getNumFields(); col++){
				String cellText = "";
				// compile date
				if(col == 0){
					cellText = currentRecord.getDate();
				}
				// compile time
				if(col == 1){
					cellText = currentRecord.getTime();
				}
				// compile high
				if(col == 2){
					cellText = Double.toString(currentRecord.getHighValue());
				}
				// compile low
				if(col == 3){
					cellText = Double.toString(currentRecord.getLowValue());
				}
				rowData.add(cellText);
			}
			tableModel.addRow(rowData);
			row++;
		}
		// RESIZE THE COLUMNS TO FIT THE DATA
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		autoSizeAllColumns();
		// Update table stats
		intTable.initDateRanges();
		updateTableStats(intTable);
	}
	
	public static void updateTableStats(CBIntervalTable table){
		CBGUI.setTblHighField(Double.toString(table.getTableHigh()));
		CBGUI.setTblLowField(Double.toString(table.getTableLow()));
		table.initDateRanges();
		CBGUI.setDRangeField1(table.getStartDate());
		CBGUI.setDRangeField2(table.getEndDate());
	}
	
	public static void setupCalculation(){
		// Set up CB Interval Calculation
		double initScale = CBGUI.getScale();
		// TODO: Add functionality for multiple algorithms
		// Get algorithm
		//Object initAlg = CBGUI.getAlgorithm();
		// TODO: Add functionality for different momentums
		// Use a default of 0.75 or 75%
		double initMomentum = 0.75;
		
		CBIntervalCalculation newCalc = new CBIntervalCalculation(myTable, initScale, initMomentum);
		CBFileManager.setIntervalCalculation(newCalc);
	}
	
	public static void autoSizeAllColumns(){
		JTable table = CBGUI.getTable();
	    for (int i = 0; i < table.getColumnCount(); i++){
	        autoSizeColumn(i);
	    }
	}
	
	public static void autoSizeColumn(int columnIndex){
		// GET THE COLUMN DATA
		JTable table = CBGUI.getTable();
	    DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
	    TableColumn column = columnModel.getColumn(columnIndex);
	    int columnWidth = 0;

	    // AND GET THE RENDERER FOR THIS COLUMN
	    TableCellRenderer renderer = column.getHeaderRenderer();
	    if (renderer == null){
	        renderer = table.getTableHeader().getDefaultRenderer();
	    }
	    Component component = renderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, 0);
	    columnWidth = component.getPreferredSize().width;

	    // FIND THE MAXIMUM WIDTH PIECE OF DATA, WE'LL MAKE THAT OUR WIDTH
	    for (int i = 0; i < table.getRowCount(); i++){
	        renderer = table.getCellRenderer(i, columnIndex);
	        component = renderer.getTableCellRendererComponent(table, table.getValueAt(i, columnIndex), false, false, i, columnIndex);
	        columnWidth = Math.max(columnWidth, component.getPreferredSize().width);
	    }
	    // PADDING
	    columnWidth += 24;
	    // AND SET THE PARAMETERS FOR THIS COLUMN
	    column.setWidth(columnWidth);
	    column.setPreferredWidth(columnWidth);
	}
	
	public static void saveOutput() throws IOException{
		// TODO: Save all output to a text file
	}
	
}

class CBCSVFileFiler extends FileFilter{
	public boolean accept(File file){
		return file.getName().endsWith(".csv");
	}
	public String getDescription(){
		return ".csv";
	}
}