package com.forme.agents.DTO;

import java.io.Serializable;

public class RasidResponse implements Serializable {

    public String renewaltoken ="";
    public String UUIDDevice  ="";
    public String devicetype  ="";
    public String DeviceId ="";
    public String Mobilenumber ="";
    public  String transid = "";


    public RasidResponse(String renewaltoken, String UUIDDevice, String devicetype , String DeviceId, String Mobilenumber , String transid) {
        this.renewaltoken = renewaltoken;
        this.UUIDDevice = UUIDDevice;
        this.Mobilenumber = Mobilenumber;
        this.devicetype = devicetype;
        this.DeviceId = DeviceId;
        this.transid = transid;

    }

}
