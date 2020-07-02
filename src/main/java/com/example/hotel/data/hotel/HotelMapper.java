package com.example.hotel.data.hotel;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.Service;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface HotelMapper {

    int insertHotel(Hotel hotel);

    List<HotelVO> selectAllHotel();

    HotelVO selectById(@Param("id") Integer id);

    int deleteHotel(@Param("id") Integer id);

    HotelVO retrieveHotelByManager(@Param("managerId") int managerId);

    int setHotelManager(@Param("hotelId") Integer hotelId, @Param("managerId") Integer managerId);

    int updateHotelRate(int hotelId, double rate);

    int updateHotelInfo(int id, String address, BizRegion bizRegion, String description, HotelStar hotelStar);

    List<Service> getHotelServices(int hotelId);

    int addService(Service service);

    int deleteService(int hotelId, String type);

    int deleteServiceByHotel(int hotelId);
}
