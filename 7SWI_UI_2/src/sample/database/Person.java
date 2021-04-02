package sample.database;

public class Person {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private Address address;

    public Person(String name, String surname, String phoneNumber, String email, Address address) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
}
