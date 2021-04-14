package com.javatechie.crud.example.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "timerange", schema = "mydb", catalog = "")
public class TimerangeEntity {
    private int id;
    private Time timeOfStart;
    private Time timeOfEnd;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "timeOfStart")
    public Time getTimeOfStart() {
        return timeOfStart;
    }

    public void setTimeOfStart(Time timeOfStart) {
        this.timeOfStart = timeOfStart;
    }

    @Basic
    @Column(name = "timeOfEnd")
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

        TimerangeEntity that = (TimerangeEntity) o;

        if (id != that.id) return false;
        if (timeOfStart != null ? !timeOfStart.equals(that.timeOfStart) : that.timeOfStart != null) return false;
        if (timeOfEnd != null ? !timeOfEnd.equals(that.timeOfEnd) : that.timeOfEnd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (timeOfStart != null ? timeOfStart.hashCode() : 0);
        result = 31 * result + (timeOfEnd != null ? timeOfEnd.hashCode() : 0);
        return result;
    }
}
