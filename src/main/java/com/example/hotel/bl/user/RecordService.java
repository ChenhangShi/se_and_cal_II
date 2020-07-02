package com.example.hotel.bl.user;

import com.example.hotel.po.CreditRecord;

import java.util.List;

public interface RecordService {

    void addCreditRecord(int userId, Integer orderId, double change, String action);

    List<CreditRecord> getUserCreditRecords(int userId);
}
