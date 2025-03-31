import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class showTable {
    static Object[][] searchData(String table, int col){
        try{
            String query = null;
            int row = 0, i;

            new connection();

            // If Products table
            if(table == "Products"){
                query = sqlQuery.showProducts;
            }
            // If c_login or a_login table
            else{
                query = sqlQuery.showLogin(table);
            }

            // Row count
            Statement s = connection.c.createStatement();
            ResultSet rs = s.executeQuery(sqlQuery.rowCount(table));
            while(rs.next()) {
                row = rs.getInt(1);
                System.out.println(row);
            }

            Object[][] data = new Object[row][col];

            s = connection.c.createStatement();
            rs = s.executeQuery(query);

//            while(rs.next()){
//                for(int j = 0; j < col; j++){
//                    data[i][j] = rs.getObject(j);
//                }
//                i++;
//            }

            if(table == "Products"){
                i = 0;
                while(rs.next()){
                    data[i][0] = rs.getInt(1);
                    data[i][1] = rs.getString(2);
                    data[i][2] = rs.getFloat(3);
                    data[i][3] = rs.getFloat(4);
                    data[i][4] = rs.getInt(5);
                    i++;
                }
            }
            else //if(table == "c_login" || table == "a_login")
            {
                i = 0;
                while(rs.next()){
                    data[i][0] = rs.getString(1);
                    data[i][1] = rs.getString(2);
                    data[i][2] = rs.getString(3);
                    i++;
                }
            }


            connection.c.close();
            System.out.println("Connection closed");
            return data;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static Object[][] filterData(String table, String field, String value, int col){
        try{
            String query = null;
            int row = 0, i;

            new connection();

            // If Products table
            if(table == "Products"){
                query = sqlQuery.filterProducts(field, value);
            }

            // If c_login or a_login table
            else{
                query = sqlQuery.filterLogin(table, field, value);
            }

            // Row count
            Statement s = connection.c.createStatement();
            ResultSet rs = s.executeQuery(sqlQuery.searchRowCount(table, field, value));
            while(rs.next()) {
                row = rs.getInt(1);
                System.out.println(row);
            }

            Object[][] data = new Object[row][col];

            s = connection.c.createStatement();
            rs = s.executeQuery(query);

            if(table == "Products"){
                i = 0;
                while(rs.next()){
                    data[i][0] = rs.getInt(1);
                    data[i][1] = rs.getString(2);
                    data[i][2] = rs.getFloat(3);
                    data[i][3] = rs.getFloat(4);
                    data[i][4] = rs.getInt(5);
                    i++;
                }
            }

            else if(table == "c_login" || table == "a_login"){
                i = 0;
                while(rs.next()){
                    data[i][0] = rs.getString(3);
                    data[i][1] = rs.getString(4);
                    data[i][2] = rs.getString(1);
                    i++;
                }
            }


            connection.c.close();
            System.out.println("Connection closed");
            return data;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
