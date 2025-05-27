import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Signin implements ActionListener{

    String table;
    JFrame f1;
    JButton b1;
    JLabel l1,l2,l3,l4,l5,l6;
    JTextField tf1,tf2,tf3;
    JPasswordField tf4,tf5;
    Signin(String typeTable){
        table = typeTable;
        f1 = new JFrame("Sign Up");


        l1 = new JLabel("Please Sign Up into Application");
        l1.setBounds(50,20,250,20);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        f1.add(l1);

        l2 = new JLabel("First Name:");
        l2.setBounds(50,70,130,20);
        f1.add(l2);

        l3 = new JLabel("Last Name:");
        l3.setBounds(50,100,130,20);
        f1.add(l3);

        l4 = new JLabel("User Id:");
        l4.setBounds(50,130,130,20);
        f1.add(l4);

        l5 = new JLabel("Password:");
        l5.setBounds(50,160,130,20);
        f1.add(l5);

        l6 = new JLabel("Confirm Password:");
        l6.setBounds(50,190,130,20);
        f1.add(l6);


        // First name
        tf1 = new JTextField();
        tf1.setBounds(180,70,120,20);
        f1.add(tf1);

        // Last name
        tf2 = new JTextField();
        tf2.setBounds(180,100,120,20);
        f1.add(tf2);

        // User Id
        tf3 = new JTextField();
        tf3.setBounds(180,130,120,20);
        f1.add(tf3);

        // Password
        tf4 = new JPasswordField();
        tf4.setBounds(180,160,120,20);
        f1.add(tf4);

        // Confirm Password
        tf5 = new JPasswordField();
        tf5.setBounds(180,190,120,20);
        f1.add(tf5);


        b1 = new JButton("Sign Up");
        b1.setBounds(50,240,250,30);
        b1.setFocusable(false);
        b1.addActionListener(this);
        f1.add(b1);
        f1.setSize(370,370);
        f1.setLayout(null);
        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String fname = tf1.getText();
        String lname = tf2.getText();
        String uid = tf3.getText();
        String pass = tf4.getText();

        if (e.getSource() == b1) {

            // if empty text fields
            if (Objects.equals(fname,"") || Objects.equals(lname,"") || Objects.equals(uid,"") || Objects.equals(pass,"") || Objects.equals(tf5.getText(),"")){
                JOptionPane.showMessageDialog(null, "Please fill all the text boxes!");
            }

            // passwords miss matched
            else if (!Objects.equals(tf4.getText(), tf5.getText())) {
                JOptionPane.showMessageDialog(null, "Passwords miss match!");
                tf4.setText(null);
                tf5.setText(null);
            }

            else{
                new connection();
                String query1 = sqlQuery.signinCheck(table);
                String query2 = sqlQuery.addUser(table);
                try {

                    PreparedStatement ps = connection.c.prepareStatement(query1);
                    ps.setString(1,uid);
                    ResultSet rs = ps.executeQuery();

                    // If returns a rows
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Account already created with same User id!");
                    }
                    else{
                        ps = connection.c.prepareStatement(query2);
                        ps.setString(1,fname);
                        ps.setString(2,lname);
                        ps.setString(3,uid);
                        ps.setString(4,pass);
                        ps.execute();
                        JOptionPane.showMessageDialog(null, "Account Created!");

                        if(table == "c_login") {
                            new Login("c_login");
                        }

                        f1.dispose();
                        connection.c.close();

                    }


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
