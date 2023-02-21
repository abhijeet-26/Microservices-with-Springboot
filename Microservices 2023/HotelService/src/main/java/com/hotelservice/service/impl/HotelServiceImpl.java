package com.hotelservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hotelservice.entities.Hotel;
import com.hotelservice.exception.ResourceNotFoundException;
import com.hotelservice.repository.HotelRepository;
import com.hotelservice.service.HotelServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelServices{
	
	private final HotelRepository hotelRepository;

	@Override
	public Hotel createHotel(Hotel hotel) {
		String randomId = UUID.randomUUID().toString();
		hotel.setHotelId(randomId);
		return this.hotelRepository.save(hotel);
	}

	@Override
	public Hotel getById(String hotelId) {
		return this.hotelRepository.findById(hotelId)
				.orElseThrow(()-> new ResourceNotFoundException("Hotel not found with hotelId: "+hotelId));
	}

	@Override
	public List<Hotel> getAll() {
		return this.hotelRepository.findAll();
	}

}
