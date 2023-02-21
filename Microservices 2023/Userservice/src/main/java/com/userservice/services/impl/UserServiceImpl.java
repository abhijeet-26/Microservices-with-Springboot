package com.userservice.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.userservice.entities.Hotel;
import com.userservice.entities.Rating;
import com.userservice.entities.User;
import com.userservice.exception.ResourceNotFoundException;
import com.userservice.repository.UserRepository;
import com.userservice.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final RestTemplate restTemplate;

	@Override
	public User createUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return this.userRepository.save(user);
	}

	@Override
	public User updateUser(User user, String userId) {
		User oldUser = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(
				"User not found with this userId: "+userId));
		oldUser.setName(user.getName());
		oldUser.setEmail(user.getEmail());
		oldUser.setAbout(user.getAbout());
		return this.userRepository.save(oldUser);
	}

	@Override
	public User getUserById(String userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(
				"User not found with this userId: "+userId));
		
		// fetching rating of the above user by call RATING_SERVICE
		//http://localhost:8083/ratings/users/a23e906a-d826-4e60-af68-fee49ee1414b
		
		Rating[] allRatings = restTemplate
					.getForObject("http://RATING-SERVICE/ratings/users/"+userId, Rating[].class);
		
		List<Rating> ratings = Arrays.stream(allRatings).toList();
		
		List<Rating> ratingList = ratings.stream().map(rating->{
			//api call 
			//http://localhost:8082/hotels/1e87b11c-9be7-4556-9a05-f81053ffdb7c
			Hotel hotel = restTemplate
					.getForObject("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
			
			//set hotel
			rating.setHotel(hotel);
			//return
			return rating;
		}).collect(Collectors.toList());
		
		user.setRatings(ratingList);
		
		return user;
	}

	@Override
	public List<User> getAllUser() {
		return this.userRepository.findAll();
	}

	@Override
	public void deleteUser(String userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(
				"User not found with this userId: "+userId));
		this.userRepository.delete(user);
	}

}
