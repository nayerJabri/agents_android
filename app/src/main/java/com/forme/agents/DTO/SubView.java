package com.forme.agents.DTO;

import java.io.Serializable;

public class SubView implements Serializable {
    String Id="";
    String Name="";
    Object subgroup;





    public SubView(String id, String name, Object subgroup) {
        Id = id;
        Name = name;
        this.subgroup = subgroup;

    }

    public SubView() {

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

    public Object getSubgroup() {
        return subgroup;
    }

    @Override
    public String toString() {
        return "SubView{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", subgroup=" + subgroup +
                '}';
    }

    public void setSubgroup(Object subgroup) {
        this.subgroup = subgroup;
    }

}
