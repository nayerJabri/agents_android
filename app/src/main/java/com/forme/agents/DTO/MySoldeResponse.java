package com.forme.agents.DTO;

import java.io.Serializable;

public class MySoldeResponse implements Serializable {

    public String renewaltoken ="";
    public String UUIDDevice  ="";
    public String devicetype  ="";
    public String DeviceId ="";
    public String Currency_Id ="";
    public  String transid = "";

    public MySoldeResponse(String renewaltoken, String UUIDDevice, String devicetype , String DeviceId,String Currency_Id,String transid ) {
        this.renewaltoken = renewaltoken;
        this.UUIDDevice = UUIDDevice;
        this.devicetype = devicetype;
        this.DeviceId = DeviceId;
        this.Currency_Id = Currency_Id;
        this.transid = transid;

    }

}
