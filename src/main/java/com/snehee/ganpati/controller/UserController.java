package com.snehee.ganpati.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.snehee.ganpati.entity.User;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.service.UserService;

/**
 * The type User controller.
 *
 * @author Girish Bapat
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Get all users list.
	 *
	 * @return the list
	 */
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return this.userService.getAllUsers();
	}

	/**
	 * Gets user by id.
	 *
	 * @param userId the user id
	 * @return the users by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") final Integer userId)
			throws ResourceNotFoundException {
		final User user = this.userService.getUserById(userId);
		return ResponseEntity.ok().body(user);
	}

	/**
	 * Create user user.
	 *
	 * @param user the user
	 * @return the user
	 */
	@PostMapping("/users")
	public User createUser(@Valid @RequestBody final User user) {
		return this.userService.createUser(user);
	}

	/**
	 * Update user response entity.
	 *
	 * @param userId      the user id
	 * @param userDetails the user details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") final Integer userId,
			@Valid @RequestBody final User userDetails) throws ResourceNotFoundException {
		final User updatedUser = this.userService.updateUser(userId, userDetails);
		return ResponseEntity.ok(updatedUser);
	}

	/**
	 * Delete user map.
	 *
	 * @param userId the user id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") final Integer userId) throws Exception {
		final Boolean ifcurrentUserGotDeleted = this.userService.deleteUser(userId);
		final Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", ifcurrentUserGotDeleted);
		return response;
	}
}
