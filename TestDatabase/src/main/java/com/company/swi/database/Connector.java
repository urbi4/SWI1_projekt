package com.company.swi.database;

import java.sql.*;

public class Connector {
    public static Connection connectOld(){

        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?","root","Heslo123");

            Statement stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("SELECT jmeno FROM person");
            String s = "Insert into person values('Bob', 1)";
            PreparedStatement preparedStmt = con.prepareStatement(s);
            preparedStmt.execute();
            /*
            while (rs.next()) {
                String s = rs.getString("jmeno");
                System.out.println(s);
            }*/
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }

    public static Connection connect(){

        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?","root","Heslo123");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;

    }
}
