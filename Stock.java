import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class Stock implements ActionListener {

    JFrame f1, f2, f3, f4, f5;
    JComboBox cb1;
    JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9, tf10, tf11, tf12;

    Stock() {

        f1 = new JFrame("Stock");
        f1.setSize(1000, 600);
        f1.setLayout(new BorderLayout());


        // For Top Panel
        JLabel l1 = new JLabel("Search Item:");
        l1.setPreferredSize(new Dimension(75, 25));

        String[] elements = {"Name", "Code"};
        cb1 = new JComboBox(elements);
        cb1.addActionListener(this);

        tf1 = new JTextField();
        tf1.setPreferredSize(new Dimension(200, 25));


        b4 = new JButton("🔍");
        b4.setPreferredSize(new Dimension(50, 25));
        b4.setFocusable(false);
        b4.addActionListener(this);

        b5 = new JButton("X");
        b5.setPreferredSize(new Dimension(50, 25));
        b5.setBackground(Color.RED);
        b5.setForeground(Color.WHITE);
        b5.setFocusable(false);
        b5.addActionListener(this);

        b6 = new JButton("↻");
        b6.setPreferredSize(new Dimension(50, 25));
        b6.setFocusable(false);
        b6.addActionListener(this);

        b1 = new JButton("Add Item");
        b1.setPreferredSize(new Dimension(175, 30));
        b1.setFocusable(false);
        b1.addActionListener(this);

        b2 = new JButton("Delete Item");
        b2.setPreferredSize(new Dimension(175, 30));
        b2.setFocusable(false);
        b2.addActionListener(this);

        b3 = new JButton("Update Item");
        b3.setPreferredSize(new Dimension(175, 30));
        b3.setFocusable(false);
        b3.addActionListener(this);


        JPanel top = new JPanel();
        JPanel side = new JPanel();

        top.setBackground(Color.LIGHT_GRAY);
        side.setBackground(Color.LIGHT_GRAY);

        top.setPreferredSize(new Dimension(100, 40));
        side.setPreferredSize(new Dimension(250, 100));

        f1.add(top, BorderLayout.NORTH);
        f1.add(side, BorderLayout.EAST);

        displayTable();

        // Border layout in side layout
        side.setLayout(new BorderLayout());

        JPanel a = new JPanel();
        a.setPreferredSize(new Dimension(250, 200));
        a.setBackground(Color.LIGHT_GRAY);
        side.add(a, BorderLayout.NORTH);

        JPanel b = new JPanel();
        b.setPreferredSize(new Dimension(250, 100));
        b.setBackground(Color.LIGHT_GRAY);
        side.add(b, BorderLayout.CENTER);

        JPanel c = new JPanel();
        c.setPreferredSize(new Dimension(250, 50));
        c.setBackground(Color.LIGHT_GRAY);
        side.add(c, BorderLayout.SOUTH);

        JLabel m1, m2, m3, m4, m5;
        m1 = new JLabel("1. Search Item code from table");
        m1.setPreferredSize(new Dimension(200, 30));
        m2 = new JLabel("2. 'Add Item' to add Item in database");
        m2.setPreferredSize(new Dimension(200, 30));
        m3 = new JLabel("3. 'Delete Item' to delete");
        m3.setPreferredSize(new Dimension(200, 30));
        m4 = new JLabel("4. 'Update Item' to update database");
        m4.setPreferredSize(new Dimension(200, 30));
        m5 = new JLabel("5. Please give inputs properly");
        m5.setPreferredSize(new Dimension(200, 30));


        // top
        top.add(l1);
        top.add(cb1);
        top.add(tf1);
        top.add(b4);
        top.add(b5);
        top.add(b6);

        a.add(b1);
        a.add(b2);
        a.add(b3);

        b.add(m1);
        b.add(m2);
        b.add(m3);
        b.add(m4);
        b.add(m5);

        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){               // Add Item
            System.out.println("Add Item");
            addItem();
        }

        else if(e.getSource() == b2){           // Delete Item
            System.out.println("Delete Item");
            deleteItem();
        }

        else if(e.getSource() == b3){           // Update Item
            System.out.println("Update Item");
            updateItem();
        }

        else if(e.getSource()==b4){         // For search button
            String value = tf1.getText();
            String field = (String) cb1.getSelectedItem();
            if(field=="Code"){
                field = "pcode";
            }
            else{
                field = "pname";
            }

            if(!Objects.equals(value, "")) {
                System.out.println(value);
                search(field, value);
            }

            else{
                f1.dispose();
                new Stock();
            }
        }

        else if(e.getSource()==b5){         // For cancel/clear button
            tf1.setText(null);
        }

        else if(e.getSource()==b6){         // For refresh button
            f1.dispose();
            new Stock();
        }


        // For methods

        else if(e.getSource()==b7){         // add button of addItem method

            if(Objects.equals(tf3.getText(), "") || Objects.equals(tf4.getText(), "") || Objects.equals(tf5.getText(), "") || Objects.equals(tf6.getText(), "")){
                JOptionPane.showMessageDialog(null,"Please fill all the text boxes!");
            }

            else if(!Objects.equals(tf2.getText(),"")){      // Add without item code (auto Increment)

                int code = Integer.parseInt(tf2.getText());

                try {
                    boolean flag = recordCheck(code);

                    if(!flag){
                        new connection();
                        String name = tf3.getText();
                        float price = Float.parseFloat(tf4.getText());
                        float discount = Float.parseFloat(tf5.getText());
                        int units = Integer.parseInt(tf6.getText());

                        PreparedStatement ps = connection.c.prepareStatement(sqlQuery.addToProduct1);
                        ps.setInt(1, code);
                        ps.setString(2, name);
                        ps.setFloat(3, price);
                        ps.setFloat(4, discount);
                        ps.setInt(5, units);
                        ps.execute();

                        JOptionPane.showMessageDialog(null,"Item inserted!");

                        connection.c.close();
                        System.out.println("Connection Closed");

                        f1.dispose();
                        f3.dispose();
                        new Stock();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Item code already used!");
                    }
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

            else{                     
                try {
                    new connection();
                    String name = tf3.getText();
                    float price = Float.parseFloat(tf4.getText());
                    float discount = Float.parseFloat(tf5.getText());
                    int units = Integer.parseInt(tf6.getText());

                    PreparedStatement ps = connection.c.prepareStatement(sqlQuery.addToProduct2);
                    ps.setString(1, name);
                    ps.setFloat(2, price);
                    ps.setFloat(3, discount);
                    ps.setInt(4, units);
                    ps.execute();

                    JOptionPane.showMessageDialog(null,"Item inserted!");
                    connection.c.close();
                    System.out.println("Connection Closed");

                    f1.dispose();
                    f3.dispose();
                    new Stock();
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }


        // remove button for deleteItem method

        else if(e.getSource()==b8){
            if(Objects.equals(tf7.getText(),"")){
                JOptionPane.showMessageDialog(null,"Please enter Item code!");
            }
            else{
                int code = Integer.parseInt(tf7.getText());
                try{
                    boolean flag = recordCheck(code);

                    if(!flag){
                        JOptionPane.showMessageDialog(null,"No such item found!");
                    }

                    else{
                        new connection();

                        PreparedStatement ps = connection.c.prepareStatement(sqlQuery.deleteProduct);
                        ps.setInt(1, code);
                        ps.execute();

                        JOptionPane.showMessageDialog(null,"Product Deleted!");
                        connection.c.close();
                        System.out.println("Connection Closed");

                        f1.dispose();
                        f4.dispose();
                        new Stock();
                    }

                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }


        // for search button in updateItem
        else if(e.getSource()==b9){
            if(Objects.equals(tf8.getText(),"")){
                JOptionPane.showMessageDialog(null,"Please enter Item code!");
            }
            else{
                int code = Integer.parseInt(tf8.getText());
                boolean flag = true;
                try{
                    new connection();
                    PreparedStatement ps = connection.c.prepareStatement(sqlQuery.itemCheck);
                    ps.setInt(1, code);
                    ResultSet rs = ps.executeQuery();

                    if(!rs.next()){
                        flag = false;
                    }

                    if (!flag){
                        JOptionPane.showMessageDialog(null,"No such item found!");
                    }
                    else{
                        int pcode = 0;
                        String pname = null;
                        float price = 0;
                        float discount = 0;
                        int units = 0;

                        ps = connection.c.prepareStatement(sqlQuery.itemCheck);
                        ps.setInt(1, code);
                        rs = ps.executeQuery();

                        while(rs.next()){
                            pcode = rs.getInt(1);
                            pname = rs.getString(2);
                            price = rs.getFloat(3);
                            discount = rs.getFloat(4);
                            units = rs.getInt(5);
                        }

//                        System.out.println(pcode+pname+price+discount+units);

                        tf9.setEditable(true);
                        tf9.setText(String.valueOf(pname));

                        tf10.setEditable(true);
                        tf10.setText(String.valueOf(price));

                        tf11.setEditable(true);
                        tf11.setText(String.valueOf(discount));

                        tf12.setEditable(true);
                        tf12.setText(String.valueOf(units));
                    }


                    connection.c.close();
                    System.out.println("Connection Closed");
                }
                catch(SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        }

        // For update button in updateItem method
        else if(e.getSource()==b10){
            if(Objects.equals(tf8.getText(),"") || Objects.equals(tf9.getText(),"") || Objects.equals(tf10.getText(),"") || Objects.equals(tf11.getText(),"") || Objects.equals(tf12.getText(),"")){
                JOptionPane.showMessageDialog(null,"Please fill the text boxes!");
            }
            else{
                int pcode = Integer.parseInt(tf8.getText());
                String pname = tf9.getText();
                float price = Float.parseFloat(tf10.getText());
                float discount = Float.parseFloat(tf11.getText());
                int units = Integer.parseInt(tf12.getText());

//                System.out.println(pcode+pname+price+discount+units);

                try{
                    new connection();
                    PreparedStatement ps = connection.c.prepareStatement(sqlQuery.updateProduct);
                    ps.setString(1, pname);
                    ps.setFloat(2, price);
                    ps.setFloat(3, discount);
                    ps.setInt(4, units);
                    ps.setInt(5, pcode);
                    ps.execute();

                    JOptionPane.showMessageDialog(null,"Product values updated!");

                    connection.c.close();
                    System.out.println("Connection Closed");

                    f1.dispose();
                    f5.dispose();
                    new Stock();
                }
                catch(SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        }

    }


    boolean recordCheck(int code){
        boolean flag = true;
        try {
            new connection();
            PreparedStatement ps = connection.c.prepareStatement(sqlQuery.itemCheck);
            ps.setInt(1, code);
            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                flag = false;
            }
            connection.c.close();
            System.out.println("Connection Closed");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }


    String[] column = new String[]{"Item Code","Name","Price (₹)","Discount (%)","Available Units"};
    Object[][] data;
    JTable table;
    JScrollPane sp;
    DefaultTableModel tmodel;
    void displayTable(){
//        f1.remove(sp);
        data = showTable.searchData("Products",5);

        tmodel = new DefaultTableModel(data,column);

        table = new JTable(tmodel);
        sp = new JScrollPane(table);

        f1.add(sp,BorderLayout.CENTER);
    }

    void search(String field, String value){

        String val = value+"%";
        data = showTable.filterData("Products", field, val, 5);


        tmodel = new DefaultTableModel(data,column);

        table = new JTable(tmodel);
        sp = new JScrollPane(table);

        f2 = new JFrame("Search Results");
        f2.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(100,40));
        top.setBackground(Color.LIGHT_GRAY);

        JLabel l1 = new JLabel("Search results");
        l1.setPreferredSize(new Dimension(100,25));
        top.add(l1);

        f2.add(top, BorderLayout.NORTH);
        f2.add(sp, BorderLayout.CENTER);

        f2.setVisible(true);
        f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f2.pack();

    }

    void addItem(){
        f3 = new JFrame("Add Item");
        f3.setSize(300,370);

        JLabel l1 = new JLabel("Enter the details of the product");
        l1.setBounds(0,20,300,20);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        f3.add(l1);

        JLabel l2 = new JLabel("Code:");
        l2.setBounds(50,70,80,20);
        f3.add(l2);

        tf2 = new JTextField();
        tf2.setBounds(130,70,120,20);
        f3.add(tf2);


        JLabel l3 = new JLabel("Name:");
        l3.setBounds(50,100,80,20);
        f3.add(l3);

        tf3 = new JTextField();
        tf3.setBounds(130,100,120,20);
        f3.add(tf3);


        JLabel l4 = new JLabel("Price(₹):");
        l4.setBounds(50,130,80,20);
        f3.add(l4);

        tf4 = new JTextField();
        tf4.setBounds(130,130,120,20);
        f3.add(tf4);


        JLabel l5 = new JLabel("Discount(%):");
        l5.setBounds(50,160,80,20);
        f3.add(l5);

        tf5 = new JTextField();
        tf5.setBounds(130,160,120,20);
        f3.add(tf5);


        JLabel l6 = new JLabel("Units:");
        l6.setBounds(50,190,80,20);
        f3.add(l6);

        tf6 = new JTextField();
        tf6.setBounds(130,190,120,20);
        f3.add(tf6);


        b7 = new JButton("Add");
        b7.setBounds(50,220,200,30);
        b7.setFocusable(false);
        b7.addActionListener(this);
        f3.add(b7);

        JLabel l7 = new JLabel("Product code will automatically generated");
        l7.setBounds(0,270,300,20);
        l7.setHorizontalAlignment(SwingConstants.CENTER);
        f3.add(l7);

        JLabel l8 = new JLabel("if not provided");
        l8.setBounds(0,290,300,20);
        l8.setHorizontalAlignment(SwingConstants.CENTER);
        f3.add(l8);




        f3.setLayout(null);
        f3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f3.setVisible(true);
    }

    void deleteItem(){
        f4 = new JFrame("Delete Item");

        JLabel l1 = new JLabel("Enter item code to delete item");
        l1.setBounds(0,20,300,20);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        f4.add(l1);

        JLabel l2 = new JLabel("Code:");
        l2.setBounds(50,70,80,20);
        f4.add(l2);

        tf7 = new JTextField();
        tf7.setBounds(130,70,120,20);
        f4.add(tf7);

        b8 = new JButton("Remove");
        b8.setBounds(50,120,200,30);
        b8.setFocusable(false);
        b8.addActionListener(this);
        f4.add(b8);




        f4.setSize(300,240);
        f4.setLayout(null);
        f4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f4.setVisible(true);
    }
    
    
    void updateItem(){
        f5 = new JFrame("Update Item");

        JLabel l1 = new JLabel("Enter the product code & search");
        l1.setBounds(0,20,300,20);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        f5.add(l1);

        JLabel l2 = new JLabel("Code:");
        l2.setBounds(50,70,80,20);
        f5.add(l2);

        tf8 = new JTextField();
        tf8.setBounds(130,70,70,20);
        f5.add(tf8);

        b9 = new JButton("🔍");
        b9.setBounds(200,70,50,20);
        b9.setFocusable(false);
        b9.addActionListener(this);
        f5.add(b9);


        JLabel l3 = new JLabel("Name:");
        l3.setBounds(50,100,80,20);
        f5.add(l3);

        tf9 = new JTextField();
        tf9.setBounds(130,100,120,20);
        tf9.setEditable(false);
        f5.add(tf9);


        JLabel l4 = new JLabel("Price(₹):");
        l4.setBounds(50,130,80,20);
        f5.add(l4);

        tf10 = new JTextField();
        tf10.setBounds(130,130,120,20);
        tf10.setEditable(false);
        f5.add(tf10);


        JLabel l5 = new JLabel("Discount(%):");
        l5.setBounds(50,160,80,20);
        f5.add(l5);

        tf11 = new JTextField();
        tf11.setBounds(130,160,120,20);
        tf11.setEditable(false);
        f5.add(tf11);


        JLabel l6 = new JLabel("Units:");
        l6.setBounds(50,190,80,20);
        f5.add(l6);

        tf12 = new JTextField();
        tf12.setBounds(130,190,120,20);
        tf12.setEditable(false);
        f5.add(tf12);



        b10 = new JButton("update");
        b10.setBounds(50,220,200,30);
        b10.setFocusable(false);
        b10.addActionListener(this);
        f5.add(b10);


        JLabel l7 = new JLabel("On search text boxes will filled with");
        l7.setBounds(0,270,300,20);
        l7.setHorizontalAlignment(SwingConstants.CENTER);
        f5.add(l7);

        JLabel l8 = new JLabel("Current product details then edit & update");
        l8.setBounds(0,290,300,20);
        l8.setHorizontalAlignment(SwingConstants.CENTER);
        f5.add(l8);
        
        f5.setSize(300,370);
        f5.setLayout(null);
        f5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f5.setVisible(true);
    }
}
