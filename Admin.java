import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin implements ActionListener {

    JButton b1, b2, b3;
    Admin(){
        JFrame f1 = new JFrame("Administrator");
        f1.setSize(300,330);


        JLabel l1 = new JLabel("Choose Your Option");
        l1.setBounds(50,50,200,30);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        f1.add(l1);


        b1 = new JButton("Stock");
        b1.setBounds(50,100,200,30);
        b1.setFocusable(false);
        b1.addActionListener(this);
        f1.add(b1);

        b2 = new JButton("Administrator List");
        b2.setBounds(50,150,200,30);
        b2.setFocusable(false);
        b2.addActionListener(this);
        f1.add(b2);

        b3 = new JButton("Customer List");
        b3.setBounds(50,200,200,30);
        b3.setFocusable(false);
        b3.addActionListener(this);
        f1.add(b3);


        f1.setLayout(null);
        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){
            System.out.println("Stock");
            new Stock();
        }
        else if(e.getSource() == b2){
            System.out.println("Admin List");
            new adminList();
        }
        else if(e.getSource() == b3){
            System.out.println("Customer List");
            new customerList();
        }
    }
}
