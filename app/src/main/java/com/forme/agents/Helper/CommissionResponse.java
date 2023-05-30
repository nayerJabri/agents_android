package com.forme.agents.Helper;

import java.io.Serializable;

public class CommissionResponse implements Serializable {

    public String renewaltoken ="";
    public String UUIDDevice  ="";
    public String devicetype  ="";
    public String DeviceId ="";
    public String transid = "";
    public String Stock_Id ="" ;
    public String Quantity ="" ;
    public String Currency_Id ="" ;

    public CommissionResponse(String renewaltoken, String UUIDDevice, String devicetype, String deviceId, String transid, String stock_Id, String quantity, String currency_Id) {
        this.renewaltoken = renewaltoken;
        this.UUIDDevice = UUIDDevice;
        this.devicetype = devicetype;
        DeviceId = deviceId;
        this.transid = transid;
        Stock_Id = stock_Id;
        Quantity = quantity;
        Currency_Id = currency_Id;
    }
}
