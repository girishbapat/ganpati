/**
 *
 */
package com.snehee.ganpati.serviceimpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snehee.ganpati.entity.AppUser;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.AppUserRepository;
import com.snehee.ganpati.service.AppUserService;

/**
 * @author Girish
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class AppUserServiceImpl implements AppUserService {
	@Autowired
	private AppUserRepository appUserRepository;

	/**
	 * Get all users list.
	 *
	 * @return the list
	 */
	@Override
	public List<AppUser> getAllAppUsers() {
		return this.appUserRepository.findAll();
	}

	@Override
	public AppUser getAppUserById(final Integer appUserId) throws ResourceNotFoundException {
		final AppUser user = this.appUserRepository.findById(appUserId)
				.orElseThrow(() -> new ResourceNotFoundException("App User not found on :: " + appUserId));
		return user;
	}

	@Override
	public AppUser createAppUser(final AppUser appUser) {
		return this.appUserRepository.save(appUser);
	}

	@Override
	public AppUser updateAppUser(final Integer userId, final AppUser appUserDetails) throws ResourceNotFoundException {
		final AppUser currentUserFromDb = this.appUserRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("App User not found on :: " + userId));
		BeanUtils.copyProperties(appUserDetails, currentUserFromDb);
		final AppUser updatedUser = this.appUserRepository.save(currentUserFromDb);
		return updatedUser;
	}

	@Override
	public Boolean deleteAppUser(final Integer appUserId) throws ResourceNotFoundException {
		final AppUser currentUserFromDb = this.appUserRepository.findById(appUserId)
				.orElseThrow(() -> new ResourceNotFoundException("App User not found on :: " + appUserId));
		this.appUserRepository.delete(currentUserFromDb);
		return true;
	}

}
