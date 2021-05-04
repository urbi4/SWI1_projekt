package backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

// Proc mate Orders (mnozne cislo) a v ostatnich pripadech spravne pouzivate jednotne cislo Person, Problem, Vehicle,...?

@Entity
@Table(name = "Orders", schema = "mydb")
public class Orders {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private Date date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timerange_id")
    private TimeRange timerange;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Vehicle vehicle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TimeRange getTimerange() {
        return timerange;
    }

    public void setTimerange(TimeRange timerange) {
        this.timerange = timerange;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return id == orders.id && Objects.equals(date, orders.date) && Objects.equals(timerange, orders.timerange) && Objects.equals(person, orders.person) && Objects.equals(vehicle, orders.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, timerange, person, vehicle);
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", date=" + date +
                ", timerange=" + timerange +
                ", person=" + person +
                ", vehicle=" + vehicle +
                '}';
    }
}
