package com.forme.agents.DTO;

import java.io.Serializable;

public class Currencie implements Serializable {
    String Currency_Id ="";
    String Currency_Name ="";

    public Currencie() {
    }

    public Currencie(String currency_Id, String currency_Name) {
        Currency_Id = currency_Id;
        Currency_Name = currency_Name;
    }

    public String getCurrency_Id() {
        return Currency_Id;
    }

    public void setCurrency_Id(String currency_Id) {
        Currency_Id = currency_Id;
    }

    public String getCurrency_Name() {
        return Currency_Name;
    }

    public void setCurrency_Name(String currency_Name) {
        Currency_Name = currency_Name;
    }

    @Override
    public String toString() {
        return "Currencie{" +
                "Currency_Id='" + Currency_Id + '\'' +
                ", Currency_Name='" + Currency_Name + '\'' +
                '}';
    }
}
