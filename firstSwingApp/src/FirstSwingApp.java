/**
 * Created by Dungeoun on 10/21/16.
 *
 * The Following Program can perform following function:
 *
 * 1. Add a new Record.
 * 2. Delete a record by Entering Friend Number in Text Box.
 * 3. Delete a record by Selecting it from the table.
 * 4. Allows us to view the current state of table.
 * 5. User can see the Phone Number of a friend by entering it's Friend Number.
 * 6. User can see the Physical Address of a friend by entering it's Friend Number.
 *
 *
 */
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.Objects;
import java.util.Vector;


public class FirstSwingApp extends JFrame{

    static Connection conn = null;

    String  friendNo;

    Object urObjctInCell;

    JButton jbtAdd;
    JButton jbtView;
    JButton jbtDel;
    JButton jbtDel1;
    JButton jbtPhone;
    JButton jbtAdr;
    JButton jbtUpd;

    JLabel lblName;
    JTextField nameText;
    JLabel lblNo;
    JTextField noText;

    JLabel lblBdate;
    JTextField bdateText;
    JLabel lblSex;
    JTextField sexText;

    JLabel lblState;
    JTextField stateText;
    JLabel lblCity;
    JTextField cityText;

    JLabel lblPhone;
    JTextField phoneText;
    JLabel lblExt;
    JTextField extText;

    JLabel lblAdd1;
    JTextField add1Text;
    JLabel lblAdd2;
    JTextField add2Text;




    JTable table;


    JButton panelBtn;

    DefaultTableModel model;

    Vector columnNames = new Vector();
    Vector data = new Vector();


    public FirstSwingApp(){

        super();

        //Add button

        jbtAdd = new JButton("Add");
        jbtAdd.addActionListener(new BtnAdd());

        //Delete using Friend Number Button

        jbtDel = new JButton("Delete using Friend Number");
        jbtDel.addActionListener(new BtnDelete());

        //Delete using Row Click Selection button

        jbtDel1 = new JButton("Delete Selected Row");
        jbtDel1.addActionListener(new BtnDelete1(this));

        //View Table button

        jbtView = new JButton("View Table");
        jbtView.addActionListener(new BtnView());

        //View Phone number

        jbtPhone = new JButton("View Phone No");
        jbtPhone.addActionListener(new BtnPhone());

        //view address

        jbtAdr = new JButton("View Address");
        jbtAdr.addActionListener(new BtnAdr());

        //update button

        jbtUpd = new JButton("Update Contact");
        jbtUpd.addActionListener(new BtnUpd());


        // UI Design and Layouts



        lblName = new JLabel("Friend Name");
        nameText = new JTextField(20);

        lblNo = new JLabel("Friend Number");
        noText = new JTextField(4);

        lblBdate = new JLabel("Birth date");
        bdateText = new JTextField(10);

        lblSex = new JLabel("Sex");
        sexText = new JTextField(1);

        lblState = new JLabel("State");
        stateText = new JTextField(20);

        lblCity = new JLabel("City");
        cityText = new JTextField(20);

        lblExt = new JLabel("Ext");
        extText = new JTextField(3);

        lblPhone = new JLabel("Phone No.");
        phoneText = new JTextField(10);

        lblAdd1 = new JLabel("Address Line 1");
        add1Text = new JTextField(10);

        lblAdd2 = new JLabel("Address Line 2");
        add2Text = new JTextField(10);

        panelBtn = new JButton("pbutton");

        model = new DefaultTableModel();

        urObjctInCell = new Object();

        table = new JTable(model);





        friendNo = urObjctInCell.toString();

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel1.setSize(300,300);
        panel1.add(lblName);
        panel1.add(nameText);
        panel1.add(lblNo);
        panel1.add(noText);


        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel2.setSize(300,300);
        panel2.add(lblBdate);
        panel2.add(bdateText);
        panel2.add(lblSex);
        panel2.add(sexText);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel3.setSize(300,300);
        panel3.add(lblState);
        panel3.add(stateText);
        panel3.add(lblCity);
        panel3.add(cityText);


        //panel4: panel for Action buttons

        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel4.setSize(300,300);
        panel4.add(jbtAdd);
        panel4.add(jbtDel);
        panel4.add(jbtUpd);
        panel4.add(jbtDel1);

        //Phone Number Panel

        JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel6.setSize(300,300);
        panel6.add(lblExt);
        panel6.add(extText);
        panel6.add(lblPhone);
        panel6.add(phoneText);

        //Physical address panel

        JPanel panel7 = new JPanel();
        panel7.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel7.setSize(300,300);
        panel7.add(lblAdd1);
        panel7.add(add1Text);
        panel7.add(lblAdd2);
        panel7.add(add2Text);


        //panel5: panel for view buttons

        JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel5.setSize(300,300);

        panel5.add(jbtView);
        panel5.add(jbtPhone);
        panel5.add(jbtAdr);
        panel5.add(new JScrollPane(table));



        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel6);
        this.add(panel7);
        this.add(panel4);
        this.add(panel5);




        this.setLayout(new FlowLayout(FlowLayout.LEADING));

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setBounds(100,100,400,250);

        this.setTitle("gotta get that swing");

        this.setVisible(true);

        this.setSize(700,400);





    }




    public static void main(String[] args){

        FirstSwingApp app = new FirstSwingApp();



    }

    //getSelectedCell() is used to get the values contained in row selected by click

    public Object getSelectedCell() {
        int col = 1;
        int row = table.getSelectedRow();

        if (col < 0 || row < 0) {
            return null; // no selection made, return null
        } else {
            return table.getValueAt(row, col);
        }
    }

    //BtnView class implements the View Table action of the button

    private class BtnView implements ActionListener{

        public void actionPerformed(ActionEvent e) {



            CallableStatement dstmt = null;
            ResultSet rs;


            try {

                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contact_Manager?user=root");
                String disQuery = "select * from FRIEND";

                dstmt = conn.prepareCall(disQuery);

                rs = dstmt.executeQuery();

                ResultSetMetaData metaData = rs.getMetaData();

                // names of columns
                Vector<String> columnNames = new Vector<String>();
                int columnCount = metaData.getColumnCount();
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }

                // data of the table
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                while (rs.next()) {
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(rs.getObject(columnIndex));
                    }
                    data.add(vector);
                }

                // It updates and displays the table


                model.setDataVector(data, columnNames);



            } catch (SQLException ex) {

                System.out.println("Error in connection: " + ex.getMessage());
            }

        }
    }

    //BtnPhone shows the phone numbers of a friend

    private class BtnPhone implements ActionListener{

        public void actionPerformed(ActionEvent e) {



            CallableStatement dstmt = null;
            ResultSet rs;


            try {

                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contact_Manager?user=root");
                String disQuery = "{call show_phone (?)} ";

                String fnumber = noText.getText();

                dstmt = conn.prepareCall(disQuery);

                dstmt.setString(1, fnumber);

                rs = dstmt.executeQuery();

                ResultSetMetaData metaData = rs.getMetaData();

                // names of columns
                Vector<String> columnNames = new Vector<String>();
                int columnCount = metaData.getColumnCount();
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }

                // data of the table
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                while (rs.next()) {
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(rs.getObject(columnIndex));
                    }
                    data.add(vector);
                }

                // It creates and displays the table

                model.setDataVector(data, columnNames);



            } catch (SQLException ex) {

                System.out.println("Error in connection: " + ex.getMessage());
            }

        }
    }

    // BtnAdr class for viewing the addresses

    private class BtnAdr implements ActionListener{

        public void actionPerformed(ActionEvent e) {



            CallableStatement dstmt = null;
            ResultSet rs;


            try {

                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contact_Manager?user=root");
                String disQuery = "{call show_address (?)} ";

                String fnumber = noText.getText();

                dstmt = conn.prepareCall(disQuery);

                dstmt.setString(1, fnumber);

                rs = dstmt.executeQuery();

                ResultSetMetaData metaData = rs.getMetaData();

                // names of columns
                Vector<String> columnNames = new Vector<String>();
                int columnCount = metaData.getColumnCount();
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }

                // data of the table
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                while (rs.next()) {
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(rs.getObject(columnIndex));
                    }
                    data.add(vector);
                }

                // It creates and displays the table

                model.setDataVector(data, columnNames);



            } catch (SQLException ex) {

                System.out.println("Error in connection: " + ex.getMessage());
            }

        }
    }


    //Button action listener for adding new friend

    public class BtnAdd implements ActionListener{

        public void actionPerformed(ActionEvent e){

            String name = nameText.getText();
            String fnumber = noText.getText();
            String bdate = bdateText.getText();
            String sex = sexText.getText();
            String state = stateText.getText();
            String city = cityText.getText();
            String ext = extText.getText();
            String phone = phoneText.getText();
            String add1 = add1Text.getText();
            String add2 = add2Text.getText();






            CallableStatement cstmt = null;
            CallableStatement dstmt = null;

            ResultSet rs;


            try{

                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contact_Manager?user=root");


                //calling stored procedure add_contact()

                String SQL = "{call add_contact (?,?,?,?,?,?,?,?,?,?)}";

                String disQuery = "select * from FRIEND";

                cstmt = conn.prepareCall(SQL);

                dstmt = conn.prepareCall(disQuery);

                cstmt.setString(1, name);
                cstmt.setString(2, fnumber);
                cstmt.setString(3, bdate);
                cstmt.setString(4, sex);
                cstmt.setString(5, city);
                cstmt.setString(6, state);
                cstmt.setString(7, ext);
                cstmt.setString(8, phone);
                cstmt.setString(9, add1);
                cstmt.setString(10, add2);


                //executing query ie. the stored procedure

                cstmt.executeQuery();

                rs = dstmt.executeQuery();

                //reading the output tables generating in mySQL to display in Java App

                ResultSetMetaData metaData = rs.getMetaData();

                // names of columns
                Vector<String> columnNames = new Vector<String>();
                int columnCount = metaData.getColumnCount();
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }

                // data of the table
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                while (rs.next()) {
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(rs.getObject(columnIndex));
                    }
                    data.add(vector);
                }

                // It creates and displays the table



                model.setDataVector(data, columnNames);

                // Closes the Connection




                cstmt.close();
                dstmt.close();
                System.out.println("Success!!");
            }
            catch(SQLException ex) {

                System.out.println("Error in connection: " + ex.getMessage());
            }





        }
    }

    public class BtnDelete implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            String name = nameText.getText();
            String fnumber = noText.getText();
            String bdate = bdateText.getText();
            String sex = sexText.getText();
            String state = stateText.getText();
            String city = cityText.getText();


            CallableStatement cstmt = null;
            CallableStatement dstmt = null;

            ResultSet rs;


            try {

                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contact_Manager?user=root");


                String SQL = "{call delete_contact (?)}";

                String disQuery = "select * from FRIEND";

                cstmt = conn.prepareCall(SQL);

                dstmt = conn.prepareCall(disQuery);


                cstmt.setString(1, fnumber);

                cstmt.executeQuery();

                rs = dstmt.executeQuery();

                ResultSetMetaData metaData = rs.getMetaData();

                // names of columns
                Vector<String> columnNames = new Vector<String>();
                int columnCount = metaData.getColumnCount();
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }

                // data of the table
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                while (rs.next()) {
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(rs.getObject(columnIndex));
                    }
                    data.add(vector);
                }

                // It creates and displays the table


                model.setDataVector(data, columnNames);

                // Closes the Connection


                cstmt.close();
                dstmt.close();
                System.out.println("Success!!");
            } catch (SQLException ex) {

                System.out.println("Error in connection: " + ex.getMessage());
            }


        }

    }


    //Delete button action listener for deleting using select from table

    public class BtnDelete1 extends AbstractAction {


        private FirstSwingApp mainGui;

        public BtnDelete1(FirstSwingApp mainGui) {
            super("Press Me");
            putValue(MNEMONIC_KEY, KeyEvent.VK_P);
            this.mainGui = mainGui;
        }

        public void actionPerformed(ActionEvent e) {

            Object cell = mainGui.getSelectedCell();

            String fnumber = cell.toString();

            CallableStatement dstmt = null;
            CallableStatement cstmt = null;

            ResultSet rs;


            try {

                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contact_Manager?user=root");


                String SQL = "{call delete_contact (?)}";

                String disQuery = "select * from FRIEND";

                dstmt = conn.prepareCall(disQuery);
                cstmt = conn.prepareCall(SQL);

                cstmt.setString(1, fnumber);

                cstmt.executeQuery();

                rs = dstmt.executeQuery();

                ResultSetMetaData metaData = rs.getMetaData();

                // names of columns
                Vector<String> columnNames = new Vector<String>();
                int columnCount = metaData.getColumnCount();
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }

                // data of the table
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                while (rs.next()) {
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(rs.getObject(columnIndex));
                    }
                    data.add(vector);
                }

                // It creates and displays the table


                model.setDataVector(data, columnNames);

                // Closes the Connection

                dstmt.close();
                System.out.println("Success!!");
            } catch (SQLException ex) {

                System.out.println("Error in connection: " + ex.getMessage());
            }


        }

    }

    //Update Records

    public class BtnUpd implements ActionListener{

        public void actionPerformed(ActionEvent e){

            String name = nameText.getText();
            String fnumber = noText.getText();
            String bdate = bdateText.getText();
            String sex = sexText.getText();
            String state = stateText.getText();
            String city = cityText.getText();
            String ext = extText.getText();
            String phone = phoneText.getText();
            String add1 = add1Text.getText();
            String add2 = add2Text.getText();




            CallableStatement cstmt = null;
            CallableStatement dstmt = null;

            ResultSet rs;


            try{

                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contact_Manager?user=root");


                String SQL = "{call update_contact (?,?,?,?,?,?,?,?,?,?)}";

                String disQuery = "select * from FRIEND";

                cstmt = conn.prepareCall(SQL);

                dstmt = conn.prepareCall(disQuery);

                cstmt.setString(1, name);
                cstmt.setString(2, fnumber);
                cstmt.setString(3, bdate);
                cstmt.setString(4, sex);
                cstmt.setString(5, city);
                cstmt.setString(6, state);
                cstmt.setString(7, ext);
                cstmt.setString(8, phone);
                cstmt.setString(9, add1);
                cstmt.setString(10, add2);



                cstmt.executeQuery();

                rs = dstmt.executeQuery();

                ResultSetMetaData metaData = rs.getMetaData();

                // names of columns
                Vector<String> columnNames = new Vector<String>();
                int columnCount = metaData.getColumnCount();
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }

                // data of the table
                Vector<Vector<Object>> data = new Vector<Vector<Object>>();
                while (rs.next()) {
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(rs.getObject(columnIndex));
                    }
                    data.add(vector);
                }

                // It creates and displays the table



                model.setDataVector(data, columnNames);

                // Closes the Connection

                //JOptionPane.showMessageDialog(null, new JScrollPane(table));


                cstmt.close();
                dstmt.close();
                System.out.println("Success!!");
            }
            catch(SQLException ex) {

                System.out.println("Error in connection: " + ex.getMessage());
            }





        }
    }

}
