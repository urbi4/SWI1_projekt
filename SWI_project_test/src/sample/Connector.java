package sample;

import java.sql.*;

public class Connector {

    public static Connection connect(){

        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys","root","Password321");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;

    }
}
