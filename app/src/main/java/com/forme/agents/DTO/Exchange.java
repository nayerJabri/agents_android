package com.forme.agents.DTO;

import java.io.Serializable;

public class Exchange implements Serializable {

    String Id ="";
    String Name ="";

    public Exchange() {
    }

    public Exchange(String id, String name) {
        Id = id;
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
