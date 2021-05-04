package backend.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "timerange", schema = "mydb")
public class TimeRange {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "timeofstart")
    private Time timeOfStart;

    @Column(name = "timeofend")
    private Time timeOfEnd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTimeOfStart() {
        return timeOfStart;
    }

    public void setTimeOfStart(Time timeOfStart) {
        this.timeOfStart = timeOfStart;
    }

    public Time getTimeOfEnd() {
        return timeOfEnd;
    }

    public void setTimeOfEnd(Time timeOfEnd) {
        this.timeOfEnd = timeOfEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeRange timeRange = (TimeRange) o;
        return id == timeRange.id && Objects.equals(timeOfStart, timeRange.timeOfStart) && Objects.equals(timeOfEnd, timeRange.timeOfEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeOfStart, timeOfEnd);
    }

    @Override
    public String toString() {
        return "TimeRange{" +
                "id=" + id +
                ", timeOfStart=" + timeOfStart +
                ", timeOfEnd=" + timeOfEnd +
                '}';
    }
}
