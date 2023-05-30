package com.forme.agents.DTO;

public class ChangePasswordModel {
    public String oldpassword ;
    public String newpassword ;
    public String confirmpassword ;
    public String renewaltoken ;
    public String UUIDDevice ;
    public String devicetype ;
    public String DeviceId ;
    public  String transid = "";


    public ChangePasswordModel(String oldpassword, String newpassword, String confirmpassword, String renewaltoken, String UUIDDevice, String devicetype, String deviceId , String transid) {
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
        this.confirmpassword = confirmpassword;
        this.renewaltoken = renewaltoken;
        this.UUIDDevice = UUIDDevice;
        this.devicetype = devicetype;
        this.DeviceId = deviceId;
        this.transid = transid;
    }
}
