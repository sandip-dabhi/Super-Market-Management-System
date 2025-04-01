public class sqlQuery {

//    // Customer
//    static String c_login_check  = "select email, password from c_login where email=? and password=?;";
//    static String c_signin_check  = "select email from c_login where email=?;";
//    static String c_add = "insert into c_login (fname, lname, email, password) values (?, ?, ?, ?);";
//
//
//    // Admin
//    static String a_login_check = "select email, password from a_login where email=? and password=?;";
//    static String a_signin_check  = "select email from a_login where email=?;";
//    static String a_add = "insert into a_login (fname, lname, email, password) values (?, ?, ?, ?);";



    static String loginCheck(String table){
        return "select email, password from " + table + " where email=? and password=?;";
    }

    static String signinCheck(String table){
        return "select email from " + table + " where email=?;";
    }

    static String addUser(String table){
        return "insert into " + table + " (fname, lname, email, password) values (?, ?, ?, ?);";
    }

    static String rowCount(String table) {
        return "select count(*) from " + table + ";";
    }

    static String showProducts = "Select * from Products;";

    static String showLogin(String table){
        return "Select fname, lname, email from "+ table +";";
    }

    static String searchRowCount(String table, String field, String value){
        return "Select count(*) from "+ table +" where "+ field +" like '"+ value +"';";
    }

    static String filterProducts(String field, String value){
        return "Select * from Products where "+ field +" like '"+ value +"';";
    }

    static String filterLogin(String table, String field, String value){
        return "Select * from "+ table +" where "+ field +" like '"+ value +"';";
    }

    static String checkUnits = "Select units from Products where pcode = ?;";

    static String addToChart = "Select pcode, pname, price, discount from Products where pcode = ?;";

    static String addUnits = "Update Products set units = units + ? where pcode = ?;";

    static String updateUnits = "Update Products set units = units - ? where pcode = ?";

    static String itemCheck = "Select * from Products where pcode = ?;";

    // Including product code
    static String addToProduct1 = "insert into products (pcode, pname, price, discount, units) values(?,?,?,?,?);";

    // Excluding Product code
    static String addToProduct2 = "insert into products (pname, price, discount, units) values(?,?,?,?);";

    static String deleteProduct = "delete from Products where pcode = ?;";

    static String updateProduct = "Update Products set pname = ?, price = ?, discount = ?, units = ? where pcode = ?;";

    static String accountCheck(String table){
        return "Select * from "+ table +" where email = ?;";
    }

    static String deleteAccount(String table){
        return "delete from "+ table +" where email = ?;";
    }

    static String example(String table){
        return "Select email,fname,lname from "+ table +";";
    }
}
