package sample.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Connector {

    public static Connection connect(){

        Connection con = null;
        try {
            Props properties = getProps("src/sample/database/DBprops.prop");
            con = DriverManager.getConnection(properties.getUrl(),properties.getUser(), properties.getPassword());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return con;

    }

    private static Props getProps(String path){
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            Props export = new Props(url,user,password);
            return export;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Props{
        private String url;
        private String user;
        private String password;

        public Props(String url, String user, String password) {
            this.url = url;
            this.user = user;
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }
    }
}
