import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JDialog;


public class MyNewJFrame extends JDialog implements ActionListener, KeyListener
{
    public final static int MODIFY_MODE = 0;
    public final static int ADD_MODE = 1;
    public final static int DELETE_MODE = 2;

    int mode;
    protected JLabel row, first, last, cuny, gpa, venus;
    protected JTextField rowT, firstT, lastT, cunyT, gpaT, venusT;
    
    protected JButton btnOperation, btnCancel;
    protected JTable myReferencedTable;

    public MyNewJFrame(JFrame jfrm, JTable newTable) {
        myReferencedTable = newTable;
        setSize(300, 270);
        setLocationRelativeTo(jfrm);
        setLayout(new FlowLayout());
        //Rows
        row = new JLabel("         Row ID:");
        rowT = new JTextField(12);
        add(row);
        add(rowT);
        //First
        first = new JLabel("   First Name:");
        firstT = new JTextField(12);
        firstT.addKeyListener(this);
        add(first);
        add(firstT);
        //Last
        last = new JLabel("   Last Name:");
        lastT = new JTextField(12);
        lastT.addKeyListener(this);
        add(last);
        add(lastT);
        //CUNY
        cuny = new JLabel("      CUNY ID:");
        cunyT = new JTextField(12);
        cunyT.addKeyListener(this);
        add(cuny);
        add(cunyT);
        //GPA
        gpa = new JLabel("             GPA:");
        gpaT = new JTextField(12);
        gpaT.addKeyListener(this);
        add(gpa);
        add(gpaT);
        //Venus Login
        venus = new JLabel("Venus Login:");
        venusT = new JTextField(12);
        add(venus);
        add(venusT);

        (this.btnOperation = new JButton()).setActionCommand("Operation");
        this.btnOperation.addActionListener(this);
        (this.btnCancel = new JButton("Cancel")).addActionListener(this);

        this.add(btnOperation);
        this.add(btnCancel);
        this.setModal(true);
        this.getRootPane().setDefaultButton(btnOperation);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand;
        switch (actionCommand = e.getActionCommand()) {
            case "Operation": {
                if (Confirm()) {
                    processMode();
                    break;
                }
                setVisible(false);
                break;
            }
            case "Cancel": {
                setVisible(false);
                break;
            }
            default:
                break;
        }
    }
    
    
    public void Mode(int newMode) {
        mode = newMode;
        if (mode == MyNewJFrame.ADD_MODE) {
            setTitle("Create Student Record");
            btnOperation.setText("Add New Student");
        }
        else if (mode == MyNewJFrame.MODIFY_MODE) {
            setTitle("Modify Student Record");
            btnOperation.setText("Update Student");
            fillInField();
        }
        else if (mode == MyNewJFrame.DELETE_MODE) {
            setTitle("Delete A Student Record");
            btnOperation.setText("Delete Student");
            fillInField();
        }
        if(mode == ADD_MODE){
        	row.setVisible(false);
        	rowT.setVisible(false);
        }


    }
    
    private boolean Confirm() {
        boolean goOrNo = false;
        String confirm = null;
        String title = null;
        if (this.mode == MyNewJFrame.ADD_MODE) {
            confirm = "Are you sure you want to add this student?";
            title = "Add Confirmation";
        }
        else if (this.mode == MyNewJFrame.MODIFY_MODE) {
            confirm = "Are you sure you want to modify this student?";
            title = "Modify Confirmation";
        }
        else if (this.mode == MyNewJFrame.DELETE_MODE) {
            confirm = "Are you sure you want to delete this student?";
            title = "Delete Confirmation";
        }
        int result = JOptionPane.showConfirmDialog(this, confirm, title, 2, 3);
        if (result == 0) {
             goOrNo = true;
        }
        return goOrNo;
    }
    
    private void processMode() {
        if (checkData()) {
            DefaultTableModel tblModel = (DefaultTableModel)this.myReferencedTable.getModel();
            int newRow = 1;
            final Object[] data = this.rowData();
            if (this.mode == MyNewJFrame.ADD_MODE) {
                data[0] = new Integer(newRow);
                if (tblModel.getRowCount() > 0) {
                    newRow = (int)tblModel.getValueAt(tblModel.getRowCount() - 1, 0) + 1;
                }
                tblModel.addRow(data);
            }
            else if (mode == MyNewJFrame.MODIFY_MODE) {
                int rowToModify = this.myReferencedTable.convertRowIndexToModel(this.myReferencedTable.getSelectedRow());
                for (int i = 0; i < tblModel.getColumnCount(); i++) {
                    tblModel.setValueAt(data[i], rowToModify, i);
                }
            }
            else if (mode == MyNewJFrame.DELETE_MODE) {
                tblModel.removeRow(this.myReferencedTable.convertRowIndexToModel(this.myReferencedTable.getSelectedRow()));
            }
            setVisible(false);
        }
    }
    
    private boolean checkData() {
        boolean correct = true;
        if (mode == MyNewJFrame.MODIFY_MODE && rowT.getText().length() <= 0) {
            correct = false;
            JOptionPane.showMessageDialog(this, "Invalid Row ID");
        }
        else if (firstT.getText().length() < 2) {
            correct = false;
            JOptionPane.showMessageDialog(this, "First name must be 2 characters or more");
        }
        else if (lastT.getText().length() < 2) {
            correct = false;
            JOptionPane.showMessageDialog(this, "Last name must be 2 characters or more");
        }
        else if (cunyT.getText().length() <= 0 || !CheckInteger(this.cunyT.getText()) || Integer.parseInt(this.cunyT.getText()) < 10000000 || Integer.parseInt(this.cunyT.getText()) > 99999999) {
            correct = false;
            JOptionPane.showMessageDialog(this, "CUNY ID must be 8 digits");
        }
        else if (gpaT.getText().length() <= 0 || !CheckDouble(this.gpaT.getText()) || Double.parseDouble(this.gpaT.getText()) < 0.0 || Double.parseDouble(this.gpaT.getText()) > 4.0) {
            correct = false;
            JOptionPane.showMessageDialog(this, "GPA must be between 0.0 - 4.0");
        }
        return correct;
    }
    
    //ONLY FOR MODIFY AND DELETE.. NOT ADD
    private void fillInField() { 
        int selectedRow = this.myReferencedTable.getSelectedRow();
        for (int i = 0; i < this.myReferencedTable.getColumnCount(); ++i) {
            String value = this.myReferencedTable.getValueAt(selectedRow, i).toString().trim();
            String columnName;
            switch (columnName = this.myReferencedTable.getColumnName(i)) {
                case "Row ID": 
                    this.rowT.setText(value);
                    break;
                case "Venus Login": 
                    this.venusT.setText(value);
                    break;
                case "Last Name": 
                    this.lastT.setText(value);
                    break;
                case "GPA": 
                    this.gpaT.setText(value);
                    break;
                case "First Name": 
                    this.firstT.setText(value);
                    break;
                case "CUNY ID": 
                    this.cunyT.setText(value);
                    break;
            }
        }
    }
    
    private Object[] rowData() {
        Object[] rowData = new Object[6];
        if(this.rowT.getText().length() > 0){
        	rowData[0] = new Integer(this.rowT.getText());
        }
        rowData[3] = new Integer(cunyT.getText());
        rowData[1] = firstT.getText();
        rowData[2] = lastT.getText();
        rowData[5] = venusT.getText();
        rowData[4] = new Double(gpaT.getText());
        rowData[5] = venusT.getText();
        return rowData;
    }

    
    //CREATE THE VENUS LOGIN
    private void venusCreator() {
        StringBuilder sb = new StringBuilder();
        if (this.lastT.getText().length() >= 2) {
            sb.append(this.lastT.getText().substring(0, 2));
        }
        if (this.firstT.getText().length() >= 2) {
            sb.append(this.firstT.getText().substring(0, 2));
        }
        if (this.cunyT.getText().length() >= 4) {
            sb.append(this.cunyT.getText().substring(this.cunyT.getText().length() - 4));
        }
        this.venusT.setText(sb.toString());
    }
   
    //To remove the textfields
    public void makeBlank() {
    	String blank = "";
        rowT.setText(blank);
        firstT.setText(blank);
        lastT.setText(blank);
        cunyT.setText(blank);
        gpaT.setText(blank);
        venusT.setText(blank);
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
    
    
    @Override
    public void keyReleased(KeyEvent e) {
        this.venusCreator();
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    
    
    
    
    
    
    
    
    
    
  
}
