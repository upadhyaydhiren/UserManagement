package com.papoye.UserMangement.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.papoye.UserMangement.domain.Role;
import com.papoye.UserMangement.domain.User;

/**
 * This is User repository implementation class that implement user repository
 * interface
 * 
 * @author Dhiren
 * @Since 28-01-2016
 */
@Repository
@Transactional
public class UserRepositryImpl implements UserRepository {

	@Autowired
	SessionFactory sessionFactory;

	/**
	 * This is save method for user.
	 * 
	 * @param User
	 *            this is object that is persist in database
	 * @return Its return type is void
	 * @exception That
	 *                method is through exception to service class.
	 */
	@Override
	public void Save(User user) throws Exception {
		sessionFactory.getCurrentSession().save(user);
	}

	/**
	 * This is getAll method.
	 * 
	 * @return Its return type is List of User Except Admin Role user.
	 * @exception That
	 *                method is through exception to service class.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUser() throws Exception {
		return sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.ne("role", Role.ADMIN)).list();
	}

	/**
	 * This is getUserById method
	 * 
	 * @param It
	 *            take User id.
	 * @return It's return type is User type object
	 * @exception That
	 *                method is through exception to service class.
	 */
	@Override
	public User getUserById(Long userId) throws Exception {
		return (User) sessionFactory.getCurrentSession()
				.get(User.class, userId);
	}

	/**
	 * This is delete method
	 * 
	 * @param It
	 *            takes long user id for deletion .
	 * @return It's return type is void
	 * @exception That
	 *                method is through exception to service class.
	 */
	@Override
	public void deleteUser(Long userId) throws Exception {
		User user = (User) sessionFactory.getCurrentSession().load(User.class,
				userId);
		sessionFactory.getCurrentSession().delete(user);
	}

	/**
	 * This is update method
	 * 
	 * @param It
	 *            takes method User object that is already persisted in
	 *            database.
	 * @return Its return type is updated User object
	 * @exception That
	 *                method is through exception to service class.
	 */
	@Override
	public User updateUser(User user) throws Exception {
		sessionFactory.getCurrentSession().update(user);
		return user;
	}

	/**
	 * This is method for get user by user name.
	 * 
	 * @param It
	 *            takes username
	 * @return Its return User object that is associated with given user name
	 */
	@Override
	public User getUserByUserName(String username) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				User.class);
		if (username.contains("@")) {
			criteria.add(Restrictions.eq("email", username));
		} else {
			criteria.add(Restrictions.eq("mobileNumber", username));
		}
		return (User) criteria.uniqueResult();
	}

}
