package com.papoye.UserMangement.service;

import java.util.List;

import com.papoye.UserMangement.domain.User;

/**
 * This is inteface for User Service that is consume user repository library.
 * 
 * @author Dhiren
 * @since 29-01-2016
 *
 */
public interface UserService {
	public void Save(User user) throws Exception;

	public List<User> getAllUser() throws Exception;

	public User getUserById(Long userId) throws Exception;

	public void deleteUser(Long userId) throws Exception;

	public User updateUser(User user) throws Exception;

	public User getUserByUserName(String username) throws Exception;
}
