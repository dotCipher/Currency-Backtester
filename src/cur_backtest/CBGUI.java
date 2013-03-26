package cur_backtest;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;

import cur_backtest.events.*;

public class CBGUI {
	// HERE'S THE WINDOW ITSELF
	private static JFrame window;
	
	// MENU BAR
	private static JMenuBar menuBar;
	private static JMenu fileMenu;
	private static JMenuItem exitMenuItem;
	private static JMenuItem openMenuItem;
	
	// OPTION PANEL
	private static JPanel optPanel;
	private static TitledBorder optPanelTitle;
	private static JLabel optAlgLabel;
	private static JComboBox optAlgComboBox;
	private static JLabel optIntScLabel;
	private static JComboBox optIntScComboBox;
	private static JButton optCalcButton;
	
	// OUTPUT PANEL
	private static JPanel outPanel;
	private static TitledBorder outPanelTitle;
	// - Successes sub-panel
	private static JPanel outSucPanel;
	private static TitledBorder outSucPanelTitle;
	// - - Shorts
	private static JLabel outSucPanelShortNumLabel;
	private static JTextField outSucPanelShortNumField;
	private static JLabel outSucPanelShortPerLabel;
	private static JTextField outSucPanelShortPerField;
	// - - Longs
	private static JLabel outSucPanelLongNumLabel;
	private static JTextField outSucPanelLongNumField;
	private static JLabel outSucPanelLongPerLabel;
	private static JTextField outSucPanelLongPerField;
	// - - Total
	private static JLabel outSucPanelTotalNumLabel;
	private static JTextField outSucPanelTotalNumField;
	private static JLabel outSucPanelTotalPerLabel;
	private static JTextField outSucPanelTotalPerField;
	// - Failures sub-panel
	private static JPanel outFailPanel;
	private static TitledBorder outFailPanelTitle;
	// - - Shorts
	private static JLabel outFailPanelShortNumLabel;
	private static JTextField outFailPanelShortNumField;
	private static JLabel outFailPanelShortPerLabel;
	private static JTextField outFailPanelShortPerField;
	// - - Longs
	private static JLabel outFailPanelLongNumLabel;
	private static JTextField outFailPanelLongNumField;
	private static JLabel outFailPanelLongPerLabel;
	private static JTextField outFailPanelLongPerField;
	// - - Total
	private static JLabel outFailPanelTotalNumLabel;
	private static JTextField outFailPanelTotalNumField;
	private static JLabel outFailPanelTotalPerLabel;
	private static JTextField outFailPanelTotalPerField;
	
	// TABLE STATS PANEL
	private static JPanel tblStatPanel;
	private static TitledBorder tblStatPanelTitle;
	// - DATE RANGE
	private static JLabel dRangeLabel;
	private static JTextField dRangeField1;
	private static JTextField dRangeField2;
	// - TABLE LOWS AND HIGH
	private static JLabel tblHighLabel;
	private static JTextField tblHighField;
	private static JLabel tblLowLabel;
	private static JTextField tblLowField;
	
	// LOADED DATA TABLE
	private static JTable table;
	private static DefaultTableModel tableModel;
	
	public static JFrame 			getWindow()		{	return window;		}
	public static JTable			getTable()		{	return table;		}
	public static DefaultTableModel	getTableModel()	{	return tableModel;	}
	
	public static void setDRangeField1(String txt)	{ dRangeField1.setText(txt);	}
	public static void setDRangeField2(String txt)	{ dRangeField2.setText(txt);	}
	public static void setTblHighField(String txt)	{ tblHighField.setText(txt);	}
	public static void setTblLowField(String txt)	{ tblLowField.setText(txt);		}
	
	public static double getScale()	{
		return Double.valueOf(optIntScComboBox.getSelectedItem().toString()).doubleValue();
	}
	
	public static Object getAlgorithm(){
		return optAlgComboBox.getSelectedItem();
	}
	
	public static void initWindow(){
		window = new JFrame("Currency Backtester");
		
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// SET SIZE
		window.setSize(810, 615);
		window.setResizable(false);
	}
	
	private static void layoutMenuBar(){
		// NORTH MENU BAR
		menuBar = new JMenuBar();
		// FILE MENU
		fileMenu = new JMenu("File");
		// OPEN DATA FILE
		openMenuItem = new JMenuItem("Open");
		// EXIT PROGRAM MENU ITEM
		exitMenuItem = new JMenuItem("Exit");
		// ADD TO MENU
		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		// ADD TO MENU BAR
		menuBar.add(fileMenu);
		
		// ADD TO WINDOW
		window.setJMenuBar(menuBar);
	}
	
	private static void layoutOptionsPanel(){
		optPanel = new JPanel();
		optPanel.setLayout(new GridBagLayout());
		optPanelTitle = BorderFactory.createTitledBorder("Options");
		optPanel.setBorder(optPanelTitle);
		
		// ALGORITHMS
		String[] algs = {"Momentum"};
		optAlgLabel = new JLabel("Algorithm:");
		optAlgComboBox = new JComboBox(algs);
		
		// INTERVAL SCALES
		String[] intScls = {"1.0", "0.1", "0.01", "0.001"};
		optIntScLabel = new JLabel("Interval Scale:");
		optIntScComboBox = new JComboBox(intScls);
		optIntScComboBox.setSelectedIndex(2);
		
		optCalcButton = new JButton("CALCULATE");
		
		// ADD TO PANEL
		addJComponentToContainerUsingGBL(
				optAlgLabel, optPanel, 0, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				optAlgComboBox, optPanel, 1, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				optIntScLabel, optPanel, 0, 1, 1, 1);
		addJComponentToContainerUsingGBL(
				optIntScComboBox, optPanel, 1, 1, 1, 1);
		addJComponentToContainerUsingGBL(
				optCalcButton, optPanel, 0, 2, 2, 1);
		
		// ADD TO WINDOW
		addJComponentToContainerUsingGBL(
				optPanel, window, 0, 2, 1, 1);
	}
	
	private static void layoutOuputPanel(){
		// Panel separator
		JSeparator horizLine = new JSeparator(SwingConstants.HORIZONTAL);
		// OUTPUT PANEL
		outPanel = new JPanel();
		outPanel.setLayout(new GridBagLayout());
		outPanelTitle = BorderFactory.createTitledBorder("Output");
		outPanel.setBorder(outPanelTitle);
		// - Successes sub-panel
		outSucPanel = new JPanel();
		outSucPanel.setLayout(new GridBagLayout());
		outSucPanelTitle = BorderFactory.createTitledBorder("Successes");
		outSucPanel.setBorder(outSucPanelTitle);
		// ADD TO OUTPUT PANEL
		addJComponentToContainerUsingGBL(
				outSucPanel, outPanel, 0, 0, 1, 1);
		// - - Shorts
		outSucPanelShortNumLabel = new JLabel("# of Shorts:");
		outSucPanelShortNumField = new JTextField();
		outSucPanelShortNumField.setColumns(5);
		outSucPanelShortPerLabel = new JLabel("% of Shorts:");
		outSucPanelShortPerField = new JTextField();
		outSucPanelShortPerField.setColumns(5);
		// ADD TO SUCCESS SUB-PANEL
		addJComponentToContainerUsingGBL(
				outSucPanelShortNumLabel, outSucPanel, 0, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				outSucPanelShortNumField, outSucPanel, 1, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				outSucPanelShortPerLabel, outSucPanel, 2, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				outSucPanelShortPerField, outSucPanel, 3, 0, 1, 1);
		// - - Longs
		outSucPanelLongNumLabel = new JLabel("# of Longs:");
		outSucPanelLongNumField = new JTextField();
		outSucPanelLongNumField.setColumns(5);
		outSucPanelLongPerLabel = new JLabel("% of Longs:");
		outSucPanelLongPerField = new JTextField();
		outSucPanelLongPerField.setColumns(5);
		// ADD TO SUCCESS SUB-PANEL
		addJComponentToContainerUsingGBL(
				outSucPanelLongNumLabel, outSucPanel, 0, 1, 1, 1);
		addJComponentToContainerUsingGBL(
				outSucPanelLongNumField, outSucPanel, 1, 1, 1, 1);
		addJComponentToContainerUsingGBL(
				outSucPanelLongPerLabel, outSucPanel, 2, 1, 1, 1);
		addJComponentToContainerUsingGBL(
				outSucPanelLongPerField, outSucPanel, 3, 1, 1, 1);
		// - - Total
		outSucPanelTotalNumLabel = new JLabel("# Total:");
		outSucPanelTotalNumField = new JTextField();
		outSucPanelTotalNumField.setColumns(5);
		outSucPanelTotalPerLabel = new JLabel("% Total:");
		outSucPanelTotalPerField = new JTextField();
		outSucPanelTotalPerField.setColumns(5);
		// [ - - - - - - - SEPARATOR - - - - - - - ]
		addJComponentToContainerUsingGBL(
				horizLine, outSucPanel, 0, 2, 4, 1);
		// REST OF TOTAL
		addJComponentToContainerUsingGBL(
				outSucPanelTotalNumLabel, outSucPanel, 0, 3, 1, 1);
		addJComponentToContainerUsingGBL(
				outSucPanelTotalNumField, outSucPanel, 1, 3, 1, 1);
		addJComponentToContainerUsingGBL(
				outSucPanelTotalPerLabel, outSucPanel, 2, 3, 1, 1);
		addJComponentToContainerUsingGBL(
				outSucPanelTotalPerField, outSucPanel, 3, 3, 1, 1);
		// - Failures sub-panel
		outFailPanel = new JPanel();
		outFailPanel.setLayout(new GridBagLayout());
		outFailPanelTitle = BorderFactory.createTitledBorder("Failures");
		outFailPanel.setBorder(outFailPanelTitle);
		// ADD TO OUTPUT PANEL
		addJComponentToContainerUsingGBL(
				outFailPanel, outPanel, 0, 1, 1, 1);
		// - - Shorts
		outFailPanelShortNumLabel = new JLabel("# of Shorts:");
		outFailPanelShortNumField = new JTextField();
		outFailPanelShortNumField.setColumns(5);
		outFailPanelShortPerLabel = new JLabel("% of Shorts:");
		outFailPanelShortPerField = new JTextField();
		outFailPanelShortPerField.setColumns(5);
		// ADD TO FAIL SUB-PANEL
		addJComponentToContainerUsingGBL(
				outFailPanelShortNumLabel, outFailPanel, 0, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				outFailPanelShortNumField, outFailPanel, 1, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				outFailPanelShortPerLabel, outFailPanel, 2, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				outFailPanelShortPerField, outFailPanel, 3, 0, 1, 1);
		// - - Longs
		outFailPanelLongNumLabel = new JLabel("# of Longs:");
		outFailPanelLongNumField = new JTextField();
		outFailPanelLongNumField.setColumns(5);
		outFailPanelLongPerLabel = new JLabel("% of Longs:");
		outFailPanelLongPerField = new JTextField();
		outFailPanelLongPerField.setColumns(5);
		// ADD TO SUCCESS SUB-PANEL
		addJComponentToContainerUsingGBL(
				outFailPanelLongNumLabel, outFailPanel, 0, 1, 1, 1);
		addJComponentToContainerUsingGBL(
				outFailPanelLongNumField, outFailPanel, 1, 1, 1, 1);
		addJComponentToContainerUsingGBL(
				outFailPanelLongPerLabel, outFailPanel, 2, 1, 1, 1);
		addJComponentToContainerUsingGBL(
				outFailPanelLongPerField, outFailPanel, 3, 1, 1, 1);
		// - - Total
		outFailPanelTotalNumLabel = new JLabel("# Total:");
		outFailPanelTotalNumField = new JTextField();
		outFailPanelTotalNumField.setColumns(5);
		outFailPanelTotalPerLabel = new JLabel("% Total:");
		outFailPanelTotalPerField = new JTextField();
		outFailPanelTotalPerField.setColumns(5);
		// [ - - - - - - - SEPARATOR - - - - - - - ]
		addJComponentToContainerUsingGBL(
				horizLine, outFailPanel, 0, 2, 4, 1);
		// REST OF TOTAL
		addJComponentToContainerUsingGBL(
				outFailPanelTotalNumLabel, outFailPanel, 0, 3, 1, 1);
		addJComponentToContainerUsingGBL(
				outFailPanelTotalNumField, outFailPanel, 1, 3, 1, 1);
		addJComponentToContainerUsingGBL(
				outFailPanelTotalPerLabel, outFailPanel, 2, 3, 1, 1);
		addJComponentToContainerUsingGBL(
				outFailPanelTotalPerField, outFailPanel, 3, 3, 1, 1);
		
		// LAY OUT INSIDE WINDOW
		addJComponentToContainerUsingGBL(
				outPanel, window, 0, 0, 1, 2);
	}
	
	private static void layoutTable(){
		// THIS WILL RENDER OUR SPREADSHEET
		table = new JTable();
		Font tableFont = new Font("Arial", Font.PLAIN, 20);
		table.setFont(tableFont);
		table.setRowHeight(25);
		Font headerFont = new Font("Serif", Font.BOLD, 24);
		table.getTableHeader().setFont(headerFont);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		
		// THIS WILL HOLD THE DATA IN OUR SPREADSHEET
		tableModel = new DefaultTableModel();
		table.setModel(tableModel);
		
		// WE WANT THE TABLE TO SCROLL IF NECESSARY
		JScrollPane jsp = new JScrollPane(table);
		
		// LAY OUT INSIDE FRAME
		addJComponentToContainerUsingGBL(
				jsp, window, 1, 0, 1, 2);
	}
	
	public static void layoutDefaultTableColumns(){
		// TODO
		
	}
	
	private static void layoutTableStats(){
		// TABLE STATS PANEL
		tblStatPanel = new JPanel();
		tblStatPanel.setLayout(new GridBagLayout());
		tblStatPanelTitle = BorderFactory.createTitledBorder(
				"Table Statistics");
		tblStatPanel.setBorder(tblStatPanelTitle);
		// DATE RANGE
		dRangeLabel = new JLabel("Date Range:");
		dRangeField1 = new JTextField();
		dRangeField1.setColumns(10);
		dRangeField1.setEditable(false);
		// TO - FROM
		JLabel to = new JLabel("-");
		dRangeField2 = new JTextField();
		dRangeField2.setColumns(10);
		dRangeField2.setEditable(false);
		// PUT DATE IN PANEL
		addJComponentToContainerUsingGBL(
				dRangeLabel, tblStatPanel, 0, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				dRangeField1, tblStatPanel, 1, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				to, tblStatPanel, 2, 0, 1, 1);
		addJComponentToContainerUsingGBL(
				dRangeField2, tblStatPanel, 3, 0, 1, 1);
		// TABLE HIGHS AND LOWS
		tblHighLabel = new JLabel("Table High:");
		tblHighField = new JTextField(10);
		tblHighField.setColumns(10);
		tblHighField.setEditable(false);
		tblLowLabel = new JLabel("Table Low:");
		tblLowField = new JTextField(10);
		tblLowField.setColumns(10);
		tblLowField.setEditable(false);
		// PUT IN PANEL
		addJComponentToContainerUsingGBL(
				tblHighLabel, tblStatPanel, 0, 1, 1, 1);
		addJComponentToContainerUsingGBL(
				tblHighField, tblStatPanel, 1, 1, 1, 1);
		addJComponentToContainerUsingGBL(
				tblLowLabel, tblStatPanel, 0, 2, 1, 1);
		addJComponentToContainerUsingGBL(
				tblLowField, tblStatPanel, 1, 2, 1, 1);
		// LAY OUT INSIDE FRAME
		addJComponentToContainerUsingGBL(
				tblStatPanel, window, 1, 2, 1, 1);
	}
	
	public static void layoutGUI(){
		window.setLayout(new GridBagLayout());
		layoutMenuBar();
		layoutOptionsPanel();
		layoutOuputPanel();
		layoutTable();
		layoutDefaultTableColumns();
		layoutTableStats();
	}
	
	public static void initHandlers(){
		CBWindowHandler cbWindowHandler = new CBWindowHandler();
		window.addWindowListener(cbWindowHandler);
		CBExitHandler cbExitHandler = new CBExitHandler();
		exitMenuItem.addActionListener(cbExitHandler);
		CBOpenHandler cbOpenHandler = new CBOpenHandler();
		openMenuItem.addActionListener(cbOpenHandler);
		CBCalculateHandler cbCalculateHandler = new CBCalculateHandler();
		optCalcButton.addActionListener(cbCalculateHandler);
	}
	
	public static void addJComponentToContainerUsingGBL(
			JComponent jc, Container c, int x, int y, int w, int h){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5,5,5,5);
		c.add(jc, gbc);
	}
	
	public static void centerWindow(JFrame window){
	    // Get the size of the screen
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    
	    // Determine the new location of the window
	    int w = window.getSize().width;
	    int h = window.getSize().height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    
	    // Move the window
	    window.setLocation(x, y);
	}
	
	public static void main(String[] args){
		initWindow();
		layoutGUI();
		initHandlers();
		window.setVisible(true);
		
		// CENTER WINDOW
		centerWindow(window);
	}
	
}
