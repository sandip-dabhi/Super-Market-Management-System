import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Login implements ActionListener {

    String table;
    JFrame f1;
    JButton b1,b2;
    JLabel l1,l2,l3,l4;
    JTextField tf1;
    JPasswordField tf2;
    Login(String tableType){
        table = tableType;

        f1 = new JFrame("Login");
        l1 = new JLabel("Please Login into Application");
        l1.setBounds(50,20,200,20);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        f1.add(l1);

        l2 = new JLabel("UserId:");
        l2.setBounds(50,70,80,20);
        f1.add(l2);

        l3 = new JLabel("Password:");
        l3.setBounds(50,100,80,20);
        f1.add(l3);


        tf1 = new JTextField();
        tf1.setBounds(130,70,120,20);
        f1.add(tf1);

        tf2 = new JPasswordField();
        tf2.setBounds(130,100,120,20);
        f1.add(tf2);


        b1 = new JButton("Login");
        b1.setBounds(50,130,200,30);
        b1.setFocusable(false);
        b1.addActionListener(this);
        f1.add(b1);


        l4 = new JLabel("Don't have user account?");
        l4.setBounds(50,250,200,20);
        l4.setHorizontalAlignment(SwingConstants.CENTER);
        f1.add(l4);

        b2 = new JButton("Sign up");
        b2.setBounds(50,280,200,30);
        b2.setFocusable(false);
        b2.addActionListener(this);
        f1.add(b2);


        f1.setSize(320,400);
        f1.setLayout(null);
        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // If "Sign Up" button is pressed
        if(e.getSource()==b2){
            if(table == "c_login") {
                new Signin("c_login");
            }
            else{
                JOptionPane.showMessageDialog(null,"Only current admins can add new admins");
            }
        }

        String uid = tf1.getText();
        String pass = tf2.getText();

        // If "Add" button is pressed
        if(e.getSource()==b1){

            // If any TextField is empty
            if(Objects.equals(uid, "") || Objects.equals(pass, "")){
                JOptionPane.showMessageDialog(null,"Please fill the UserId and Password!");
            }

            else{
                new connection();
                String query = sqlQuery.loginCheck(table);

                try {
                    PreparedStatement ps = connection.c.prepareStatement(query);
                    ps.setString(1,uid);
                    ps.setString(2,pass);
                    ResultSet rs = ps.executeQuery();

                    // If returns a rows
                    if (rs.next()) {
                        f1.dispose();
                        if(table == "c_login") {
                            new Customer();
                        }
                        else{
                            new Admin();
                        }
                    }
                    // If no rows returned
                    else{
                        JOptionPane.showMessageDialog(null,"Invalid UserId or Password!");
                        tf2.setText(null);
                    }
                    connection.c.close();
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
