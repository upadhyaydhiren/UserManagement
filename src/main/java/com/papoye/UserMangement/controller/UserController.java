package com.papoye.UserMangement.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.papoye.UserMangement.domain.Role;
import com.papoye.UserMangement.domain.User;
import com.papoye.UserMangement.service.UserService;

/**
 * This is User Controller that handles all HTTP request mapping of web
 * 
 * @author Dhiren
 * @since 30-01-2016
 */
@Controller
public class UserController {

	private final UserService userService;
	private static final String DEFAULT_ADMIN = "admin@papoye.com";
	private static final String DEFAULT_PASSWORD = "admin123";

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * This is landing page method. That method register default admin if admin
	 * is not exist.
	 * 
	 * @return Its return redirection of login method.
	 */
	@RequestMapping("/")
	public String getLandingPage() {
		try {
			User user = userService.getUserByUserName(DEFAULT_ADMIN);
			if (user == null) {
				user = new User();
				user.setEmail(DEFAULT_ADMIN);
				user.setPassword(new BCryptPasswordEncoder()
						.encode(DEFAULT_PASSWORD));
				user.setRole(Role.ADMIN);
				user.setFirstName("Admin");
				userService.Save(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/login";
	}

	/**
	 * This is login method that is associated with spring security login
	 * processing method
	 * 
	 * @param user
	 *            It takes User as modal attribute for spring login form binding
	 * @param error
	 *            It is optional method that thrown by spring security login
	 *            processing
	 * @param logout
	 *            It is also optional method that thrown by spring security
	 *            login processing
	 * @param redirectAttributes
	 *            It is use for bind a message for redirection
	 * @return It is return view name when error and logout is null otherwise
	 *         return respective redirected view
	 */
	@RequestMapping("login")
	public String getLogin(@ModelAttribute("user") User user,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			RedirectAttributes redirectAttributes) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:home";
		}
		if (error != null) {
			redirectAttributes.addFlashAttribute("error",
					"Incorrect Email/Mobile or password");
			return "redirect:error";

		} else if (logout != null) {
			redirectAttributes.addFlashAttribute("success",
					"Logged out successfully");
			return "redirect:logout";

		}
		return "home";
	}

	/**
	 * This Home method that is appear after login.
	 * 
	 * @param user
	 *            It takes User as modal attribute for spring form binding in
	 *            home page
	 * @param modelMap
	 *            It takes Model map for add more than one data in modal
	 *            attribute and display in home page
	 * @param redirectAttributes
	 *            It is use for bind a message for redirection
	 * @param request
	 *            It takes for Http Servlet request operation.
	 * @return It is return view name otherwise return respective redirected
	 *         view
	 */
	@RequestMapping("home")
	public String getHome(@ModelAttribute User user, ModelMap modelMap,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				User loggedInUser = userService.getUserByUserName(userDetail
						.getUsername());
				modelMap.addAttribute("loggedInUser", loggedInUser);
				request.getSession().setAttribute("user", loggedInUser);
				modelMap.addAttribute("allUsers", userService.getAllUser());
				return "home";
			} else {
				redirectAttributes.addFlashAttribute("info",
						"Please Login again");
				return "redirect:logout";
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error",
					"Some thing goes wrong");
			return "redirect:error";
		}
	}

	/**
	 * This is redirection method that is handle two request mapping logout and
	 * error
	 * 
	 * @param user
	 * @return It takes User as modal attribute for spring login form binding
	 */
	@RequestMapping(value = { "logout", "error" })
	public ModelAndView redirect(@ModelAttribute User user) {
		return new ModelAndView("home");
	}

	/**
	 * This method handle add user request
	 * 
	 * @param user
	 *            It take User modal attribute from spring form
	 * @param redirectAttributes
	 *            It is use for bind a message for redirection
	 * @return It is return redirected view of home
	 */
	@RequestMapping(value = "adduser", method = RequestMethod.POST)
	public String InsertUser(@ModelAttribute User user,
			RedirectAttributes redirectAttributes) {
		try {
			user.setRole(Role.NORMAL_USER);
			user.setPassword(new BCryptPasswordEncoder().encode(user
					.getPassword()));
			userService.Save(user);
			redirectAttributes.addFlashAttribute("success",
					"User is successfully added");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error",
					"User is not added succesfully");
		}
		return "redirect:home";
	}

	/**
	 * This method handle edit user request
	 * 
	 * @param user
	 *            It take User modal attribute from spring form
	 * @param redirectAttributes
	 *            It is use for bind a message for redirection
	 * @return It is return redirected view of home
	 */
	@RequestMapping(value = "edituser", method = RequestMethod.POST)
	public String editUser(@ModelAttribute User user,
			RedirectAttributes redirectAttributes) {
		try {
			user.setRole(Role.NORMAL_USER);
			userService.updateUser(user);
			redirectAttributes.addFlashAttribute("success",
					"User detail is successfully updated");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error",
					"User detail is not updated");
		}
		return "redirect:home";
	}

	/**
	 * This request call by spring form by ajax request
	 * 
	 * @param username
	 *            It takes username for user existing checking
	 * @return Its return is boolean through json in view
	 */
	@RequestMapping(value = "checkuser")
	public @ResponseBody boolean isExistUser(
			@RequestParam("username") String username) {
		try {
			User user = userService.getUserByUserName(username);
			if (user != null)
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * This method is user for load data in edit modal dialog
	 * 
	 * @param userId
	 *            It takes User id for user loading from database
	 * @return It return is User object in page using json.
	 */
	@RequestMapping(value = "existUser")
	public @ResponseBody User getUser(@RequestParam("id") Long userId) {
		try {
			return userService.getUserById(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method is user for delete data in edit modal dialog
	 * 
	 * @param userId
	 *            It takes User id for user delete from database
	 * @return It return is boolean in page using json.
	 */
	@RequestMapping(value = "deleteUser")
	public @ResponseBody boolean deleteUserById(@RequestParam("id") Long userId) {
		try {
			userService.deleteUser(userId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This is delete redirected method.
	 * 
	 * @param status
	 *            It takes boolean delete status from ajax
	 * @param redirectAttributes
	 *            It is use for bind a message for redirection
	 * @return It is return redirected view of home
	 */
	@RequestMapping(value = "deleteredirect")
	public String deleteRedirect(@RequestParam("status") Boolean status,
			RedirectAttributes redirectAttributes) {
		if (status)
			redirectAttributes.addFlashAttribute("success",
					"User is successfully deleted");
		else
			redirectAttributes.addFlashAttribute("error",
					"User is not successfully deleted");
		return "redirect:home";
	}
}