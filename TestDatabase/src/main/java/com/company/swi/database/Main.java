package com.company.swi.database;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        Connection con = Connector.connect();
        DBOperations.add(con, "Carl", 42);
        DBOperations.printAll(con);


    }



}