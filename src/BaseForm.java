import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class BaseForm implements ActionListener, KeyListener
{
    String[] columnNames;
    protected JFrame jfrm;
    protected JFileChooser jfc;
    protected JLabel lblOutput;
    protected JTable tblData;
    protected JTextField search;
    protected DefaultTableModel tblModel;
    protected JButton addBtn, deleteBtn, exportBtn;
    FileDialog fileD;
    JComboBox<String> combo;
    MyNewJFrame operation;
    
	public BaseForm(){
		
		//Basic setup
		jfrm = new JFrame();
		jfrm.setSize(600,600);
		jfrm.setResizable(false);
		jfrm.setLocationRelativeTo(null);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setLayout(new FlowLayout());
		jfrm.setVisible(true);
		jfrm.setTitle("Student Management System");
		
		
		this.jfrm.setSize(550,600);
		lblOutput = new JLabel("Search by: ");
		this.jfrm.add(lblOutput);
		combo = new JComboBox<String>();
		combo.addItem("Row ID");
		combo.addItem("First Name");
		combo.addItem("Last Name");
		combo.addItem("CUNY ID");
		combo.addItem("GPA");
		combo.addItem("Venus Login");
		jfrm.add(combo);
		(search = new JTextField(10)).setActionCommand("Search Text");
		search.addActionListener(this);
		search.addKeyListener(this);
		jfrm.add(search);
		addBtn = new JButton("Add");
		addBtn.addActionListener(this);
		deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(this);
		jfrm.add(addBtn);
		jfrm.add(deleteBtn);
		
		JMenuBar jMenu = new JMenuBar();
		
		//Create the Menu for the File tab including all its Tabs
		JMenu jFile = new JMenu("File");
		jFile.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem jOpen = new JMenuItem("Open");
		jOpen.setMnemonic(KeyEvent.VK_O);
		jOpen.setAccelerator(
				KeyStroke.getKeyStroke(
						KeyEvent.VK_O,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()
						)
		);
		jfc = new JFileChooser();
		fileD = new FileDialog(jfrm);
		
		
		
		JMenuItem jExport = new JMenuItem("Export");
		jExport.setMnemonic(KeyEvent.VK_E);
		jExport.setAccelerator(
				KeyStroke.getKeyStroke(
						KeyEvent.VK_E,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()
						)
		);
		
		JMenuItem jQuit = new JMenuItem("Quit");
		jQuit.setMnemonic(KeyEvent.VK_Q);
		jQuit.setAccelerator(
				KeyStroke.getKeyStroke(
						KeyEvent.VK_Q,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()
						)
		);
		
		jOpen.addActionListener(this);
		jExport.addActionListener(this);
		jQuit.addActionListener(this);
		jFile.add(jOpen);
		jFile.add(jExport);
		jFile.addSeparator();
		jFile.add(jQuit);
		jMenu.add(jFile);
		
		JMenu jHelp = new JMenu("Help");
		jHelp.setMnemonic(KeyEvent.VK_H);
		JMenuItem jAbout = new JMenuItem("About");
		
		jAbout.setMnemonic(KeyEvent.VK_A);
		jAbout.setMnemonic(KeyEvent.VK_A);
		jAbout.setAccelerator(
				KeyStroke.getKeyStroke(
						KeyEvent.VK_A,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()
						)
		);
		
		jAbout.addActionListener(this);
		jHelp.add(jAbout);
		jMenu.add(jHelp);
		
		jfrm.setJMenuBar(jMenu);
		
		tblData = new JTable();
		
        this.tblData.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                    	return Integer.class;
                    case 1:
                    	return String.class;
                    case 2:
                    	return String.class;
                    case 3: {
                        return Integer.class;
                    }
                    case 4: {
                        return Double.class;
                    }
                    case 5: {
                        return String.class;
                    }

                }
				return null;
            }
        });
		
        
		//This is where you can manipulate data
		
		tblModel = (DefaultTableModel)tblData.getModel();
		tblModel.addColumn("Row ID");
		tblModel.addColumn("First Name");
		tblModel.addColumn("Last Name");
		tblModel.addColumn("CUNY ID");
		tblModel.addColumn("GPA");
		tblModel.addColumn("Venus Login");
				
		
		
		JScrollPane jspData = new JScrollPane(tblData);
		jfrm.add(jspData);	
		
		jfrm.setVisible(true);
			
		
        tblData.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (BaseForm.this.operation == null) {
                        BaseForm.this.operation = new MyNewJFrame(BaseForm.this.jfrm, BaseForm.this.tblData);
                    }
             
                    BaseForm.this.operation.makeBlank();
                    operation.Mode(MyNewJFrame.MODIFY_MODE);
                    BaseForm.this.operation.setVisible(true);
                }
            }
        });
        tblData.setSelectionMode(0);
        tblData.setAutoCreateRowSorter(true);
        jfrm.add(jspData);
        JButton btnExport = new JButton("Export Data");
        btnExport.addActionListener(this);
        jfrm.add(btnExport);
        jfrm.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand;
        switch (actionCommand = e.getActionCommand()) {
            case "Export Data": 
            	ExportData();
                break;
            
            case "Add": 
                this.HandleAddEvent(e);
                break;
            
            case "Open": 
                this.HandleOpenEvent();
                break;
            
            case "Quit": 
                System.exit(0);
                break;
            
            case "About": 
                this.ShowAboutDialog();
                break;
            
            case "Search Text": 
                this.comboSearch();
                break;
            
            case "Delete": 
                this.HandleDeleteEvent();
                break;
            
            case "Export": 
            	ExportData();
                break;
            
        }
        
    }
    
    public void comboSearch() {
        RowFilter<Object, Object> rowFilter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                boolean shouldInclude = false;
                switch (GetCurrentComboSearchIndex()) {
                    case 0:
                        if (Integer.parseInt(entry.getStringValue(GetCurrentComboSearchIndex()).trim())
                                == Integer.parseInt(GetCurrentTextSearch())) {
                            shouldInclude = true;
                        }
                        break;
                    case 1:
                        if (entry.getStringValue(GetCurrentComboSearchIndex()).toLowerCase().contains(GetCurrentTextSearch().toLowerCase())) {
                            shouldInclude = true;
                        }
                        break;
                    case 2:
                        if (entry.getStringValue(GetCurrentComboSearchIndex()).toLowerCase().contains(GetCurrentTextSearch().toLowerCase())
                                ) {
                            shouldInclude = true;
                        }
                        break;
                    case 3:
                        if (Integer.parseInt(entry.getStringValue(GetCurrentComboSearchIndex()).trim())
                                == Integer.parseInt(GetCurrentTextSearch())) {
                            shouldInclude = true;
                        }
                        break;
                    case 4:
                        if (entry.getStringValue(GetCurrentComboSearchIndex()).toLowerCase().contains(GetCurrentTextSearch().toLowerCase())) {
                            shouldInclude = true;
                        }
                        break;
                    case 5:
                        if (entry.getStringValue(GetCurrentComboSearchIndex()).toLowerCase().contains(GetCurrentTextSearch().toLowerCase())) {
                            shouldInclude = true;
                        }
                        break;
                }
                return shouldInclude;
            }
        };
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) tblData.getRowSorter();
        sorter.setRowFilter(rowFilter);
    }
    
    private String GetCurrentTextSearch() {
        return this.search.getText();
    }
    
    private int GetCurrentComboSearchIndex() {
        return this.combo.getSelectedIndex();
    }
    
    private void ExportData() {
        if (this.tblData.getRowCount() > 0) {
            this.fileD.setMode(1);
            
            this.fileD.setVisible(true);
            if (this.fileD.getFile() != null) {
                try {
                    BufferedWriter fw = new BufferedWriter(new FileWriter(String.valueOf(this.fileD.getDirectory()) + this.fileD.getFile()));
                    for (int i = 0; i < this.tblData.getRowCount(); ++i) {
                        for (int j = 1; j < this.tblData.getColumnCount(); ++j) {
                            fw.write(String.format("%s", this.tblData.getValueAt(i, j)));
                            if (j != this.tblData.getColumnCount() - 1) {
                                fw.write(",");
                            }
                        }
                        fw.write("\r\n");
                        fw.flush();
                    }
                }
                catch (IOException e) {
                    JOptionPane.showMessageDialog(this.jfrm,"Error during Export");
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(this.jfrm, "File has been exported to " + this.fileD.getDirectory() + this.fileD.getFile());
            
            }
        }
    }
    
    private void HandleOpenEvent() {
        this.fileD.setMode(0);
        this.fileD.setVisible(true);
        final String fileName = this.fileD.getFile();
        if (fileName != null) {
            this.transferData(String.valueOf(this.fileD.getDirectory()) + this.fileD.getFile());
        }
    }
    
    private void HandleAddEvent(final ActionEvent e) {
        if (this.operation == null) {
            this.operation = new MyNewJFrame(this.jfrm, this.tblData);
        }
        this.operation.Mode(MyNewJFrame.ADD_MODE);
        this.operation.makeBlank();
        this.operation.setVisible(true);
    }
    
    private void HandleDeleteEvent() {
        if (this.tblData.getSelectedRow() >= 0) {
            if (this.operation == null) {
                this.operation = new MyNewJFrame(this.jfrm, this.tblData);
            }
            this.operation.makeBlank();
            this.operation.Mode(MyNewJFrame.DELETE_MODE);
            this.operation.setVisible(true);
        }
        else {
            JOptionPane.showMessageDialog(this.jfrm, "No Row Selected.", "ERROR", 2);
        }
    }
    
    
    
    
    public boolean CheckInteger(String value) {
        boolean integer = false;
        try {
            Integer.parseInt(value);
            integer = true;
        }
        catch(NumberFormatException e){
        	integer = false;
        }
		return integer;
    }
    
    
    
    
    public boolean CheckDouble(String value) {
        boolean doub = false;
        try {
            Double.parseDouble(value);
            doub = true;
        }
        catch(NumberFormatException e){
        	doub = false;
        }
		return doub;
    }
    
    
    private void transferData(String fileName) {
        try {
            Scanner read = new Scanner(new File(fileName));
            int counter = 1;
            while (read.hasNextLine()) {
                final String[] rowData = read.nextLine().split(",");
                if (rowData.length == 5) {
                    final Object[] newData = new Object[6];
                    for (int i = 0; i < 6; ++i) {
                        switch (i) {
                            case 0: {
                                newData[i] = new Integer(counter);
                                break;
                            }
                            case 1:
                            case 2:
                            case 3:
                            case 5: {
                                newData[i] = new String(rowData[i - 1]);
                                break;
                            }
                            case 4: {
                                newData[i] = new Double(rowData[i - 1]);
                                break;
                            }
                        }
                    }
                    this.tblModel.addRow(newData);
                }
                ++counter;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void ShowAboutDialog() {
    
    	JOptionPane.showMessageDialog(null, "This is a project created by Andrew Kwak implementing GUI");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void keyTyped(final KeyEvent e) {
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
    }
}
