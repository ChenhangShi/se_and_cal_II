package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;

import java.util.List;

public interface RoomService {

    /**
     * 获取某个酒店的全部房间信息
     * @param hotelId
     * @return
     */
    List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId);

    HotelRoom retrieveRoom(Integer hotelId, String roomType);

    /**
     * 添加酒店客房信息
     * @param hotelRoom
     */
    void insertRoomInfo(HotelRoom hotelRoom);

    /**
     * 预订酒店后更新客房房间数量
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    ResponseVO updateRoomInfo(Integer hotelId, String roomType, Integer rooms);

    void updateRoomPrice(Integer hotelId, String roomType, double price);

    void updateRoomTotal(Integer hotelId, String roomType, Integer rooms);

    /**
     * 获取酒店指定房间剩余数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId, String roomType);

    void deleteRoomByHotel(Integer hotelId) throws ServiceException;

    List<RoomVO> convertRoomPOListToRoomVOList(List<HotelRoom> rooms);

    List<RoomVO> getAllRooms();
}
