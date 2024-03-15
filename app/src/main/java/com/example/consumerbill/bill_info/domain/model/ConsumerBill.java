package com.example.consumerbill.bill_info.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ConsumerBill implements Parcelable {
    private String accountNo;
    private String address;
    private double billAmount;
    private double billPenalty;
    private String biller;
    private String consumption;
    private String contactNum;
    private String dueDate;
    private String name;
    private String paymentStatus;
    private String presentReading;
    private String preReading;

    public ConsumerBill(String accountNo, String address, double billAmount, double billPenalty, String biller, String consumption, String contactNum, String dueDate, String name, String paymentStatus, String presentReading, String preReading) {
        this.accountNo = accountNo;
        this.address = address;
        this.billAmount = billAmount;
        this.billPenalty = billPenalty;
        this.biller = biller;
        this.consumption = consumption;
        this.contactNum = contactNum;
        this.dueDate = dueDate;
        this.name = name;
        this.paymentStatus = paymentStatus;
        this.presentReading = presentReading;
        this.preReading = preReading;
    }

    protected ConsumerBill(Parcel in) {
        accountNo = in.readString();
        address = in.readString();
        billAmount = in.readDouble();
        billPenalty = in.readDouble();
        biller = in.readString();
        consumption = in.readString();
        contactNum = in.readString();
        dueDate = in.readString();
        name = in.readString();
        paymentStatus = in.readString();
        presentReading = in.readString();
        preReading = in.readString();
    }

    public static final Creator<ConsumerBill> CREATOR = new Creator<ConsumerBill>() {
        @Override
        public ConsumerBill createFromParcel(Parcel in) {
            return new ConsumerBill(in);
        }

        @Override
        public ConsumerBill[] newArray(int size) {
            return new ConsumerBill[size];
        }
    };

    public String getAccountNo() {
        return accountNo;
    }

    public String getAddress() {
        return address;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public double getBillPenalty() {
        return billPenalty;
    }

    public String getBiller() {
        return biller;
    }

    public String getConsumption() {
        return consumption;
    }

    public String getContactNum() {
        return contactNum;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getName() {
        return name;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPresentReading() {
        return presentReading;
    }

    public String getPreReading() {
        return preReading;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(accountNo);
        dest.writeString(address);
        dest.writeDouble(billAmount);
        dest.writeDouble(billPenalty);
        dest.writeString(biller);
        dest.writeString(consumption);
        dest.writeString(contactNum);
        dest.writeString(dueDate);
        dest.writeString(name);
        dest.writeString(paymentStatus);
        dest.writeString(presentReading);
        dest.writeString(preReading);
    }
}
