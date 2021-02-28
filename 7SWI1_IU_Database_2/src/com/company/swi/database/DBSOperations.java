package com.company.swi.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class DBSOperations {

    public static void add(Connection con, String name, int age){
        try {
            Statement statement = con.createStatement();
            String s = "Insert into person values('" + name + "'," + age + " );";
            PreparedStatement preparedStmt = con.prepareStatement(s);
            preparedStmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void printAll(Connection con){
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT jmeno, vek FROM person");

            while (rs.next()) {
                String s = rs.getString("jmeno");
                int i = rs.getInt("vek");
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
            ResultSet rs = stmt.executeQuery("SELECT jmeno, vek FROM person");

            while (rs.next()) {
                String s = rs.getString("jmeno");
                export.add(s);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return export;
    }

}
