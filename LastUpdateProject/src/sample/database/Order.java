package sample.database;

import java.time.LocalDate;
import java.time.LocalTime;

public class Order {
    private LocalDate  date;
    private Person person;
    private Vehicle vehicle;
    private String[] problems;
    private LocalTime timeRange;

    public Order(LocalDate date, Person person, Vehicle vehicle, String[] problems, LocalTime timeRange) {
        this.date = date;
        this.person = person;
        this.vehicle = vehicle;
        this.problems = problems;
        this.timeRange = timeRange;
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

    public String[] getProblems() {
        return problems;
    }

    public LocalTime getTimeRange() {
        return timeRange;
    }
}
