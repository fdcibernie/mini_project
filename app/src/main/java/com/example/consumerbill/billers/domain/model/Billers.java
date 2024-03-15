package com.example.consumerbill.billers.domain.model;

import com.example.consumerbill.billers.data.datasource.remote.model.BillerModel;

public class Billers extends BillerModel {
    private final String billerCode;
    private final String billerCompleteName;
    private final String billerName;
    private final String billerStatus;
    private final String billerType;
    public Billers(String billerCode, String billerCompleteName, String billerName, String billerStatus, String billerType) {
        super();
        this.billerCode = billerCode;
        this.billerCompleteName = billerCompleteName;
        this.billerName = billerName;
        this.billerStatus = billerStatus;
        this.billerType = billerType;
    }

    @Override
    public String getBillerCode() {
        return billerCode;
    }

    @Override
    public String getBillerCompleteName() {
        return billerCompleteName;
    }

    @Override
    public String getBillerName() {
        return billerName;
    }

    @Override
    public String getBillerStatus() {
        return billerStatus;
    }

    @Override
    public String getBillerType() {
        return billerType;
    }
}
