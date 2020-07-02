package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelCooperativeCompany;
import com.example.hotel.vo.HotelCooperativeCompanyVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface CompanyService {
    ResponseVO addCooperativeCompany(HotelCooperativeCompanyVO hotelCooperativeCompanyVO);

    ResponseVO deleteCooperativeCompany(HotelCooperativeCompanyVO hotelCooperativeCompanyVO);

    List<HotelCooperativeCompany> getCooperativeCompanies(int hotelId);

    void deleteCooperativeCompanyByHotel(int hotelId);
}
