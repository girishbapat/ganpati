/**
 *
 */
package com.snehee.ganpati.serviceimpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snehee.ganpati.entity.User;
import com.snehee.ganpati.exception.ResourceNotFoundException;
import com.snehee.ganpati.repository.UserRepository;
import com.snehee.ganpati.service.UserService;

/**
 * @author Girish
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	/**
	 * Get all users list.
	 *
	 * @return the list
	 */
	@Override
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	@Override
	public User getUserById(final Integer userId) throws ResourceNotFoundException {
		final User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
		return user;
	}

	@Override
	public User createUser(final User user) {
		return this.userRepository.save(user);
	}

	@Override
	public User updateUser(final Integer userId, final User userDetails) throws ResourceNotFoundException {
		final User currentUserFromDb = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
		BeanUtils.copyProperties(userDetails, currentUserFromDb);
		final User updatedUser = this.userRepository.save(currentUserFromDb);
		return updatedUser;
	}

	@Override
	public Boolean deleteUser(final Integer userId) throws ResourceNotFoundException {
		final User currentUserFromDb = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
		this.userRepository.delete(currentUserFromDb);
		return true;
	}

}
