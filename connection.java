
import java.sql.DriverManager;
import java.sql.Connection;

class connection {

    static Connection c = null;
    connection(){
//        Statement s;
//        PreparedStatement ps;
        try{
            // Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/supermarket";
            String user = "root";
            String password = "Ywue@780";
            System.out.println("Connecting to mysql...");
            // Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Connected");

            c = DriverManager.getConnection(url, user, password);
//            s = c.createStatement();
//            ps = c.prepareStatement(query);
//            c.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}