package com.hotelservice.service;

import java.util.List;

import com.hotelservice.entities.Hotel;

public interface HotelServices {
	Hotel createHotel(Hotel hotel);
	Hotel getById(String hotelId);
	List<Hotel> getAll();
}
