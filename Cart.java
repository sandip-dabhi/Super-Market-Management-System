import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Cart {
    static ArrayList<ArrayList<String>> data = new ArrayList<>();
    int count = 0;

    static void addItem(int code, int quantity){

        int flag = 0;
        float total = 0;
        String scode = String.valueOf(code);

        System.out.println(scode + "  "+ quantity + "  "+ data.size());

        // If item is already present then increase the quantity only
        int index = -1;

        for(int i = 0; i < data.size(); i++){

            if(Objects.equals(data.get(i).get(0), scode)){
                flag = 1;
                index = i;
            }
        }


        // If item already present
        if(flag == 1){

            int units = getUnits(code);

            if(quantity > units){
                JOptionPane.showMessageDialog(null, "Only "+ units +" units available!");
            }
            else {
                float price = Float.parseFloat(data.get(index).get(2));
                float discount = Float.parseFloat(data.get(index).get(3));
                int qty = Integer.parseInt(data.get(index).get(4));
                qty = qty + quantity;

                total = (price - (price * discount / 100)) * qty;

                updateQuantity(code, quantity);

//            String stotal = String.valueOf(total);
//            String squantity = String.valueOf(qty);

                data.get(index).set(4, String.valueOf(qty));
                data.get(index).set(5, String.valueOf(total));

                JOptionPane.showMessageDialog(null, "Product added to cart");

                showTable();
            }
        }

        // If item not present in list
        else if(flag == 0) {
            try {

                int units = getUnits(code);

                if (units == -999) {
                    JOptionPane.showMessageDialog(null, "No item found!");
                }
                else {

                    if(quantity > units){
                        JOptionPane.showMessageDialog(null, "Onlly "+ units +" units available!");
                    }
                    else {

                        new connection();

                        int pcode = 0;
                        float price =0 , dis =0;
                        String pname = null;

                        PreparedStatement ps = connection.c.prepareStatement(sqlQuery.addToChart);
                        ps.setInt(1, code);
                        ResultSet rs = ps.executeQuery();

//                        data.add(new ArrayList<>());
                        index = data.size();
                        System.out.println("array size: "+data.size());

                        while (rs.next()) {

                            pcode = rs.getInt(1);
                            pname = rs.getString(2);
                            price = rs.getFloat(3);
                            dis = rs.getFloat(4);

                        }


                        total = (price - (price * dis / 100)) * quantity;

                        updateQuantity(code, quantity);

                        System.out.println("this");

                        data.add(new ArrayList<>());
                        data.get(index).add(String.valueOf(pcode));
                        data.get(index).add(String.valueOf(pname));
                        data.get(index).add(String.valueOf(price));
                        data.get(index).add(String.valueOf(dis));
                        data.get(index).add(String.valueOf(quantity));
                        data.get(index).add(String.valueOf(total));

                        System.out.println("data["+index+"] size: "+data.get(index).size());



                        JOptionPane.showMessageDialog(null, "Product added to cart");

                        showTable();
                    }
                }


                connection.c.close();
                System.out.println("Connection Closed: add item (not present)");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    static int getUnits(int code){
        int units = -999;
        try {
            new connection();
            PreparedStatement ps = connection.c.prepareStatement(sqlQuery.checkUnits);
            ps.setInt(1, code);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                units = rs.getInt(1);
            }

            System.out.println("getUnits working");

            connection.c.close();
            System.out.println("Connection Closed: getUnits");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return units;
    }

    static void updateQuantity(int code, int quantity){
        try {
            new connection();
            PreparedStatement ps = connection.c.prepareStatement(sqlQuery.updateUnits);
            ps.setInt(1, quantity);
            ps.setInt(2, code);
            ps.execute();

            System.out.println("Units updated");

            connection.c.close();
            System.out.println("Connection Closed: updateQunatity");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    static String[][] getDataArray(){
        String[][] dataArray = new String[data.size()][];
        for (int i=0;i<data.size();i++){
            ArrayList<String> row = data.get(i);
            dataArray[i] = row.toArray(new String[0]);
        }

        System.out.println("Done");

        return dataArray;
    }




    static void showTable(){
        for(int i =0; i<data.size(); i++){
            for(int j = 0; j< data.get(i).size(); j++){
                System.out.print(data.get(i).get(j)+"  " );
            }
            System.out.println();
        }
    }
}
