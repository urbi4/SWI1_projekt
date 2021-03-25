package sample;

import java.sql.*;
import java.util.ArrayList;

public class DBOperations {

    public static void add(Connection con, Person person){
        try {
            String s = "Insert into customer(id,name,surname,address,phone,date,time) values("+null+",'"+person.getName()+"','"+person.getSurname()+"','"+person.getAddress()+"',"+person.getPhone()+",'"+person.getDate()+"','"+person.getTime()+"');";
            PreparedStatement preparedStmt = con.prepareStatement(s);
            preparedStmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void remove(Connection con,Person person){
        try{
            String s = "Delete from customer where name = '"+person.getName()+"';";
            PreparedStatement preparedStatement = con.prepareStatement(s);
            preparedStatement.execute();

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void update(Connection con, Person person){
        try{
            String s = "Update customer set name = '"+person.getName()+"',surname = '"+person.getSurname()+"',address ='"+person.getAddress()+"',phone = '"+person.getPhone()+"',date ='"+person.getDate()+"',time = '"+person.getTime()+"' where name = '" + person.getName()+"';";
            PreparedStatement preparedStatement = con.prepareStatement(s);
            preparedStatement.execute();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }



    public static ArrayList<Person> getList(Connection con){
        ArrayList<Person> export = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String address = rs.getString("address");
                String date = rs.getString("date");
                String phone = rs.getString("phone");
                String choice = rs.getString("time");

                export.add(new Person(name,surname,address,phone,date,choice));
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return export;
    }

}
