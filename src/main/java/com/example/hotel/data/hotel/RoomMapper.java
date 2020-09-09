package com.example.hotel.data.hotel;

import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper {

    /**
     * 可用房间数 = 原可用房间数 - {num}
     * @param hotelId
     * @param roomType
     * @param num
     * @return
     */
    int updateRoomInfo(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType,@Param("num") Integer num);

    int updateRoomPrice(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType,@Param("price") double price);

    int updateRoomTotal(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType,@Param("rooms") Integer rooms);

    int insertRoom(HotelRoom hotelRoom);

    List<HotelRoom> selectRoomsByHotelId(@Param("hotelId") Integer hotelId);

    HotelRoom retrieveRoom(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType);

    int getRoomCurNum(@Param("hotelId") Integer hotelId,@Param("roomType") String roomType);

    int deleteRoomByHotel(@Param("hotelId") Integer hotelId);

    List<HotelRoom> getAllRooms();
}
