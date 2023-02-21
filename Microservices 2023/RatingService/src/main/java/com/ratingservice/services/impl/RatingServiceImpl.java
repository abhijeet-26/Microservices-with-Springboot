package com.ratingservice.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ratingservice.entities.Rating;
import com.ratingservice.repository.RatingRepository;
import com.ratingservice.services.RatingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService{

	private final RatingRepository ratingRepo;
	
	@Override
	public Rating createRating(Rating rating) {
		String randomId = UUID.randomUUID().toString();
		rating.setRatingId(randomId);
		return this.ratingRepo.save(rating);
	}

	@Override
	public List<Rating> getAll() {
		return this.ratingRepo.findAll();
	}

	@Override
	public List<Rating> getRatingByUserId(String userId) {
		return this.ratingRepo.findByUserId(userId);
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		return this.ratingRepo.findByHotelId(hotelId);
	}

}
