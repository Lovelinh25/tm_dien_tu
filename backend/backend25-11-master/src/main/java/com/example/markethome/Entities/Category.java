package com.example.markethome.Entities;
import javax.persistence.*;
import java.util.Collection;
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;
    private String name ;
    @OneToMany(mappedBy ="category")
    private Collection<Service> services;

    public Category() {
    }

    public Category(Integer ID, String name, Collection<Service> services) {
        this.ID = ID;
        this.name = name;
        this.services = services;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Service> getServices() {
        return services;
    }

    public void setServices(Collection<Service> services) {
        this.services = services;
    }
}
