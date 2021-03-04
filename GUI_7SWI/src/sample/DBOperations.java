package sample;

import java.sql.*;
import java.util.ArrayList;

public class DBOperations {

    public static void add(Connection con, String name){
        try {
            //Statement statement = con.createStatement();
            String s = "Insert into customer value('" + name + "');";
            PreparedStatement preparedStmt = con.prepareStatement(s);
            preparedStmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void remove(Connection con,String name){
        try{
            String s = "Delete from customer where name = '"+name+"';";
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

    public static ArrayList<String> getList(Connection con){
        ArrayList<String> export = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM customer");

            while (rs.next()) {
                String s = rs.getString("name");
                export.add(s);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return export;
    }

}
