package com.example.hotel.bl.hotel;

import com.example.hotel.po.HotelCooperativeCompany;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.Service;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface HotelService {

    /**
     * 添加酒店
     * @param hotelVO
     * @throws
     */
    void addHotel(HotelVO hotelVO) throws ServiceException;


    /**
     * 预订酒店修改剩余客房信息
     * @param hotelId
     * @param roomType
     * @param rooms
     */
    ResponseVO updateRoomInfo(Integer hotelId, String roomType,Integer rooms);

    /**
     * 列表获取酒店信息
     * @return
     */
    List<HotelVO> retrieveHotels();

    /**
     * 获取某家酒店详细信息
     * @param hotelId
     * @return
     */
    HotelVO retrieveHotelDetails(Integer hotelId);

    /**
     * 查看酒店剩余某种房间数量
     * @param hotelId
     * @param roomType
     * @return
     */
    int getRoomCurNum(Integer hotelId,String roomType);

    ResponseVO deleteHotel(Integer hotelId);

    /**
     * 酒店管理人员取他所管理的酒店
     * @param managerId
     * @return
     */
    HotelVO retrieveHotelByManager(Integer managerId);

    /**
     * 前端获取酒店信息时，携带酒店的房间
     * @param hotelId
     * @param hotelVO
     */
    void setHotelRooms(int hotelId, HotelVO hotelVO);

    /**
     * 设置酒店的工作人员
     * @param hotelId
     * @param managerId
     * @return
     */
    ResponseVO setHotelManager(Integer hotelId,Integer managerId);

    /**
     * rate是新评论的评分，而不是计算好的评分
     * @param hotelId
     * @param rate
     * @throws Exception
     */
    void updateHotelRate(int hotelId, double rate) throws Exception;

    /**
     * 获取所有的枚举商圈
     * @return
     */
    List<String> getAllBizRegions();

    ResponseVO updateHotelInfo(HotelVO hotelVO);

    ResponseVO addCooperativeCompany(HotelCooperativeCompanyVO hotelCooperativeCompanyVO);

    ResponseVO deleteCooperativeCompany(HotelCooperativeCompanyVO hotelCooperativeCompanyVO);

    List<HotelCooperativeCompany> getCooperativeCompanies(int hotelId);

    /**
     * 单独返回所有的房间
     * @return
     */
    List<RoomVO> getAllRooms();

    /**
     * 删除酒店时调用
     * @param hotelId
     */
    void deleteCooperativeCompanyByHotel(int hotelId);

    /**
     * 获取所有的枚举服务
     * @return
     */
    List<String> getAllServices();

    List<ServiceVO> getHotelServices(int hotelId);

    ResponseVO addService(int hotelId, String type);

    ResponseVO deleteService(int hotelId, String type);

    /**
     * 删除酒店时调用
     * @param hotelId
     */
    void deleteServiceByHotel(int hotelId);
}
