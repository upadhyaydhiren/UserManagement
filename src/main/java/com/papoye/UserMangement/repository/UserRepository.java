package com.papoye.UserMangement.repository;

import java.util.List;

import com.papoye.UserMangement.domain.User;

/**
 * This is interface for user repository. In this interface declare all data
 * related operation method.
 * 
 * @author Dhiren
 * @Since 28-01-2016
 */
public interface UserRepository {
	public void Save(User user) throws Exception;

	public List<User> getAllUser() throws Exception;

	public User getUserById(Long userId) throws Exception;

	public void deleteUser(Long userId) throws Exception;

	public User updateUser(User user) throws Exception;

	public User getUserByUserName(String username) throws Exception;

	public User getAdminByUserName(String username) throws Exception;
}
