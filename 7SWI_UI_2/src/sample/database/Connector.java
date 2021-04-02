package sample.database;

import java.sql.*;

public class Connector {

    public static Connection connect(){

        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?","root","Heslo123");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return con;

    }
}
