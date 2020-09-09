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

    /**
     * 获取单个房间信息
     * @param hotelId
     * @param roomType
     * @return
     */
    HotelRoom retrieveRoom(Integer hotelId, String roomType);

    /**
     * 添加或更新客房信息
     * 不存在时添加 已存在时更新价格、增加总数和可用数量
     * @param hotelRoom
     */
    void insertRoomInfo(HotelRoom hotelRoom);

    /**
     * 预订酒店后更新客房剩余房间数量
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    ResponseVO updateRoomInfo(Integer hotelId, String roomType, Integer rooms);

    void updateRoomPrice(Integer hotelId, String roomType, double price);

    /**
     * 更新房间总数
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    void updateRoomTotal(Integer hotelId, String roomType, Integer rooms);

    /**
     * 获取酒店指定房间剩余数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId, String roomType);

    /**
     * 删除酒店时调用
     * @param hotelId
     * @throws ServiceException
     */
    void deleteRoomByHotel(Integer hotelId) throws ServiceException;

    List<RoomVO> convertRoomPOListToRoomVOList(List<HotelRoom> rooms);

    /**
     * 单独返回所有的房间
     * @return
     */
    List<RoomVO> getAllRooms();
}
