package com.ratingservice.controller;

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

import com.ratingservice.entities.Rating;
import com.ratingservice.services.RatingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ratings")
public class RatingController {

	private final RatingService ratingService;
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating){
		Rating createRating = this.ratingService.createRating(rating);
		return new ResponseEntity<Rating>(createRating, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Rating>> getAllRating(){
		List<Rating> allRatings = this.ratingService.getAll();
		return new ResponseEntity<List<Rating>>(allRatings, HttpStatus.OK);
	}
	
	@GetMapping("/users/{userId}")
	@PreAuthorize("hasAuthority('SCOPE_Internal') || hasAuthority('Admin') ")
	public ResponseEntity<List<Rating>> getByUserId(@PathVariable String userId){
		List<Rating> ratingByUserId = this.ratingService.getRatingByUserId(userId);
		return new ResponseEntity<List<Rating>>(ratingByUserId, HttpStatus.OK);
	}
	
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>> getByHotelId(@PathVariable String hotelId){
		List<Rating> ratingByHotelId = this.ratingService.getRatingByHotelId(hotelId);
		return new ResponseEntity<List<Rating>>(ratingByHotelId, HttpStatus.OK);
	}
	
}
