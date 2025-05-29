import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class viewCart implements ActionListener {
    JFrame f1, f2, f3;
    JButton b1, b2, b3, b4, b5, b6;
    viewCart(){
        f1 = new JFrame("Cart");
        f1.setSize(1000, 600);
        f1.setLayout(new BorderLayout());

        JPanel side = new JPanel();
        side.setBackground(Color.LIGHT_GRAY);
        side.setPreferredSize(new Dimension(250,100));
        f1.add(side,BorderLayout.EAST);

        // For side panel
        side.setLayout(new BorderLayout());

        JPanel a = new JPanel();
        a.setPreferredSize(new Dimension(250,200));
        a.setBackground(Color.LIGHT_GRAY);
        side.add(a,BorderLayout.NORTH);

        JPanel c = new JPanel();
        c.setPreferredSize(new Dimension(250,50));
        c.setBackground(Color.LIGHT_GRAY);
        side.add(c,BorderLayout.SOUTH);


        b1 = new JButton("Add More items");
        b1.setPreferredSize(new Dimension(175,30));
        b1.setFocusable(false);
        b1.addActionListener(this);

        b2 = new JButton("Delete item");
        b2.setPreferredSize(new Dimension(175,30));
        b2.setFocusable(false);
        b2.addActionListener(this);

        b3 = new JButton("Update Quantity");
        b3.setPreferredSize(new Dimension(175,30));
        b3.setFocusable(false);
        b3.addActionListener(this);

        b4 = new JButton("Generate Bill");
        b4.setPreferredSize(new Dimension(175,30));
        b4.setFocusable(false);
        b4.addActionListener(this);

        a.add(b1);
        a.add(b2);
        a.add(b3);
        c.add(b4);


        String[] columns = {"Code","Name","Price","Discount","Quantity","Total"};
        String[][] data = Cart.getDataArray();

        DefaultTableModel tmodel = new DefaultTableModel(data,columns);
        JTable table = new JTable(tmodel);
        JScrollPane sp = new JScrollPane(table);

        f1.add(sp, BorderLayout.CENTER);
        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.setVisible(true);

        f1.pack();
    }


    JTextField tf1, tf2, tf3;
    public void removeItem(){


        f2 = new JFrame("Delete Item");
        f2.setSize(300,300);

        JLabel l1 = new JLabel("Enter code of item to be Deleted");
        l1.setBounds(0,50,300,30);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        f2.add(l1);

        JLabel l2 = new JLabel("Code:");
        l2.setBounds(50,100,50,20);
        f2.add(l2);

        tf1 = new JTextField();
        tf1.setBounds(100,100,150,20);
        f2.add(tf1);

        b5 = new JButton("Remove");
        b5.setBounds(50,150,200,30);
        b5.setFocusable(false);
        b5.addActionListener(this);
        f2.add(b5);


        f2.setLayout(null);
        f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f2.setVisible(true);


    }

    public void updateQuantity(){
        f3 = new JFrame("Update Quantity");
        f3.setSize(300,300);

        JLabel l1 = new JLabel("Enter code & quantity item to be Updated");
        l1.setBounds(0,50,300,30);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        f3.add(l1);

        JLabel l2 = new JLabel("Code:");
        l2.setBounds(50,100,80,20);
        f3.add(l2);

        tf2 = new JTextField();                         // code
        tf2.setBounds(130,100,120,20);
        f3.add(tf2);

        JLabel l3 = new JLabel("Quantity:");
        l3.setBounds(50,140,80,20);
        f3.add(l3);

        tf3 = new JTextField();                         // quantity
        tf3.setBounds(130,140,120,20);
        f3.add(tf3);


        b6 = new JButton("Update");
        b6.setBounds(50,190,200,30);
        b6.setFocusable(false);
        b6.addActionListener(this);
        f3.add(b6);

        f3.setLayout(null);
        f3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f3.setVisible(true);
    }



    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){                // Add more items
            System.out.println("Add more");
            f1.dispose();
        }

        else if(e.getSource() == b2){           // Delete item
            System.out.println("Delete item");
            removeItem();
        }

        else if(e.getSource() == b3){           // Update quantity
            System.out.println("Update quantity");
            updateQuantity();
        }

        else if(e.getSource() == b4){           // Generate bill
            System.out.println("Generate Bill");
            Bill bill = new Bill();
        }

        else if(e.getSource() == b5){           // delete button in other frame (f2)
            System.out.println("Remove");

            if(Objects.equals(tf1.getText(),"")){
                JOptionPane.showMessageDialog(null,"Please enter the Item Code!");
            }
            else{
                String code = tf1.getText();
                int flag = 0;
                int index = -1;

                for(int i=0 ; i < Cart.data.size(); i++){
                    if(Objects.equals(Cart.data.get(i).get(0), code)){
                        flag = 1;
                        index = i;
                    }
                }

                if(flag == 1){
                    int pcode = Integer.parseInt(Cart.data.get(index).get(0));
                    int quantity = Integer.parseInt(Cart.data.get(index).get(4));

                    Cart.data.remove(index);
                    JOptionPane.showMessageDialog(null,"Item removed!");
                    f1.dispose();
                    f2.dispose();
                    new viewCart();
                    try {
                        new connection();
                        PreparedStatement ps = connection.c.prepareStatement(sqlQuery.addUnits);
                        ps.setInt(1, quantity);
                        ps.setInt(2, pcode);
                        ps.execute();

                        System.out.println("Units updated");

                        connection.c.close();
                        System.out.println("Connection Closed: updateQunatity");
                    }
                    catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"No such item found!");
                    tf1.setText("");
                }
            }
        }

        else if(e.getSource() == b6){
            System.out.println("Update");

            if(Objects.equals(tf2.getText(),"") || Objects.equals(tf3.getText(),"")){
                JOptionPane.showMessageDialog(null,"Please enter the Item Code and Quantity!");
            }

            else{
                String code = tf2.getText();
                int flag = 0;
                int index = -1;

                for(int i=0 ; i < Cart.data.size(); i++){
                    if(Objects.equals(Cart.data.get(i).get(0), code)){
                        flag = 1;
                        index = i;
                    }
                }

                if(flag == 1){
                    int pcode = Integer.parseInt(Cart.data.get(index).get(0));
                    int oldQty = Integer.parseInt(Cart.data.get(index).get(4));
                    int newQty = Integer.parseInt(tf3.getText());
                    int diffQty = newQty - oldQty;
                    int units = Cart.getUnits(pcode);

                    if(units < diffQty){
                        JOptionPane.showMessageDialog(null, "Onlly "+ units +" units available!");
                    }

                    else if(newQty < 1){
                        JOptionPane.showMessageDialog(null, "Invalid Quantity!");
                    }

                    else {
                        Cart.data.get(index).set(4, String.valueOf(newQty));
                        f1.dispose();
                        f3.dispose();
                        new viewCart();
                        JOptionPane.showMessageDialog(null, "Quantity updated!");
                        try {
                            new connection();
                            PreparedStatement ps = connection.c.prepareStatement(sqlQuery.updateUnits);
                            ps.setInt(1, diffQty);
                            ps.setInt(2, pcode);
                            ps.execute();

                            System.out.println("Units updated");

                            connection.c.close();
                            System.out.println("Connection Closed: updateQunatity");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"No such item found!");
                    tf1.setText("");
                }
            }
        }
    }
}
