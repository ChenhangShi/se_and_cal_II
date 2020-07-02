package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.bl.hotel.CompanyService;
import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.po.*;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private final static String DELETE_ERROR = "删除酒店失败";
    private final static String SET_MANAGER_ERROR = "设置工作人员失败";
    private final static String UPDATE_HOTEL_INFO_ERROR = "删除酒店失败";
    private final static String ADD_SERVICE_ERROR = "添加酒店服务设施失败";
    private final static String DELETE_SERVICE_ERROR = "删除酒店服务设施失败";

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private CouponService couponService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private CompanyService companyService;

    @Override
    public void addHotel(HotelVO hotelVO) throws ServiceException {
        /*
        User manager = accountService.getUserInfo(hotelVO.getManagerId());
        if(manager == null || !manager.getUserType().equals(UserType.HotelManager)){
            throw new ServiceException("管理员不存在或者无权限！创建酒店失败！");
        }
        */


        Hotel hotel = new Hotel();
        hotel.setDescription(hotelVO.getDescription());
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
//        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setRate(0.0);
        hotel.setCommentNum(0);
        hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public ResponseVO updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        return roomService.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        return roomService.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public List<HotelVO> retrieveHotels() {

        return hotelMapper.selectAllHotel();
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        setHotelRooms(hotelId,hotelVO);
//        hotelVO.setCooperativeCompanies(getCooperativeCompanies(hotelId));
        hotelVO.setServices(getHotelServices(hotelId));
        return hotelVO;
    }

    @Override
    public ResponseVO deleteHotel(Integer hotelId){
        try{
            roomService.deleteRoomByHotel(hotelId);
            orderService.deleteOrderByHotel(hotelId);
            couponService.deleteCouponByHotel(hotelId);
            deleteCooperativeCompanyByHotel(hotelId);
            deleteServiceByHotel(hotelId);
            hotelMapper.deleteHotel(hotelId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public HotelVO retrieveHotelByManager(Integer managerId){
        HotelVO hotelVO = hotelMapper.retrieveHotelByManager(managerId);
        if(hotelVO!=null){
            setHotelRooms(hotelVO.getId(),hotelVO);
            hotelVO.setCooperativeCompanies(getCooperativeCompanies(hotelVO.getId()));
            hotelVO.setServices(getHotelServices(hotelVO.getId()));
        }
        return hotelVO;
    }

    @Override
    public void setHotelRooms(int hotelId, HotelVO hotelVO){
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelId);
        List<RoomVO> roomVOS = roomService.convertRoomPOListToRoomVOList(rooms);
        hotelVO.setRooms(roomVOS);
    }

    @Override
    public ResponseVO setHotelManager(Integer hotelId,Integer managerId){
        try{
            hotelMapper.setHotelManager(hotelId,managerId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(SET_MANAGER_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void updateHotelRate(int hotelId, double rate) throws Exception{
        hotelMapper.updateHotelRate(hotelId,rate);
    }

    @Override
    public List<String> getAllBizRegions(){
        List<String> bizRegions=new ArrayList<>();
        for(BizRegion bizRegion:BizRegion.values())
            bizRegions.add(bizRegion.toString());
        return bizRegions;
    }

    @Override
    public ResponseVO updateHotelInfo(HotelVO hotelVO){
        try{
            hotelMapper.updateHotelInfo(hotelVO.getId(), hotelVO.getAddress(), BizRegion.valueOf(hotelVO.getBizRegion()), hotelVO.getDescription(), HotelStar.valueOf(hotelVO.getHotelStar()));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_HOTEL_INFO_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO addCooperativeCompany(HotelCooperativeCompanyVO hotelCooperativeCompanyVO){
        return companyService.addCooperativeCompany(hotelCooperativeCompanyVO);
    }

    @Override
    public ResponseVO deleteCooperativeCompany(HotelCooperativeCompanyVO hotelCooperativeCompanyVO){
        return companyService.deleteCooperativeCompany(hotelCooperativeCompanyVO);
    }

    @Override
    public List<HotelCooperativeCompany> getCooperativeCompanies(int hotelId){
        return companyService.getCooperativeCompanies(hotelId);
    }

    @Override
    public List<RoomVO> getAllRooms(){
        return roomService.getAllRooms();
    }

    @Override
    public void deleteCooperativeCompanyByHotel(int hotelId){
        companyService.deleteCooperativeCompanyByHotel(hotelId);
    }

    @Override
    public List<String> getAllServices(){
        List<String> services=new ArrayList<>();
        for(com.example.hotel.enums.HotelService hotelService: com.example.hotel.enums.HotelService.values())
            services.add(hotelService.toString());
        return services;
    }

    @Override
    public List<ServiceVO> getHotelServices(int hotelId){
        List<com.example.hotel.po.Service> services = hotelMapper.getHotelServices(hotelId);
        List<ServiceVO> serviceVOS = services.stream().map(s -> {
            ServiceVO serviceVO = new ServiceVO();
            serviceVO.setHotelId(s.getHotelId());
            serviceVO.setServiceType(s.getHotelService().toString());
            return serviceVO;
        }).collect(Collectors.toList());
        return serviceVOS;
    }

    @Override
    public ResponseVO addService(int hotelId, String type){
        com.example.hotel.po.Service service=new com.example.hotel.po.Service();
        service.setHotelId(hotelId);
        com.example.hotel.enums.HotelService serviceToSet=null;
        for(com.example.hotel.enums.HotelService hotelService: com.example.hotel.enums.HotelService.values()){
            if(hotelService.toString().equals(type)){
                serviceToSet=hotelService;
                break;
            }
        }
        service.setHotelService(serviceToSet);

        try{
            hotelMapper.addService(service);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ADD_SERVICE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO deleteService(int hotelId, String type){
        String serviceName=null;
        for(com.example.hotel.enums.HotelService hotelService: com.example.hotel.enums.HotelService.values()){
            if(hotelService.toString().equals(type)){
                serviceName=hotelService.name();
                break;
            }
        }
        try{
            hotelMapper.deleteService(hotelId,serviceName);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETE_SERVICE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void deleteServiceByHotel(int hotelId){
        try{
            hotelMapper.deleteServiceByHotel(hotelId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(DELETE_SERVICE_ERROR);
        }
    }
}
