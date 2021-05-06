package sample.database;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ConnectorTest {

    @Test
    public void connectionTest(){
        try {
            Connection connection = Connector.connect();
            connection.close();
            assertTrue(connection.isClosed());
        } catch (Exception e) {
            fail();
        }

    }

}