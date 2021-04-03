package sample.database;

import java.time.LocalDate;
import java.time.LocalTime;

public class Order {
    private LocalDate  date;
    private Person person;
    private Vehicle vehicle;
    private String[] problems;
    private LocalTime timeOfStart;
    private LocalTime timeOfEnd;

    public Order(LocalDate date, Person person, Vehicle vehicle, String[] problems, LocalTime timeOfStart, LocalTime timeOfEnd) {
        this.date = date;
        this.person = person;
        this.vehicle = vehicle;
        this.problems = problems;
        this.timeOfStart = timeOfStart;
        this.timeOfEnd = timeOfEnd;
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

    public LocalTime getTimeOfStart() {
        return timeOfStart;
    }

    public LocalTime getTimeOfEnd() {
        return timeOfEnd;
    }
}
