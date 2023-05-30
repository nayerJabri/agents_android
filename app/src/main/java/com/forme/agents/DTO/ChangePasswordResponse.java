package com.forme.agents.DTO;

public class ChangePasswordResponse {

    public String status;
    public String message;
    public String data;
    public  String transid = "";

    public ChangePasswordResponse(String status, String message, String data, String transid) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.transid = transid;
    }
}
