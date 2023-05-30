package com.forme.agents.DTO;

import java.io.Serializable;

public class PriceResponse implements Serializable {

    public String renewaltoken ="";
    public String UUIDDevice  ="";
    public String devicetype  ="";
    public String DeviceId ="";
    public String Stock_Id ="";
    public String Currency_Id= "";
    public  String transid = "";


    public PriceResponse(String renewaltoken, String UUIDDevice, String devicetype , String DeviceId, String Stock_Id,String Currency_Id,String transid) {
        this.renewaltoken = renewaltoken;
        this.UUIDDevice = UUIDDevice;
        this.Stock_Id = Stock_Id;
        this.devicetype = devicetype;
        this.DeviceId = DeviceId;
        this.Currency_Id = Currency_Id;
        this.transid = transid;

    }

}
