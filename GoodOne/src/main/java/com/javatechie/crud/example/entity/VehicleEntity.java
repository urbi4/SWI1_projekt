package com.javatechie.crud.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "vehicle", schema = "mydb", catalog = "")
public class VehicleEntity {
    private int id;
    private String plateNumber;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "plateNumber")
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleEntity that = (VehicleEntity) o;

        if (id != that.id) return false;
        if (plateNumber != null ? !plateNumber.equals(that.plateNumber) : that.plateNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (plateNumber != null ? plateNumber.hashCode() : 0);
        return result;
    }
}
