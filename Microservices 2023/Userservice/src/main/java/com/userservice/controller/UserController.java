package com.userservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.entities.User;
import com.userservice.payloads.ApiResponse;
import com.userservice.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	// create
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User createUser = this.userService.createUser(user);
		return new ResponseEntity<User>(createUser, HttpStatus.CREATED);
	}
	
	// get by id
	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//	@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name ="userRateLimiter", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getById(@PathVariable String userId){
		User userById = this.userService.getUserById(userId);
		return new ResponseEntity<User>(userById, HttpStatus.OK);
	}
	
	// rating hotel fallback method
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex)
	{
		ex.printStackTrace();
		User user = new User("xxxx", "dummy", "dummy@gmail.com", "Dummy data due to sever down", null);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	
	// get all user
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = this.userService.getAllUser();
		return new ResponseEntity<List<User>>(allUser, HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String userId){
		User updateUser = this.userService.updateUser(user, userId);
		return new ResponseEntity<User>(updateUser, HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
		this.userService.deleteUser(userId);
		ApiResponse response = new ApiResponse("User deleted Successfully", true, HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
}
