package com.example.hotel.po;

import com.example.hotel.enums.HotelService;

public class Service {
    int hotelId;
    HotelService hotelService;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public HotelService getHotelService() {
        return hotelService;
    }

    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }
}
