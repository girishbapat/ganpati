package com.snehee.ganpati.service;

import java.util.List;

import com.snehee.ganpati.entity.User;
import com.snehee.ganpati.exception.ResourceNotFoundException;

public interface UserService {

	List<User> getAllUsers();

	User getUserById(final Integer userId) throws ResourceNotFoundException;

	User createUser(User user);

	User updateUser(final Integer userId, final User userDetails) throws ResourceNotFoundException;

	Boolean deleteUser(final Integer userId) throws ResourceNotFoundException;
}
