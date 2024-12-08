package com.snehee.ganpati.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.snehee.ganpati.entity.AppUser;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.service.AppUserService;

/**
 * The type App User controller.
 *
 * @author Girish Bapat
 */
@RestController
public class AppUserController {

	@Autowired
	private AppUserService appUserService;

	/**
	 * Get all App users list.
	 *
	 * @return the list
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/appUsers")
	public List<AppUser> getAllAppUsers() {
		return this.appUserService.getAllAppUsers();
	}

	/**
	 * Gets user by id.
	 *
	 * @param appUserId the Appuser appUserId
	 * @return the users by appUserId
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/appUsers/{appUserId}")
	public ResponseEntity<AppUser> getUserById(@PathVariable(value = "appUserId") final Integer appUserId)
			throws ResourceNotFoundException {
		final AppUser user = this.appUserService.getAppUserById(appUserId);
		return ResponseEntity.ok().body(user);
	}

	/**
	 * Create appUser appUser.
	 *
	 * @param appUser the appUser
	 * @return the appUser
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/appUsers")
	public AppUser createUser(@Valid @RequestBody final AppUser appUser) {
		return this.appUserService.createAppUser(appUser);
	}

	/**
	 * Update user response entity.
	 *
	 * @param appUserId      the user id
	 * @param appUserDetails the user details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, value = "/appUsers/{appUserId}")
	public ResponseEntity<AppUser> updateUser(@PathVariable(value = "appUserId") final Integer appUserId,
											  @Valid @RequestBody final AppUser appUserDetails) throws ResourceNotFoundException {
		final AppUser updatedUser = this.appUserService.updateAppUser(appUserId, appUserDetails);
		return ResponseEntity.ok(updatedUser);
	}

	/**
	 * Delete user map.
	 *
	 * @param appUserId the user id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, value = "/appUsers/{appUserId}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "appUserId") final Integer appUserId) throws Exception {
		final Boolean ifcurrentUserGotDeleted = this.appUserService.deleteAppUser(appUserId);
		final Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", ifcurrentUserGotDeleted);
		return response;
	}
}
