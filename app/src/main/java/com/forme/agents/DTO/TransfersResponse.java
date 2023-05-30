package com.forme.agents.DTO;

import java.io.Serializable;

public class TransfersResponse implements Serializable {
    public String renewaltoken ="";
    public String UUIDDevice  ="";
    public String devicetype  ="";
    public String DeviceId ="";
    public String SenderName = "" ;
    public int SenderPhone = 0 ;
    public String ReceiverName = "" ;
    public int ReceiverPhone = 0 ;
    public int Quantity = 0 ;
    public String Currency_Id ="";
    public int Stock_Id = 0 ;
    public String Image = "" ;
    public int ExchangeID = 0 ;
    public int Governorate = 0 ;
    public String Region = "" ;
    public int TransferNumber = 0 ;
    public int Price = 0 ;
    public  String transid = "";

    public TransfersResponse() {
    }

    public TransfersResponse(String renewaltoken, String UUIDDevice, String devicetype, String deviceId, String senderName, int senderPhone, String receiverName, int receiverPhone, int quantity, String currency_Id, int stock_Id, String image, int exchangeID, int governorate, String region, int transferNumber, int price, String transid) {
        this.renewaltoken = renewaltoken;
        this.UUIDDevice = UUIDDevice;
        this.devicetype = devicetype;
        DeviceId = deviceId;
        SenderName = senderName;
        SenderPhone = senderPhone;
        ReceiverName = receiverName;
        ReceiverPhone = receiverPhone;
        Quantity = quantity;
        Currency_Id = currency_Id;
        Stock_Id = stock_Id;
        Image = image;
        ExchangeID = exchangeID;
        Governorate = governorate;
        Region = region;
        TransferNumber = transferNumber;
        Price = price;
        this.transid = transid;
    }

    public String getRenewaltoken() {
        return renewaltoken;
    }

    public void setRenewaltoken(String renewaltoken) {
        this.renewaltoken = renewaltoken;
    }

    public String getUUIDDevice() {
        return UUIDDevice;
    }

    public void setUUIDDevice(String UUIDDevice) {
        this.UUIDDevice = UUIDDevice;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public int getSenderPhone() {
        return SenderPhone;
    }

    public void setSenderPhone(int senderPhone) {
        SenderPhone = senderPhone;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public int getReceiverPhone() {
        return ReceiverPhone;
    }

    public void setReceiverPhone(int receiverPhone) {
        ReceiverPhone = receiverPhone;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getCurrency_Id() {
        return Currency_Id;
    }

    public void setCurrency_Id(String currency_Id) {
        Currency_Id = currency_Id;
    }

    public int getStock_Id() {
        return Stock_Id;
    }

    public void setStock_Id(int stock_Id) {
        Stock_Id = stock_Id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getExchangeID() {
        return ExchangeID;
    }

    public void setExchangeID(int exchangeID) {
        ExchangeID = exchangeID;
    }

    public int getGovernorate() {
        return Governorate;
    }

    public void setGovernorate(int governorate) {
        Governorate = governorate;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public int getTransferNumber() {
        return TransferNumber;
    }

    public void setTransferNumber(int transferNumber) {
        TransferNumber = transferNumber;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }


}
