package com.example.hotel.bl.user;

import com.example.hotel.po.CreditRecord;

import java.util.List;

public interface RecordService {

    /**
     * 生成一条信用变更记录
     * @param userId
     * @param orderId
     * @param change
     * @param action
     */
    void addCreditRecord(int userId, Integer orderId, double change, String action);

    /**
     * 获取用户信用变更记录
     * @param userId
     * @return
     */
    List<CreditRecord> getUserCreditRecords(int userId);
}
