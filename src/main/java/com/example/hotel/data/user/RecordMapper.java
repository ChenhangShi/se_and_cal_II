package com.example.hotel.data.user;

import com.example.hotel.po.CreditRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecordMapper {

    int addCreditRecord(CreditRecord creditRecord);

    List<CreditRecord> getUserCreditRecords(int userId);
}
