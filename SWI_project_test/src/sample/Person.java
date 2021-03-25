package sample;

public class Person {

    private String name;
    private String surname;
    private String address;
    private String phone;
    private String date;
    private String time;


    public Person(String name, String surname, String address, String phone, String date,String time) {

        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.date = date;
        this.time = time;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }
}
