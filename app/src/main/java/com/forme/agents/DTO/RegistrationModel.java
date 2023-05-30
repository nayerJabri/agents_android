package com.forme.agents.DTO;

public class RegistrationModel {

    public String username ;
    public String password ;
    public String fullname ;
    public String email ;
    public String mobile ;
    public String storename ;
    public String UUIDDevice ;
    public String devicetype ;
    public String DeviceId ;
    public  String transid = "";


    public RegistrationModel(String username, String password, String fullname, String email, String mobile, String storename , String UUIDDevice , String devicetype ,String DeviceId ,String transid) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.mobile = mobile;
        this.storename = storename;
        this.UUIDDevice = UUIDDevice;
        this.devicetype = devicetype;
        this.DeviceId = DeviceId;
        this.transid = transid;
    }
}
