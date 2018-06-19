 package controllers;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import database.UserDAO;
import models.BeanUser;
import utils.BD;
import utils.ErrorMessages;
import utils.ServletUtilities;
import utils.GeneralUtils;
import utils.ValidationUtils;
import utils.JSONUtils;
import utils.Servlet;

@WebServlet("/checkLoginErrors")
public class LoginController extends Servlet {
	
	private UserDAO userDAO;
	
	public LoginController() {
		this.userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		
		BeanUser vistaUser = null;
		ErrorMessages errors = new ErrorMessages();
		
		HttpSession session = this.getSession(request);
		
		if (session.getAttribute("Session_ID") == null) {
		
			String jsonData = request.getParameter("data");
			
			if (ValidationUtils.isEmpty(jsonData) == false) {
				vistaUser = JSONUtils.returnJSONObject(jsonData, BeanUser.class);
			} else {
				errors.addError("errorData", "Not data recived!");
			}
	
			if (vistaUser != null) {
				errors.addError(loginUser(vistaUser, request));
			}
			
		}

		sendResponse(request, response, errors);
		
	}
	
	private ErrorMessages loginUser(BeanUser user, HttpServletRequest request) {

		ErrorMessages errors = this.validateUserInformation(user);
		
		if (errors.haveErrors() == false) {
			
			boolean existUser = this.userDAO.existUser(user.getUser(), user.getMail());

			if (existUser == true) {
				
				boolean loginUser = false;
				
				if (ValidationUtils.isEmpty(user.getUser()) == false) {
					loginUser = this.userDAO.loginUser(this.userDAO.COLUMN_USER, user.getUser(), user.getPassword());
				} else {
					loginUser = this.userDAO.loginUser(UserDAO.COLUMN_MAIL, user.getMail(), user.getPassword());
				}
				
				if (loginUser == false) {
					errors.addError("userLogin", "User/E-mail Address and/or password are incorrect!!");
				} else {
					fillSessionUserInformation(user, request);
				}
				
			} else {
				errors.addError("userNotExist", "The user not exist!!!");
			}
			
		}
		
		return errors;
		
	}
	
	private ErrorMessages validateUserInformation(BeanUser user) {
		ErrorMessages error = new ErrorMessages();
		
		boolean userKO = ValidationUtils.isNull(user.getUser());
		boolean mailKO = ValidationUtils.isNull(user.getMail());
		boolean passwordKO = ValidationUtils.isEmpty(user.getPassword());
		passwordKO |= !ValidationUtils.isBetweenLength(user.getPassword(), 8, 20);
		
		if (userKO == false || mailKO == false) {
			userKO = ValidationUtils.isEmpty(user.getUser());
			mailKO = ValidationUtils.isEmpty(user.getMail());
			userKO |= !ValidationUtils.isBetweenLength(user.getUser(), 4, 30);
			mailKO |= !ValidationUtils.isPatternMatches(ValidationUtils.REGEX_EMAIL, user.getMail());
			
			if (userKO == true && mailKO == true) {
				error.addError("user", "Wrong Username!");
			}
		}
		
		if (passwordKO == true) {
			String errorMessage = "Check that your password is between 8 ";
			errorMessage += "and 20 characters-length and is correct.";
			error.addError("password", errorMessage);
		}
	
		return error;
	}
	
	private void fillSessionUserInformation(BeanUser user, HttpServletRequest request) {
		BeanUser userBD = null;
		
		if (ValidationUtils.isEmpty(user.getUser()) == true) {
			userBD = this.userDAO.returnUser(this.userDAO.COLUMN_MAIL, user.getMail());
		} else {
			userBD = this.userDAO.returnUser(UserDAO.COLUMN_USER, user.getUser());
		}
		
		if (userBD != null) {
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", JSONUtils.getJSON(userBD));
			session.setAttribute("Session_ID", userBD.getUser());
		}
	}

	private void sendResponse(HttpServletRequest request, HttpServletResponse response, ErrorMessages errors)
																		throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("errors", errors.getJSON());
		response.getWriter().print(errors.getJSON());
	}

}
