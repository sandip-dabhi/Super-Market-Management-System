import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


public class Customer implements ActionListener {
    JFrame f1,f2;
    JComboBox cb1;
    JButton b1,b2,b3,b4,b5,b6;
    JTextField tf1,tf2,tf3;
    Customer(){

        f1 = new JFrame("Customer");
        f1.setSize(1000,600);
        f1.setLayout(new BorderLayout());
        // For Top Panel
        JLabel l1 = new JLabel("Search Item:");
        l1.setPreferredSize(new Dimension(75,25));

        String[] elements = {"Name","Code"};
        cb1 = new JComboBox(elements);
        cb1.addActionListener(this);

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

        b6 = new JButton("↻");
        b6.setPreferredSize(new Dimension(50,25));
        b6.setFocusable(false);
        b6.addActionListener(this);




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
        side.setBackground(Color.LIGHT_GRAY);

        top.setPreferredSize(new Dimension(100,40));
        side.setPreferredSize(new Dimension(250,100));


        f1.add(top,BorderLayout.NORTH);
        f1.add(side,BorderLayout.EAST);

        displayTable();

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
        top.add(cb1);
        top.add(tf1);
        top.add(b4);
        top.add(b5);
        top.add(b6);

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
        String pcode = tf2.getText();
        String quantity = tf3.getText();
        String field = (String) cb1.getSelectedItem();

        if(c.getSource()==b1){              // For add Button

            if(Objects.equals(tf2.getText(), "") || Objects.equals(tf3.getText(), "")){
                JOptionPane.showMessageDialog(null,"Please add Item code or Quantity");
            }
            else if(Integer.parseInt(quantity) < 1){
                JOptionPane.showMessageDialog(null,"Invalid Quantity!");
            }
            else {
                int code = Integer.parseInt(pcode);
                int qty = Integer.parseInt(quantity);

                System.out.println("\nAdd  " + pcode +"  "+ quantity);

                Cart.addItem(code, qty);
            }
        }

        else if(c.getSource()==b2){         // For reset button
            tf2.setText(null);
            tf3.setText("1");
        }

        else if(c.getSource()==b3){         // For view chart button
            System.out.println("View Chart");
            new viewCart();
        }

        else if(c.getSource()==b4){         // For search button
            String value = tf1.getText();
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
                new Customer();
            }
        }

        else if(c.getSource()==b5){         // For cancel/clear button
            tf1.setText(null);
        }

        else if(c.getSource()==b6){         // For refresh button
            f1.dispose();
            new Customer();
        }
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
//        f1.repaint();
//        f1.revalidate();
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
}
