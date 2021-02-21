package com.company;

import java.sql.*;


public class Main {

        public static void main(String args[]) {

            Connection con = Connector.connect();
            DBOperations.add(con,"Pepa","Motif");
            DBOperations.add(con,"Lojza","Mojza");
            DBOperations.remove(con,"Pepa","Motif");
            DBOperations.remove(con,"Lojza","Mojza");
            DBOperations.add(con, "Ondra", "Urbi");
            DBOperations.printAll(con);
        }
    }
