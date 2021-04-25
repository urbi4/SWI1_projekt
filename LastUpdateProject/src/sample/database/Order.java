package sample.database;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Order {
    private Integer id;
    private LocalDate  date;
    private Person person;
    private Vehicle vehicle;
    private ArrayList<String> problems;
    private LocalTime timeOfStart;
    private LocalTime timeOfEnd;

    public Order(Integer id, LocalDate date, Person person, Vehicle vehicle, ArrayList<String> problems, LocalTime timeOfStart, LocalTime timeOfEnd) {
        this.id = id;
        this.date = date;
        this.person = person;
        this.vehicle = vehicle;
        this.problems = problems;
        this.timeOfStart = timeOfStart;
        this.timeOfEnd = timeOfEnd;
    }

    public void setProblems(ArrayList<String> problems) {
        this.problems = problems;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Person getPerson() {
        return person;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ArrayList<String> getProblems() {
        return problems;
    }

    public LocalTime getTimeOfStart() {
        return timeOfStart;
    }

    public LocalTime getTimeOfEnd() {
        return timeOfEnd;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", person=" + person +
                ", vehicle=" + vehicle +
                ", problems=" + problems +
                ", timeOfStart=" + timeOfStart +
                ", timeOfEnd=" + timeOfEnd +
                '}';
    }
}
