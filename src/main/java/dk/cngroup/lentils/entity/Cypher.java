package dk.cngroup.lentils.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cypher")
public class Cypher
{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "stage")
    private int stage;

    @Column(name = "year")
    private int year;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    public Cypher() {
    }

    public Cypher(int id, String name, long latitude, long longitude) {
        this.id = id;
        this.name = name;
        this.stage = stage;
        this.year = year;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public int getStage()
    {
        return stage;
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Cypher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stage=" + stage +
                ", year=" + year +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cypher cypher = (Cypher) o;
        return id == cypher.id &&
                stage == cypher.stage &&
                year == cypher.year;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getName(), getLatitude(), getLongitude());
    }
}
