package com.example.hotel.data.hotel;

import com.example.hotel.po.HotelCooperativeCompany;
import com.example.hotel.vo.HotelCooperativeCompanyVO;
import com.example.hotel.vo.ResponseVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CompanyMapper {
    int addCooperativeCompany(HotelCooperativeCompany hotelCooperativeCompany);

    int deleteCooperativeCompany(HotelCooperativeCompany hotelCooperativeCompany);

    List<HotelCooperativeCompany> getCooperativeCompanies(int hotelId);

    int deleteCooperativeCompanyByHotel(int hotelId);
}
