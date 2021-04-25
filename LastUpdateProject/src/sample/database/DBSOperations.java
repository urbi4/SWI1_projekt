package sample.database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DBSOperations {

    public static void add(Connection connection, Order order) {
        try {

            ArrayList<String> commands = new ArrayList<>();

            commands.add("Insert into address values(DEFAULT," + order.getPerson().getAddress().getHouseNumber() + ",'" + order.getPerson().getAddress().getStreet() + "','" + order.getPerson().getAddress().getCity() + "');");
            commands.add("Insert into person values(DEFAULT,'" + order.getPerson().getName() + "','" + order.getPerson().getSurname() + "','" + order.getPerson().getEmail() + "','" + order.getPerson().getPhoneNumber() + "',(SELECT max(id) from address));");
            commands.add("Insert into vehicle values(DEFAULT,'" + order.getVehicle().getPlateNumber() + "',(SELECT id from vehicletype where description='" + order.getVehicle().getVehicleType() + "'));"); //CHECK ENUM ?
            commands.add("Insert into orders values(DEFAULT, '" + order.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "', (SELECT id from timeRange where timeOfStart='" + order.getTimeOfStart().format(DateTimeFormatter.ISO_LOCAL_TIME) + "'),(SELECT max(id) from person), (SELECT max(id) from vehicle))");

            for (String problem : order.getProblems()) {
                commands.add("Insert into orders_has_problem values((SELECT max(id) from Orders),(SELECT id from problem where description='" + problem + "'));");
            }

            for (int i = 0; i < commands.size(); i++) {
                PreparedStatement statement = connection.prepareStatement(commands.get(i));
                statement.execute();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String[] getAvailableTimes(Connection connection, LocalDate localDate){
        ArrayList<String> allTimes = getAllTimes(connection);
        Set<String> setOfTimes = new HashSet<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT timeOfStart,timeOfEnd from TimeRange JOIN (SELECT * from Orders where date='" + localDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "') as Ord ON (TimeRange_id=TimeRange.id)");
            while (rs.next()){
                String timeOfStart = rs.getString("timeOfStart");
                String timeOfEnd = rs.getString("timeOfEnd");
                String result = timeOfStart.substring(0,5) + "-" + timeOfEnd.substring(0,5);
                if (!setOfTimes.contains(result)){
                    setOfTimes.add(result);
                }else {
                    allTimes.remove(allTimes.indexOf(result));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allTimes.toArray(new String[0]);
    }

    private static ArrayList<String> getAllTimes(Connection connection){
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
        return export;
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

    public static ArrayList<String> getProblems(Connection con, Integer id){
        ArrayList<String> export = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from (SELECT id from Orders where id='" + id + "') as O JOIN Orders_has_Problem ON (O.id=Orders_id) JOIN Problem ON (Problem.id=Problem_id)");
            while (rs.next()) {
                String problem = rs.getString("description");
                export.add(problem);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return export;
    }

    public static ArrayList<Order> getList(Connection con, LocalDate date){
        ArrayList<Order> export = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM (SELECT * from Orders where date='" + date.format(DateTimeFormatter.ISO_LOCAL_DATE) + "') AS o JOIN Person ON (Person_id=Person.id) JOIN Address ON (Address_id=Address.id) JOIN TimeRange ON (TimeRange_id=TimeRange.id) JOIN Vehicle ON (Vehicle_id=Vehicle.id) JOIN VehicleType ON (VehicleType_id=VehicleType.id)");
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");
                String houseNumber = rs.getString("houseNumber");
                String street = rs.getString("street");
                String city = rs.getString("city");
                String plateNumber = rs.getString("plateNumber");
                String vehicleType = rs.getString("description");
                LocalTime timeOfStart = LocalTime.parse(rs.getString("timeOfStart"),DateTimeFormatter.ISO_LOCAL_TIME);
                LocalTime timeOfEnd = LocalTime.parse(rs.getString("timeOfEnd"),DateTimeFormatter.ISO_LOCAL_TIME);
                LocalDate localDate = LocalDate.parse(rs.getString("date"),DateTimeFormatter.ISO_LOCAL_DATE);

                Vehicle vehicle = new Vehicle(plateNumber,vehicleType);
                Address address = new Address(city,street,houseNumber);
                Person person = new Person(name,surname,phoneNumber,email,address);
                Order order = new Order(Integer.valueOf(id),localDate,person,vehicle,null,timeOfStart,timeOfEnd);
                export.add(order);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return export;
    }

    public static void remove(Connection connection, Order order){
        int person_id = getPersonId(connection,order);
        int vehicle_id = getVehicleId(connection,order);
        int address_id = getAddressId(connection,person_id);
        try{
            String deleteOrder = "DELETE from Orders where id = '" + order.getId() + "'";
            String deletePerson = "DELETE from Person where id = '" + person_id + "'";
            String deleteAddress = "DELETE from Address where id = '" + address_id + "'";
            String deleteOrders_has_Problem = "DELETE from Orders_has_Problem where Orders_id = '" + order.getId() + "'";
            String deleteVehicle = "DELETE from Vehicle where id = '" + vehicle_id + "'";

            ArrayList<String> statements = new ArrayList<>(Arrays.asList(deleteOrders_has_Problem,deleteOrder,deletePerson,deleteAddress,deleteVehicle));
            for (String statement : statements) {
                PreparedStatement preparedStatement = connection.prepareStatement(statement);
                preparedStatement.execute();
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    private static int getAddressId(Connection connection, int person_id) {
        int export = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Address_id from Person where id='" + person_id + "'");
            while (rs.next()) {
                String address_id = rs.getString("Address_id");
                export = Integer.valueOf(address_id);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return export;
    }

    private static int getVehicleId(Connection connection, Order order) {
        int export = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Vehicle_id from Orders where id='" + order.getId() + "'");
            while (rs.next()) {
                String vehicle_id = rs.getString("Vehicle_id");
                export = Integer.valueOf(vehicle_id);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return export;
    }

    private static int getPersonId(Connection connection, Order order){
        int export = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Person_id from Orders where id='" + order.getId() + "'");
            while (rs.next()) {
                String person_id = rs.getString("Person_id");
                export = Integer.valueOf(person_id);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return export;
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
