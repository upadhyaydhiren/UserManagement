package com.papoye.UserMangement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.papoye.UserMangement.domain.User;

@Service
@Component("loginService")
public class LoginServiceImpl implements UserDetailsService {

	private final UserService userService;

	@Autowired
	public LoginServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserDetails userDetails = null;
		try {
			User user = userService.getUserByUserName(username);
			String fetechedUsername;
			if (user == null) {
				return null;
			}
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities
					.add(new SimpleGrantedAuthority(user.getRole().getName()));
			if (user.getEmail() != null)
				fetechedUsername = user.getEmail();
			else
				fetechedUsername = user.getMobileNumber();
			userDetails = new org.springframework.security.core.userdetails.User(
					fetechedUsername, user.getPassword(), true, true, true,
					true, authorities);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return userDetails;
	}
}
