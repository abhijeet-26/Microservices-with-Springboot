package com.userservice.services;

import java.util.List;

import com.userservice.entities.User;

public interface UserService {
	//1. Create User
	User createUser(User user);
	//2. Update User
	User updateUser(User user, String userId);
	//3. get userby Id
	User getUserById(String userId);
	//4. getAlluser
	List<User> getAllUser();
	//5. DeleteUser
	void deleteUser(String userId);
}
