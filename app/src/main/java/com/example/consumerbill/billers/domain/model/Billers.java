package com.example.consumerbill.billers.domain.model;

import com.example.consumerbill.billers.data.datasource.remote.model.BillerModel;

public class Billers extends BillerModel {
    private final String billerCode;
    private final String billerCompleteName;
    private final String billerName;
    private final String billerStatus;
    private final String billerType;
    private final int billerImage;
    public Billers(String billerCode, String billerCompleteName, String billerName, String billerStatus, String billerType,int billerImage) {
        super();
        this.billerCode = billerCode;
        this.billerCompleteName = billerCompleteName;
        this.billerName = billerName;
        this.billerStatus = billerStatus;
        this.billerType = billerType;
        this.billerImage = billerImage;
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

    @Override
    public int getBillerImage() {
        return billerImage;
    }
}
