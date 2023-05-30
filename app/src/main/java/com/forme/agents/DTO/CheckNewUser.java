package com.forme.agents.DTO;

import java.io.Serializable;

public class CheckNewUser implements Serializable {

    public String fullname;
    public String storename;
    public String mobile;
    public String UUIDUser;
    public String DeviceIdUser;


    public CheckNewUser() {
    }

    public CheckNewUser(String fullname, String storename, String mobile, String UUIDUser, String deviceIdUser) {
        this.fullname = fullname;
        this.storename = storename;
        this.mobile = mobile;
        this.UUIDUser = UUIDUser;
        DeviceIdUser = deviceIdUser;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getUUIDUser() {
        return UUIDUser;
    }

    public void setUUIDUser(String UUIDUser) {
        this.UUIDUser = UUIDUser;
    }

    public String getDeviceIdUser() {
        return DeviceIdUser;
    }

    public void setDeviceIdUser(String deviceIdUser) {
        DeviceIdUser = deviceIdUser;
    }
}
