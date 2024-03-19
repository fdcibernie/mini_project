package com.example.consumerbill.bill_info.data.datasource.model;

import com.example.consumerbill.bill_info.domain.model.ConsumerBill;

public class ConsumerModel extends ConsumerBill {

    public ConsumerModel(String accountNo, String address,
                         double billAmount, double billPenalty,
                         String biller, String consumption, String contactNum,
                         String dueDate, String name, String paymentStatus,
                         String presentReading, String preReading,String keys)
    {
        super(accountNo, address, billAmount, billPenalty, biller, consumption, contactNum, dueDate, name, paymentStatus, presentReading, preReading,keys);
    }
}
