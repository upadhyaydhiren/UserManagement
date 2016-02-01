package com.papoye.UserMangement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.papoye.UserMangement.domain.User;
import com.papoye.UserMangement.repository.UserRepository;

/**
 * This is user service implementation class that is implement User Service
 * interface. That class is consume user repository method for processing.
 * 
 * @author Dhiren
 * @since 29-01-2016
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<User> getAllUser() throws Exception {
		return userRepository.getAllUser();
	}

	@Override
	public User getUserById(Long userId) throws Exception {
		return userRepository.getUserById(userId);
	}

	@Override
	public void deleteUser(Long userId) throws Exception {
		userRepository.deleteUser(userId);
	}

	@Override
	public User updateUser(User user) throws Exception {
		return userRepository.updateUser(user);
	}

	@Override
	public User getUserByUserName(String username) throws Exception {
		return userRepository.getUserByUserName(username);
	}

	@Override
	public void Save(User user) throws Exception {
		userRepository.Save(user);
	}

	@Override
	public User getAdminByUserName(String username) throws Exception {
		return userRepository.getAdminByUserName(username);
	}
}
