package controllers;

import java.io.IOException;

import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.UserDAO;
import models.BeanUser;
import utils.ErrorMessages;
import utils.GeneralUtils;
import utils.ValidationUtils;
import utils.JSONUtils;
import utils.Servlet;

@WebServlet("/checkErrors")
public class RegisterController extends Servlet {
	
	private UserDAO userDAO;
	
	public RegisterController() {
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
		String jsonData = request.getParameter("data");
		
		if (ValidationUtils.isEmpty(jsonData) == false) {
			vistaUser = JSONUtils.returnJSONObject(jsonData, BeanUser.class, "yyyy-MM-dd");
		}

		if (vistaUser != null) {
			
			errors.addError(registerUser(vistaUser, request));
			
		}

		sendResponse(request, response, errors);
		
	}
	
	private ErrorMessages validateUserInformation(BeanUser user) {
		String youtubeChannel = "https://www.youtube.com/channel/";
		String twitchChannel = "https://www.twitch.tv/";
		ErrorMessages error = new ErrorMessages();
		
		if (ValidationUtils.isEmpty(user.getName()) == true) {
			error.addError("name", "The field is empty.");
		} else if (ValidationUtils.isBetweenLength(user.getName(), 3, 30) == false) {
			error.addError("name", "Invalid length (min: 3, max: 30).");
		}
		
		if (ValidationUtils.isEmpty(user.getSurname()) == true) {
			error.addError("surname", "The field is empty.");
		} else if (ValidationUtils.isBetweenLength(user.getSurname(), 3, 30) == false) {
			error.addError("surname", "Invalid length (min: 3, max: 30).");
		}
		
		if (ValidationUtils.isEmpty(user.getMail()) == true) {
			error.addError("mail", "The field is empty.");
		} else if (ValidationUtils.isPatternMatches(ValidationUtils.REGEX_EMAIL, user.getMail()) == false) {
			error.addError("mail", "The email need to have the format xx@xx.xx");
		}
		
		if (ValidationUtils.isEmpty(user.getBirthDate()) == true) {
			error.addError("birthDate", "The field is empty.");
		} else {
			
			String year = GeneralUtils.split(user.getBirthDate(), "/")[2];
			int age = Calendar.getInstance().get(Calendar.YEAR) - Integer.valueOf(year) - 1;
			if (ValidationUtils.isBetweenLength(age, 18, 90) == false) {
				error.addError("birthDate", "The birthDate is not correct, your age is not between 18 and 90");
			}
			
		}
		
		if (ValidationUtils.isEmpty(user.getUser()) == true) {
			error.addError("user", "The field is empty.");
		} else if (ValidationUtils.isBetweenLength(user.getUser(), 4, 30) == false) {
			error.addError("user", "Invalid length (min: 4, max: 30)."); 
		}
		
		if (ValidationUtils.isEmpty(user.getPassword()) == true) {
			error.addError("password", "The field is empty.");
		} else if (ValidationUtils.isBetweenLength(user.getPassword(), 8, 20) == false) {
			error.addError("password", "Check that your password is between 8 and 20 characters-length.");
		}
		
		if (ValidationUtils.haveMaxLength(user.getDescription(), 255) == true) {
			error.addError("description", "This field doesn't accept more than 255 characters!");
		}

		if ((ValidationUtils.isEmpty(user.getYoutubeChannelID()) == false)
			&& (ValidationUtils.equals(user.getYoutubeChannelID().substring(0, youtubeChannel.length()), youtubeChannel) == false)) {
			error.addError("youtubeChannelID", "The field is empty.");
		}
		
		if ((ValidationUtils.isEmpty(user.getTwitchChannelID()) == false)
			&& (ValidationUtils.equals(user.getTwitchChannelID().substring(0, twitchChannel.length()), twitchChannel) == false)) {
			error.addError("twitchChannelID", "The field is empty.");
		}

		return error;
	}
	
	private ErrorMessages registerUser(BeanUser user, HttpServletRequest request) {

		ErrorMessages errors = this.validateUserInformation(user);
		
		if (errors.haveErrors() == false) {

			boolean existUser = this.userDAO.existUser(user.getUser(), user.getMail());

			if (existUser == false) {
				
				boolean insertUser = this.userDAO.insertUser(user);
				
				if (insertUser == false) {
					errors.addError("userInsert", "The user can not be inserted in BD");
				}
				
			} else {
				errors.addError("user", "The user already exist!!!");
			}
			
		}
		
		return errors;
		
	}
	
	private void sendResponse(HttpServletRequest request, HttpServletResponse response, ErrorMessages errors)
																				throws ServletException, IOException {
		this.setResponseJSONHeader(response);
		request.setAttribute("errors", errors.getJSON());
		response.getWriter().print(errors.getJSON());
	}

}
