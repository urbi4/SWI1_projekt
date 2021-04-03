package sample.database;

import java.time.format.DateTimeFormatter;

public class TableDisplayStructure {
    private String date;
    private String timeRange;
    private String name;
    private String surname;
    private String plateNumber;
    private String phoneNumber;

    public TableDisplayStructure(Order order) {
        this.date = order.getDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
        this.timeRange = order.getTimeOfStart().format(DateTimeFormatter.ofPattern("HH:mm")) + "-" + order.getTimeOfEnd().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.name = order.getPerson().getName();
        this.surname = order.getPerson().getSurname();
        this.plateNumber = order.getVehicle().getPlateNumber();
        this.phoneNumber = order.getPerson().getPhoneNumber();
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTimeRange() {
        return timeRange;
    }
}
