package com.hotelservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelservice.entities.Hotel;
import com.hotelservice.service.HotelServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
	
	private final HotelServices hotelService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
		Hotel createdHotel = this.hotelService.createHotel(hotel);
		return new ResponseEntity<Hotel>(createdHotel, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('SCOPE_Internal')")
	@GetMapping("/{hotelId}")
	public ResponseEntity<Hotel> getById(@PathVariable String hotelId){
		Hotel hotel = this.hotelService.getById(hotelId);
		return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('SCOPE_Internal') || hasAuthority('Admin') ")
	public ResponseEntity<List<Hotel>> getAll(){
		List<Hotel> allHotels = this.hotelService.getAll();
		return new ResponseEntity<List<Hotel>>(allHotels, HttpStatus.OK);
	}
}

