package com.papoye.UserMangement.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.AntPathRequestMatcher;

import com.papoye.UserMangement.service.LoginServiceImpl;

/**
 * This is java configuration for spring security.
 * 
 * @author Dhiren
 * @since 30-01-2016
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("loginService")
	LoginServiceImpl loginserviceImpl;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login", "/").permitAll().and()
				.formLogin().loginPage("/login")
				.loginProcessingUrl("/j_spring_security_check")
				.usernameParameter("email").passwordParameter("password")
				.failureUrl("/login?error").defaultSuccessUrl("/home", true)
				.permitAll().and().logout().logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/slogout"));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(loginserviceImpl).passwordEncoder(
				new BCryptPasswordEncoder());
	}

}
