package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final static String UPDATE_ROOM_INFO_ERROR = "更新房间可用数量失败";

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId) {
        return roomMapper.selectRoomsByHotelId(hotelId);
    }

    @Override
    public HotelRoom retrieveRoom(Integer hotelId,String roomType){
        return roomMapper.retrieveRoom(hotelId,roomType);
    }

    @Override
    public void insertRoomInfo(HotelRoom hotelRoom) {
        //如果该酒店的此类型房间已经存在，更新而不是插入
        int hotelId=hotelRoom.getHotelId();
        String roomType=hotelRoom.getRoomType().name();
        double price=hotelRoom.getPrice();
        int roomNum=hotelRoom.getTotal();

        HotelRoom room=retrieveRoom(hotelId,roomType);
        boolean alreadyExists = (room!=null);

        if(!alreadyExists)
            roomMapper.insertRoom(hotelRoom);
        else {
            updateRoomPrice(hotelId,roomType,price);
            updateRoomTotal(hotelId,roomType,-roomNum);
            updateRoomInfo(hotelId,roomType,-roomNum);
        }
    }

    @Override
    public ResponseVO updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        try{
            roomMapper.updateRoomInfo(hotelId,roomType,rooms);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ROOM_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void updateRoomPrice(Integer hotelId, String roomType, double price){
        roomMapper.updateRoomPrice(hotelId,roomType,price);
    }

    @Override
    public void updateRoomTotal(Integer hotelId, String roomType, Integer rooms){
        roomMapper.updateRoomTotal(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomMapper.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public void deleteRoomByHotel(Integer hotelId) throws ServiceException{
        roomMapper.deleteRoomByHotel(hotelId);
    }

    @Override
    public List<RoomVO> convertRoomPOListToRoomVOList(List<HotelRoom> rooms){
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            roomVO.setHotelId(r.getHotelId());
            return roomVO;
        }).collect(Collectors.toList());
        return roomVOS;
    }

    @Override
    public List<RoomVO> getAllRooms(){
        List<HotelRoom> rooms = roomMapper.getAllRooms();
        return convertRoomPOListToRoomVOList(rooms);
    }
}
