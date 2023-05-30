package com.forme.agents.DTO;

import java.io.Serializable;

public class SellProductsResponse implements Serializable {

    public String renewaltoken ="";
    public String UUIDDevice  ="";
    public String devicetype  ="";
    public String DeviceId ="";
    public String Stock_Id = "";
    public String Quantity = "";
    public String Price = "";
    public String salaf = "";
    public String Currency_Id ="";
    public String phoneNumber ="";
    public  String transid = "";


    public SellProductsResponse(String renewaltoken, String UUIDDevice, String devicetype, String deviceId, String stock_Id, String quantity, String price, String salaf, String currency_Id, String phoneNumber,String transid) {
        this.renewaltoken = renewaltoken;
        this.UUIDDevice = UUIDDevice;
        this.devicetype = devicetype;
        this.DeviceId = deviceId;
        this.Stock_Id = stock_Id;
        this.Quantity = quantity;
        this.Price = price;
        this.salaf = salaf;
        this.Currency_Id = currency_Id;
        this.phoneNumber = phoneNumber;
        this.transid = transid;

    }
}
