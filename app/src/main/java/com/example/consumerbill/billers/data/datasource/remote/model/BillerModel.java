package com.example.consumerbill.billers.data.datasource.remote.model;

import com.example.consumerbill.billers.domain.model.Billers;

public class BillerModel {
    private String billerCode;
    private String billerCompleteName;
    private String billerName;
    private String billerStatus;
    private String billerType;

    public BillerModel(String billerCode, String billerCompleteName, String billerName, String billerStatus, String billerType) {
        this.billerCode = billerCode;
        this.billerCompleteName = billerCompleteName;
        this.billerName = billerName;
        this.billerStatus = billerStatus;
        this.billerType = billerType;
    }

    public BillerModel() {

    }

    public Billers toBiller() {
        return new Billers(billerCode,billerCompleteName,billerName,billerStatus,billerType);
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
}
