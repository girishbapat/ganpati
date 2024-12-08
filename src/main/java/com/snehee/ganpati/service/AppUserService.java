package com.snehee.ganpati.service;

import java.util.List;

import com.snehee.ganpati.entity.AppUser;
import com.snehee.ganpati.exception.ResourceNotFoundException;

public interface AppUserService {

	List<AppUser> getAllAppUsers();

	AppUser getAppUserById(final Integer appUserId) throws ResourceNotFoundException;

	AppUser createAppUser(AppUser appUser);

	AppUser updateAppUser(final Integer userId, final AppUser appUserDetails) throws ResourceNotFoundException;

	Boolean deleteAppUser(final Integer appUserId) throws ResourceNotFoundException;
}
