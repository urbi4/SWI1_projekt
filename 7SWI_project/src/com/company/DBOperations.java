package com.company;

import java.sql.*;

public class DBOperations {

    public static void add(Connection con, String name, String surname){
        try {
            //Statement statement = con.createStatement();
            String s = "Insert into customer values('" + name + "','" + surname + "' );";
            PreparedStatement preparedStmt = con.prepareStatement(s);
            preparedStmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void remove(Connection con,String name, String surname){
        try{
            String s = "Delete from customer where name = '"+name+"' AND surname = '"+surname+"';";
            PreparedStatement preparedStatement = con.prepareStatement(s);
            preparedStatement.execute();

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void printAll(Connection con){
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name, surname FROM customer");

            while (rs.next()) {
                String s = rs.getString("name");
                String i = rs.getString("surname");
                System.out.println(s + " " + i);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

}
