package sample.database;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DBSOperationsTest {

    @Test
    public void getAvailableTimes(){
        String[] data = DBSOperations.getAvailableTimes(Connector.connect(), LocalDate.now());
        if (!Arrays.asList(data).isEmpty()) {
            return;
        }
        fail();
    }

    @Test
    public void getTypes(){
        String[] data = DBSOperations.getTypes(Connector.connect());
        if (!Arrays.asList(data).isEmpty()) {
            return;
        }
        fail();
    }

}