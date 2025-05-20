import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Bill extends JFrame {

    Bill() {

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(0,0,400,80);
        add(p1);

        JLabel l1 = new JLabel("--------------------------------------------------------------------------------------------------");
        l1.setBounds(0,0,400,20);
        p1.add(l1);

        JLabel l2 = new JLabel("RECIPT");
        l2.setBounds(170, 15, 50, 20);
        p1.add(l2);

        JLabel l3 = new JLabel("==================================================================================");
        l3.setBounds(0, 30, 400, 20);
        p1.add(l3);

        JLabel l4 = new JLabel("#Shop_Name");
        l4.setBounds(10, 40, 100, 30);
        p1.add(l4);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        Date date = new Date();

        JLabel l5 = new JLabel(formatter.format(date));
        l5.setBounds(270, 40, 150, 30);
        p1.add(l5);

        JLabel l6 = new JLabel("==================================================================================");
        l6.setBounds(0, 60, 400, 30);
        p1.add(l6);




        JPanel p2 = new JPanel();
        // p2.setBackground(Color.red);
        p2.setLayout(null);
        add(p2);

        // JLabel[] label6;
        // label6 = new JLabel[amount.length];
        // for (int i = 0; i < amount.length; i++) {
        //     label6[i] = new JLabel(amount[i]);
        //     p1.add(label6[i]);
        // }
        // ArrayList<ArrayList<String>> data= new ArrayList<>();

        JLabel[] label1;
        label1 = new JLabel[Cart.data.size()];
        int y=0;
        for(int i=0;i<Cart.data.size();i++){
            label1[i] = new JLabel(Cart.data.get(i).get(4)+" x "+Cart.data.get(i).get(1));//index 4 qty,1 pname
            label1[i].setBounds(10, 0+y, 100, 30);
            y+=20;
            p2.add(label1[i]);
        }

        p2.setBounds(0,80,100,60+y);



        JPanel p3 = new JPanel();
        // p3.setBackground(Color.red);
        p3.setLayout(null);
        p3.setBounds(300,80,100,60+y);
        add(p3);

        JLabel[] label2;
        y=0;
        float totalamount = 0 ;

        label2 = new JLabel[Cart.data.size()];
        for(int i=0;i<Cart.data.size();i++){
            label2[i] = new JLabel("$"+Cart.data.get(i).get(5));//index 5 total
            label2[i].setBounds(0, 0+y, 100, 30);
            y+=20;
            p3.add(label2[i]);


            totalamount += Float.parseFloat(Cart.data.get(i).get(5));
        }




        JPanel p4 = new JPanel();
        // p4.setBackground(Color.red);
        p4.setLayout(null);
        p4.setBounds(0,150+y,400,50);
        add(p4);

        JLabel l9 =new JLabel("--------------------------------------------------------------------------------------------------");
        l9.setBounds(0, 0, 400, 30);
        p4.add(l9);

        JLabel l7 = new JLabel("TOTAL :");
        l7.setBounds(200, 10, 50, 30);
        p4.add(l7);

        JLabel l8 = new JLabel();
        l8.setText(Float.toString(totalamount));
        l8.setBounds(250, 10, 50, 30);
        p4.add(l8);

        setSize(400, 230+y);
        setLayout(null);
        setResizable(false);

        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }
}
