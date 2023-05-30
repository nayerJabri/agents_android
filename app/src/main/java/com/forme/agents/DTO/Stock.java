package com.forme.agents.DTO;

import java.io.Serializable;

public class Stock implements Serializable {
    String id="";
    String name="";
    String price="";
    String screenNumber="";

    public Stock() {
    }

    public Stock(String id, String name, String price, String screenNumber) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.screenNumber = screenNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(String screenNumber) {
        this.screenNumber = screenNumber;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", screenNumber='" + screenNumber + '\'' +
                '}';
    }
}
