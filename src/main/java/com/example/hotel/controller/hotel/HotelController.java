package com.example.hotel.controller.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelCooperativeCompanyVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 酒店 房间 合作企业 酒店服务 的操作
 */

@RestController
@RequestMapping("/api/hotel")
public class HotelController {
    private final static String HOTEL_BY_MANAGER_ERROR = "本人无管理的酒店";

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;


    @PostMapping("/addHotel")
    public ResponseVO createHotel(@RequestBody HotelVO hotelVO) throws ServiceException {

        hotelService.addHotel(hotelVO);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/all")
    public ResponseVO retrieveAllHotels(){
        return ResponseVO.buildSuccess(hotelService.retrieveHotels());
    }

    @PostMapping("/roomInfo")
    public ResponseVO addRoomInfo(@RequestBody HotelRoom hotelRoom) {
        roomService.insertRoomInfo(hotelRoom);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/{hotelId}/detail")
    public ResponseVO retrieveHotelDetail(@PathVariable Integer hotelId) {
        return ResponseVO.buildSuccess(hotelService.retrieveHotelDetails(hotelId));
    }

    @PostMapping("/{hotelId}/deleteHotel")
    public ResponseVO deleteHotel(@PathVariable Integer hotelId){
        return hotelService.deleteHotel(hotelId);
    }

    @GetMapping("/{managerId}/retrieveHotelByManager")
    public ResponseVO retrieveHotelByManager(@PathVariable Integer managerId){
        HotelVO hotelVO = hotelService.retrieveHotelByManager(managerId);
        if(hotelVO==null)
            return ResponseVO.buildFailure(HOTEL_BY_MANAGER_ERROR);
        return ResponseVO.buildSuccess(hotelVO);
    }

    @PostMapping("/{hotelId}/setHotelManager")
    public ResponseVO setHotelManager(@PathVariable Integer hotelId,@RequestParam Integer managerId){
        return hotelService.setHotelManager(hotelId,managerId);
    }

    @GetMapping("/getAllBizRegions")
    public ResponseVO getAllBizRegions(){
        return ResponseVO.buildSuccess(hotelService.getAllBizRegions());
    }

    @PostMapping("/updateHotelInfo")
    public ResponseVO updateHotelInfo(@RequestBody HotelVO hotelVO){
        return hotelService.updateHotelInfo(hotelVO);
    }

    @PostMapping("/addCooperativeCompany")
    public ResponseVO addCooperativeCompany(@RequestBody HotelCooperativeCompanyVO hotelCooperativeCompanyVO){
        return hotelService.addCooperativeCompany(hotelCooperativeCompanyVO);
    }

    @PostMapping("/deleteCooperativeCompany")
    public ResponseVO deleteCooperativeCompany(@RequestBody HotelCooperativeCompanyVO hotelCooperativeCompanyVO){
        return hotelService.deleteCooperativeCompany(hotelCooperativeCompanyVO);
    }

    @GetMapping("/getAllRooms")
    public ResponseVO getAllRooms(){
        return ResponseVO.buildSuccess(hotelService.getAllRooms());
    }

    @PostMapping("/updateRoomCurNum")
    public ResponseVO updateRoomInfo(@RequestParam int hotelId, @RequestParam String roomType, @RequestParam int num){
        return hotelService.updateRoomInfo(hotelId,roomType,-num);
    }

    @GetMapping("/getAllServices")
    public ResponseVO getAllServices(){
        return ResponseVO.buildSuccess(hotelService.getAllServices());
    }

    @GetMapping("/{hotelId}/getHotelServices")
    public ResponseVO getHotelServices(@PathVariable int hotelId){
        return ResponseVO.buildSuccess(hotelService.getHotelServices(hotelId));
    }

    @PostMapping("/{hotelId}/addService")
    public ResponseVO addService(@PathVariable int hotelId, @RequestParam String type){
        return hotelService.addService(hotelId,type);
    }

    @PostMapping("/{hotelId}/deleteService")
    public ResponseVO deleteService(@PathVariable int hotelId, @RequestParam String type){
        return hotelService.deleteService(hotelId,type);
    }
}
