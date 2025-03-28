import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class adminList implements ActionListener {

    JFrame f1, f2, f3;
    JComboBox cb1;
    JButton b1, b2, b4, b5, b6, b7;
    JTextField tf1, tf2;

    adminList() {

        f1 = new JFrame("Administrator List");
        f1.setSize(1000, 600);
        f1.setLayout(new BorderLayout());


        // For Top Panel
        JLabel l1 = new JLabel("Search Item:");
        l1.setPreferredSize(new Dimension(75, 25));

        String[] elements = {"First name","Last name","Email"};
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


        // For Side Panel
//        JLabel l2 = new JLabel("Item Code:");
//        l2.setPreferredSize(new Dimension(80, 25));
//        tf2 = new JTextField();
//        tf2.setPreferredSize(new Dimension(90, 25));
//
//        JLabel l3 = new JLabel("Quantity:");
//        l3.setPreferredSize(new Dimension(80, 25));
//        tf3 = new JTextField("1");
//        tf3.setPreferredSize(new Dimension(90, 25));

        b1 = new JButton("Add Account");
        b1.setPreferredSize(new Dimension(175, 30));
        b1.setFocusable(false);
        b1.addActionListener(this);

        b2 = new JButton("Delete Account");
        b2.setPreferredSize(new Dimension(175, 30));
        b2.setFocusable(false);
        b2.addActionListener(this);


        JPanel top = new JPanel();
        JPanel side = new JPanel();

        top.setBackground(Color.LIGHT_GRAY);
        side.setBackground(Color.LIGHT_GRAY);

        top.setPreferredSize(new Dimension(100, 40));
        side.setPreferredSize(new Dimension(250, 100));


        f1.add(top, BorderLayout.NORTH);
        f1.add(side, BorderLayout.EAST);

        displayTable();

//        If table is not present at center then uncomment this block

//        center = new JPanel();
//        center.setBackground(Color.DARK_GRAY);
//        center.setPreferredSize(new Dimension(100,100));
//        f1.add(center,BorderLayout.CENTER);


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

        JLabel m1, m2, m3, m4;
        m1 = new JLabel("1. Search Item code from table");
        m1.setPreferredSize(new Dimension(220, 30));
        m2 = new JLabel("2. 'Add account' creates account");
        m2.setPreferredSize(new Dimension(220, 30));
        m3 = new JLabel("3. 'Delete account' deletes account");
        m3.setPreferredSize(new Dimension(220, 30));
        m4 = new JLabel("4. Please give inputs properly");
        m4.setPreferredSize(new Dimension(220, 30));


        // top
        top.add(l1);
        top.add(cb1);
        top.add(tf1);
        top.add(b4);
        top.add(b5);
        top.add(b6);

        // side
//        a.add(l2);
//        a.add(tf2);
//        a.add(l3);
//        a.add(tf3);
//        a.add(b2);
        a.add(b1);
        a.add(b2);

        b.add(m1);
        b.add(m2);
        b.add(m3);
        b.add(m4);

        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==b1){              // For add account button
            System.out.println("Add account");
            new Signin("a_login");
        }

        else if(e.getSource()==b2){         // For delete account button
            System.out.println("Delete account");
            deleteAccount();
        }

        else if(e.getSource()==b4){         // For search button
            String value = tf1.getText();
            String field = (String) cb1.getSelectedItem();
            if(field == "Email"){
                field = "email";            // as per the database table format
            }
            else if(field == "First name"){
                field = "fname";
            }
            else{
                field = "lname";
            }

            if(!Objects.equals(value, "")) {
                System.out.println(value);
                search(field, value);
            }

            else{
                f1.dispose();
                new adminList();
            }
        }

        else if(e.getSource()==b5){         // For cancel/clear button
            tf1.setText(null);
        }

        else if(e.getSource()==b6){         // For refresh button
            f1.dispose();
            new adminList();
        }

        else if(e.getSource()==b7){
            deleteAcc();
        }
    }

    String[] column = new String[]{"First Name","Last Name","Email"};
    Object[][] data;
    JTable table;
    JScrollPane sp;
    DefaultTableModel tmodel;
    void displayTable(){
//        f1.remove(sp);
        data = showTable.searchData("a_login",3);

        tmodel = new DefaultTableModel(data,column);

        table = new JTable(tmodel);
        sp = new JScrollPane(table);

        f1.add(sp,BorderLayout.CENTER);
//        f1.repaint();
//        f1.revalidate();
    }

    void search(String field, String value){

        String val = value+"%";
        data = showTable.filterData("a_login", field, val, 3);


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
    
    void deleteAccount(){
        f3 = new JFrame("Delete Account");

        JLabel l1 = new JLabel("Enter email to delete account");
        l1.setBounds(0,20,300,20);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        f3.add(l1);

        JLabel l2 = new JLabel("Email:");
        l2.setBounds(50,70,80,20);
        f3.add(l2);

        tf2 = new JTextField();
        tf2.setBounds(130,70,120,20);
        f3.add(tf2);

        b7 = new JButton("Remove");
        b7.setBounds(50,120,200,30);
        b7.setFocusable(false);
        b7.addActionListener(this);
        f3.add(b7);




        f3.setSize(300,240);
        f3.setLayout(null);
        f3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f3.setVisible(true);
    }
    
    void deleteAcc(){
        if(Objects.equals(tf2.getText(),"")){
            JOptionPane.showMessageDialog(null,"Please enter the email!");
        }
        else{
            String email = tf2.getText();
            try{
                boolean flag = recordCheck(email);

                if(!flag){
                    JOptionPane.showMessageDialog(null,"No such account found!");
                }

                else{
                    new connection();

                    PreparedStatement ps = connection.c.prepareStatement(sqlQuery.deleteAccount("a_login"));
                    ps.setString(1, email);
                    ps.execute();

                    JOptionPane.showMessageDialog(null,"Account Removed!");
                    connection.c.close();
                    System.out.println("Connection Closed");

                    f1.dispose();
                    f3.dispose();
                    new adminList();
                }

            }
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    boolean recordCheck(String email){
        boolean flag = true;
        try {
            new connection();
            PreparedStatement ps = connection.c.prepareStatement(sqlQuery.accountCheck("a_login"));
            ps.setString(1, email);
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
}