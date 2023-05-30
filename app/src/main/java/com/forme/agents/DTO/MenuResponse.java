package com.forme.agents.DTO;

import java.io.Serializable;

public class MenuResponse implements Serializable {

    public String renewaltoken ="";
    public String UUIDDevice  ="";
    public String devicetype  ="";
    public String DeviceId ="";
    public  String transid = "";


    public MenuResponse(String renewaltoken, String UUIDDevice, String devicetype ,String DeviceId,String transid ) {
        this.renewaltoken = renewaltoken;
        this.UUIDDevice = UUIDDevice;
        this.devicetype = devicetype;
        this.DeviceId = DeviceId;
        this.transid  = transid;

    }

}
