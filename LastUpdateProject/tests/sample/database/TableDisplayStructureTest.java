package sample.database;

import static org.junit.Assert.*;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class TableDisplayStructureTest {

    @Test
    public void CorrectObject(){
        Address address = new Address("Praha","Dlouhá","558");
        Person person = new Person("Karel","Novák","605605605","aaa@aaa.cz",address);
        Vehicle vehicle = new Vehicle("1t23456","CAR");
        ArrayList<String> problems = new ArrayList<>(Arrays.asList("PNEU","OIL","WIPERS"));
        Order order = new Order(1, LocalDate.of(2005,2,16),person,vehicle,problems, LocalTime.of(10,15,58),LocalTime.of(10,25,40));
        TableDisplayStructure tds = new TableDisplayStructure(order);

        String actualDate = tds.getDate();
        String actualTimeRange = tds.getTimeRange();

        String expectedDate = "16/02/2005";
        String expectedTimeRange = "10:15-10:25";

        assertEquals(expectedDate,actualDate);
        assertEquals(expectedTimeRange,actualTimeRange);

    }

}