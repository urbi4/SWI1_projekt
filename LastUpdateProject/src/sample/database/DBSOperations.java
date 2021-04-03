package sample.database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBSOperations {

    public static void add(Connection connection, Order order) {
        try {

            ArrayList<String> commands = new ArrayList<>();

            commands.add("Insert into address values(DEFAULT," + order.getPerson().getAddress().getHouseNumber() + ",'" + order.getPerson().getAddress().getStreet() + "','" + order.getPerson().getAddress().getCity() + "');");
            commands.add("Insert into person values(DEFAULT,'" + order.getPerson().getName() + "','" + order.getPerson().getSurname() + "','" + order.getPerson().getEmail() + "','" + order.getPerson().getPhoneNumber() + "',(SELECT max(id) from address));");
            commands.add("Insert into vehicle values(DEFAULT,'" + order.getVehicle().getPlateNumber() + "',(SELECT id from vehicletype where description='" + order.getVehicle().getVehicleType() + "'));"); //CHECK ENUM ?
            commands.add("Insert into orders values(DEFAULT, '" + order.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "', (SELECT id from timeRange where timeOfStart='" + order.getTimeOfStart().format(DateTimeFormatter.ISO_LOCAL_TIME) + "'),(SELECT max(id) from person), (SELECT max(id) from vehicle))");

            for (String problem : order.getProblems()) {
                commands.add("Insert into orders_has_problem values((SELECT max(id) from person),(SELECT id from problem where description='" + problem + "'));");
            }

            for (int i = 0; i < commands.size(); i++) {
                PreparedStatement statement = connection.prepareStatement(commands.get(i));
                statement.execute();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addOld(Connection con, String name, int age){
        try {
            Statement statement = con.createStatement();
            String s = "Insert into person values('" + name + "'," + age + " );";
            PreparedStatement preparedStmt = con.prepareStatement(s);
            preparedStmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String[] getTimes(Connection connection){
        ArrayList<String> export = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT timeOfStart,timeOfEnd from TimeRange");
            while (rs.next()){
                String timeOfStart = rs.getString("timeOfStart");
                String timeOfEnd = rs.getString("timeOfEnd");
                String result = timeOfStart.substring(0,5) + "-" + timeOfEnd.substring(0,5);
                export.add(result);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return export.toArray(new String[0]);
    }

    public static String[] getTypes(Connection connection){
        ArrayList<String> export = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from vehicletype");
            while (rs.next()){
                String type = rs.getString("description");
                export.add(type);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return export.toArray(new String[0]);
    }

    public static ArrayList<TableDisplayStructure> getList(Connection con){
        ArrayList<TableDisplayStructure> export = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orders JOIN Person ON (Person_id=Person.id) JOIN Address ON (Address_id=Address.id) JOIN TimeRange ON (TimeRange_id=TimeRange.id) JOIN Vehicle ON (Vehicle_id=Vehicle.id) JOIN VehicleType ON (VehicleType_id=VehicleType.id)");
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");
                String houseNumber = rs.getString("houseNumber");
                String street = rs.getString("street");
                String city = rs.getString("city");
                String plateNumber = rs.getString("plateNumber");
                LocalTime timeOfStart = LocalTime.parse(rs.getString("timeOfStart"),DateTimeFormatter.ISO_LOCAL_TIME);
                LocalTime timeOfEnd = LocalTime.parse(rs.getString("timeOfEnd"),DateTimeFormatter.ISO_LOCAL_TIME);
                LocalDate localDate = LocalDate.parse(rs.getString("date"),DateTimeFormatter.ISO_LOCAL_DATE);

                Vehicle vehicle = new Vehicle(plateNumber,"VAN");
                Address address = new Address(city,street,houseNumber);
                Person person = new Person(name,surname,phoneNumber,email,address);
                Order order = new Order(localDate,person,vehicle,null,timeOfStart,timeOfEnd);
                export.add(new TableDisplayStructure(order));
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return export;
    }

//    public static ArrayList<Person> getList(Connection con){
//        ArrayList<Person> export = new ArrayList<>();
//        try {
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM person");
//            while (rs.next()) {
//                String name = rs.getString("name");
//                String surname = rs.getString("surname");
//                String address = rs.getString("address");
//                String email = rs.getString("email");
//                String phone = rs.getString("phone");
//
//                export.add(new Person(name,surname,phone,email,address));
//            }
//        }catch(SQLException throwables){
//            throwables.printStackTrace();
//        }
//        return export;
//    }



    public static void remove(Connection con,Person person){
        try{
            String s = "Delete from customer where name = '"+person.getName()+"';";
            PreparedStatement preparedStatement = con.prepareStatement(s);
            preparedStatement.execute();

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

//    public static void update(Connection con, Person person){
//        try{
//            String s = "Update customer set name = '"+person.getName()+"',surname = '"+person.getSurname()+"',address ='"+person.getAddress()+"',phone = '"+person.getPhone()+"',date ='"+person.getDate()+"',time = '"+person.getTime()+"' where name = '" + person.getName()+"';";
//            PreparedStatement preparedStatement = con.prepareStatement(s);
//            preparedStatement.execute();
//        }catch (SQLException throwables){
//            throwables.printStackTrace();
//        }
//
//    }
}
