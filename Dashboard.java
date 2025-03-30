import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard implements ActionListener{

    JButton b1, b2;             // b1 -> Admin     and     b2 -> Customer

    Dashboard(){

        JLabel l1 = new JLabel("SUPERMARKET BILLING SYSTEM");
        l1.setBounds(50,20,200,30);
        l1.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel l2 = new JLabel("Choose Your Option");
        l2.setBounds(50,50,200,30);
        l2.setHorizontalAlignment(SwingConstants.CENTER);


        b1 = new JButton("Administrator");
        b1.setBounds(50,100,200,30);
        b1.setFocusable(false);
        b1.addActionListener(this);

        b2 = new JButton("Customer");
        b2.setBounds(50,150,200,30);
        b2.setFocusable(false);
        b2.addActionListener(this);


        JFrame f = new JFrame("Dashboard");
        f.setSize(300, 280);

        f.add(l1); f.add(l2); f.add(b1); f.add(b2);

        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){
            System.out.println("Admin");
            new Login("a_login");
        }
        else if(e.getSource() == b2){
            System.out.println("Customer");
            new Login("c_login");
        }
    }
}