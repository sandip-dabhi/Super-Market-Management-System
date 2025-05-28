
import java.sql.DriverManager;
import java.sql.Connection;

class connection {

    static Connection c = null;
    connection(){
        try{
            String url = "jdbc:mysql://localhost:3306/supermarket";
            String user = "root";
            String password = "Ywue@780";
            System.out.println("Connecting to mysql...");
            System.out.println("Connected");

            c = DriverManager.getConnection(url, user, password);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
