package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelCooperativeCompany;
import com.example.hotel.vo.HotelCooperativeCompanyVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

public interface CompanyService {
    /**
     * 添加酒店的合作企业
     * @param hotelCooperativeCompanyVO
     * @return
     */
    ResponseVO addCooperativeCompany(HotelCooperativeCompanyVO hotelCooperativeCompanyVO);

    ResponseVO deleteCooperativeCompany(HotelCooperativeCompanyVO hotelCooperativeCompanyVO);

    /**
     * 获取本酒店的合作企业
     * @param hotelId
     * @return
     */
    List<HotelCooperativeCompany> getCooperativeCompanies(int hotelId);

    /**
     * 删除酒店时被调用
     * @param hotelId
     */
    void deleteCooperativeCompanyByHotel(int hotelId);
}
