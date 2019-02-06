package dk.cngroup.lentils.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cypher")
public class Cypher
{
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private long latitude;

    @Column(name = "longitude")
    private long longitude;

    public Cypher(int id)
    {
        this.id = id;
    }
    public Cypher()
    {
    }

    public Cypher(int id, String name, long latitude, long longitude)
    {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getLatitude()
    {
        return latitude;
    }

    public void setLatitude(long latitude)
    {
        this.latitude = latitude;
    }

    public long getLongitude()
    {
        return longitude;
    }

    public void setLongitude(long longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return "Cypher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude+
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cypher cypher = (Cypher) o;
        return getId() == cypher.getId() &&
                getLatitude() == cypher.getLatitude() &&
                getLongitude() == cypher.getLongitude() &&
                getName().equals(cypher.getName());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getName(), getLatitude(), getLongitude());
    }
}
