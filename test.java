import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class test implements ActionListener {

    JButton b1,b2,b3,b4,b5;
    JTextField tf1,tf2,tf3;
    JFrame f1;

    JPanel center;
    test(){

        f1 = new JFrame("Customer");
        f1.setSize(1000,600);
        f1.setLayout(new BorderLayout());



        // For Top Panel
        JLabel l1 = new JLabel("Search by Item Name:");
        l1.setPreferredSize(new Dimension(150,25));

        tf1 = new JTextField();
        tf1.setPreferredSize(new Dimension(200,25));


        b4 = new JButton("🔍");
        b4.setPreferredSize(new Dimension(50,25));
        b4.setFocusable(false);
        b4.addActionListener(this);

        b5 = new JButton("X");
        b5.setPreferredSize(new Dimension(50,25));
        b5.setBackground(Color.RED);
        b5.setForeground(Color.WHITE);
        b5.setFocusable(false);
        b5.addActionListener(this);




        // For Side Panel
        JLabel l2 = new JLabel("Item Code:");
        l2.setPreferredSize(new Dimension(80,25));
        tf2 = new JTextField();
        tf2.setPreferredSize(new Dimension(90,25));

        JLabel l3 = new JLabel("Quantity:");
        l3.setPreferredSize(new Dimension(80,25));
        tf3 = new JTextField("1");
        tf3.setPreferredSize(new Dimension(90,25));

        b1 = new JButton("Add");
        b1.setPreferredSize(new Dimension(175,30));
        b1.setFocusable(false);
        b1.addActionListener(this);

        b2 = new JButton("Reset");
        b2.setPreferredSize(new Dimension(175,30));
        b2.setFocusable(false);
        b2.addActionListener(this);

        b3 = new JButton("View Cart");
        b3.setPreferredSize(new Dimension(175,30));
        b3.setFocusable(false);
        b3.addActionListener(this);




        JPanel top = new JPanel();
        JPanel side = new JPanel();

        top.setBackground(Color.LIGHT_GRAY);
        side.setBackground(Color.GRAY);

        top.setPreferredSize(new Dimension(100,40));
        side.setPreferredSize(new Dimension(250,100));


        f1.add(top,BorderLayout.NORTH);
        f1.add(side,BorderLayout.EAST);
        showTable2();



//        If table is not present at center then uncomment this block

//        center = new JPanel();
//        center.setBackground(Color.DARK_GRAY);
//        center.setPreferredSize(new Dimension(100,100));
//        f1.add(center,BorderLayout.CENTER);



        // Border layout in side layout
        side.setLayout(new BorderLayout());

        JPanel a = new JPanel();
        a.setPreferredSize(new Dimension(250,200));
        a.setBackground(Color.LIGHT_GRAY);
        side.add(a,BorderLayout.NORTH);

        JPanel b = new JPanel();
        b.setPreferredSize(new Dimension(250,100));
        b.setBackground(Color.LIGHT_GRAY);
        side.add(b,BorderLayout.CENTER);

        JPanel c = new JPanel();
        c.setPreferredSize(new Dimension(250,50));
        c.setBackground(Color.LIGHT_GRAY);
        side.add(c,BorderLayout.SOUTH);

        JLabel m1,m2,m3,m4,m5;
        m1 = new JLabel("1. Search Item code from table");
        m1.setPreferredSize(new Dimension(200,30));
        m2 = new JLabel("2. Add Item code from table");
        m2.setPreferredSize(new Dimension(200,30));
        m3 = new JLabel("3. Add Quantity to be purchased");
        m3.setPreferredSize(new Dimension(200,30));
        m4 = new JLabel("4. Click 'Add' to add item in cart");
        m4.setPreferredSize(new Dimension(200,30));
        m5 = new JLabel("5. Click 'View Cart' to see items");
        m5.setPreferredSize(new Dimension(200,30));



        // top
        top.add(l1);
        top.add(tf1);
        top.add(b4);
        top.add(b5);

        // side
        a.add(l2);
        a.add(tf2);
        a.add(l3);
        a.add(tf3);
        a.add(b2);
        a.add(b1);

        b.add(m1); b.add(m2); b.add(m3); b.add(m4); b.add(m5);
        
        c.add(b3);

        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent c) {
        String pcode, quantity;
        if(c.getSource()==b1){
            if(Objects.equals(tf2.getText(), "")){
                JOptionPane.showMessageDialog(null,"Please add Item code");
            }
            else {
                pcode = tf2.getText();
                quantity = tf3.getText();
                System.out.println("Add" + pcode + quantity);
            }
        }
        else if(c.getSource()==b2){
            tf2.setText(null);
            tf3.setText("1");
        }
        else if(c.getSource()==b3){
            System.out.println("View Chart");
        }
        else if(c.getSource()==b4){
            String item = tf1.getText();
            if(!Objects.equals(item, "")) {
                System.out.println(item);
            }
        }
        else if(c.getSource()==b5){
            tf1.setText(null);
        }
    }

    void showTable(){
        try {
            new connection();
            PreparedStatement ps = connection.c.prepareStatement(sqlQuery.example("c_login"));
            ResultSet rs = ps.executeQuery();
            System.out.println("done");


            String[] column = new String[]{"Email", "First Name", "Last Name"};

            ArrayList<ArrayList<String>> data = new ArrayList<>();
            int i = 0;


            while(rs.next()){
//                System.out.println(rs.getString(1));
//                System.out.println(rs.getString(2));
//                System.out.println(rs.getString(3));
//                System.out.println(rs.getString(4));

                data.add(new ArrayList<>());
                data.get(i).add(rs.getString(1));
                data.get(i).add(rs.getString(2));
                data.get(i).add(rs.getString(3));

                i++;


//                String email = rs.getString("email");
//                String fname = rs.getString("fname");
//                String lname = rs.getString("lname");
//
//
//                String tData[] = {email,fname,lname};
//                DefaultTableModel tModel = (DefaultTableModel)table.getModel();
//
//                tModel.addRow(tData);
            }

            String dataArray[][] = new String[data.size()][];
            for (i=0;i<data.size();i++){
                ArrayList<String> row = data.get(i);
                dataArray[i] = row.toArray(new String[0]);
            }

            for(i=0; i<data.size(); i++){
                for( int j=0; j<data.get(i).size(); j++){
                    System.out.print(data.get(i).get(j)+"  ");
                }
                System.out.println();
            }

            DefaultTableModel tmodel = new DefaultTableModel(dataArray,column);

            JTable table = new JTable(tmodel);
            JScrollPane sp = new JScrollPane(table);

            f1.add(sp,BorderLayout.CENTER);


            connection.c.close();
            System.out.println("Connection closed");
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    void showTable2(){
        String[] column = new String[]{"Code","Name","Price","Discount","Available Units"};
        Object[][] data = showTable.searchData("Products",5);

        DefaultTableModel tmodel = new DefaultTableModel(data,column);

        JTable table = new JTable(tmodel);
        JScrollPane sp = new JScrollPane(table);

        f1.add(sp,BorderLayout.CENTER);
    }
}
