package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.CompanyService;
import com.example.hotel.data.hotel.CompanyMapper;
import com.example.hotel.po.HotelCooperativeCompany;
import com.example.hotel.vo.HotelCooperativeCompanyVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final static String ADD_ERROR="添加酒店合作企业失败";
    private final static String DELETE_ERROR="删除酒店合作企业失败";

    @Autowired
    private CompanyMapper companyMapper;

    public ResponseVO addCooperativeCompany(HotelCooperativeCompanyVO hotelCooperativeCompanyVO){
        try{
            HotelCooperativeCompany hotelCooperativeCompany=new HotelCooperativeCompany();
            BeanUtils.copyProperties(hotelCooperativeCompanyVO,hotelCooperativeCompany);
            companyMapper.addCooperativeCompany(hotelCooperativeCompany);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    public ResponseVO deleteCooperativeCompany(HotelCooperativeCompanyVO hotelCooperativeCompanyVO){
        try{
            HotelCooperativeCompany hotelCooperativeCompany=new HotelCooperativeCompany();
            BeanUtils.copyProperties(hotelCooperativeCompanyVO,hotelCooperativeCompany);
            companyMapper.deleteCooperativeCompany(hotelCooperativeCompany);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<HotelCooperativeCompany> getCooperativeCompanies(int hotelId){
        return companyMapper.getCooperativeCompanies(hotelId);
    }

    @Override
    public void deleteCooperativeCompanyByHotel(int hotelId){
        try{
            companyMapper.deleteCooperativeCompanyByHotel(hotelId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(DELETE_ERROR);
        }
    }
}
