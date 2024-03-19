package com.example.consumerbill.billers.data.datasource.remote.model;

import com.example.consumerbill.billers.domain.model.Billers;

public class BillerModel {
    private String billerCode;
    private String billerCompleteName;
    private String billerName;
    private String billerStatus;
    private String billerType;
    private int billerImage;

    public BillerModel(String billerCode, String billerCompleteName, String billerName, String billerStatus, String billerType,int billerImage) {
        this.billerCode = billerCode;
        this.billerCompleteName = billerCompleteName;
        this.billerName = billerName;
        this.billerStatus = billerStatus;
        this.billerType = billerType;
        this.billerImage = billerImage;
    }

    public BillerModel() {

    }

    public Billers toBiller() {
        return new Billers(billerCode,billerCompleteName,billerName,billerStatus,billerType,billerImage);
    }

    public String getBillerCode() {
        return billerCode;
    }

    public String getBillerCompleteName() {
        return billerCompleteName;
    }

    public String getBillerName() {
        return billerName;
    }

    public String getBillerStatus() {
        return billerStatus;
    }

    public String getBillerType() {
        return billerType;
    }

    public int getBillerImage() {
        return billerImage;
    }
}
