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

import database.TweetDAO;
import database.UserDAO;
import models.BeanUser;
import utils.BD;
import utils.ErrorMessages;
import utils.ServletUtilities;
import utils.SessionUtils;
import utils.GeneralUtils;
import utils.ValidationUtils;
import utils.JSONUtils;
import utils.Servlet;

@WebServlet("/checkProfileErrors")

public class ProfileController extends Servlet {
	
	public ProfileController() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		
		ErrorMessages errors = new ErrorMessages();
		
		String mode = request.getParameter("mode");
		
		HttpSession session = this.getSession(request);
		
		if (session.getAttribute("Session_ID") != null) {
			
			switch(mode) {
			
				case "editProfile":
					errors.addError(this.editProfile(request));
				break;
				case "deleteAccount":
					errors.addError(this.deleteAccount(request));
					break;
				case "deleteAllTweets":
					errors.addError(this.deleteAllTweets(request));
					break;
			
			}
			
		}

		sendResponse(request, response, errors);
	}

	private ErrorMessages editProfile(HttpServletRequest request) {
		BeanUser vistaUser = null;
		boolean editUser = false;
		ErrorMessages errors = new ErrorMessages();
		String jsonData = request.getParameter("data");
		
		if (ValidationUtils.isEmpty(jsonData) == false) {
			vistaUser = JSONUtils.returnJSONObject(jsonData, BeanUser.class, "yyyy-MM-dd");
		}

		if (vistaUser != null) {
			
			errors.addError(editUser(vistaUser, request));
			
			System.out.println("Errores al editar: " + errors.getJSON());

		}
		return errors;
	}
	
	private ErrorMessages editUser(BeanUser editInformation, HttpServletRequest request) {
		
		UserDAO userDAO = new UserDAO();
		ErrorMessages errors = this.validateEditInformation(editInformation);
		
		if (errors.haveErrors() == false) {
			
			System.out.println("No Errors For Edit");
			BeanUser sessionUser = SessionUtils.getSessionUser(request);
			
			if (sessionUser == null) {
				errors.addError("session", "you don't have session!");
			}
			
			if (errors.haveErrors() == false) {
				boolean existUser = userDAO.existUser(sessionUser.getUser(), sessionUser.getMail());
	
				if (existUser == true) {
					
					editInformation.setUser(sessionUser.getUser());
					editInformation.setMail(sessionUser.getMail());

					boolean editUser = userDAO.updateUserAllInfo(editInformation);
					
					if (editUser == false) {
						System.out.println("Errors");
						errors.addError("userEdit", "sorry, you can't modify your information");
					} else {
						BeanUser userBD = userDAO.returnUser(UserDAO.COLUMN_NAME, sessionUser.getUser());
						HttpSession session = request.getSession();
						session.setAttribute("userInfo", JSONUtils.getJSON(userBD));
					}
					
				} else {
					
					System.out.println("Not exist");
					errors.addError("user", "The user not exist!!!");
				}	
			}
			
		}
		
		return errors;
		
	}
	
	private ErrorMessages validateEditInformation(BeanUser user) {
		ErrorMessages error = new ErrorMessages();
		
		if (ValidationUtils.isEmpty(user.getPassword()) == false) {
			if (ValidationUtils.isBetweenLength(user.getPassword(), 8, 20) == false) {
				error.addError("password", "Check that your password is between 8 and 20 characters-length.");
			}
		}
		
		if (ValidationUtils.haveMaxLength(user.getDescription(), 255) == true) {
			error.addError("description", "This field doesn't accept more than 255 characters!");
		}

		return error;
	}
	
	private ErrorMessages deleteAccount(HttpServletRequest request) {
		ErrorMessages errors = new ErrorMessages();
		UserDAO userDAO = new UserDAO();
		TweetDAO tweetDAO = new TweetDAO();
		BeanUser userToDelete = null;
		String jsonData = request.getParameter("data");
		
		if (ValidationUtils.isEmpty(jsonData) == false) {
			userToDelete = JSONUtils.returnJSONObject(jsonData, BeanUser.class, "yyyy-MM-dd");
		}
		
		boolean userDeleted = false;
		
		if (userToDelete != null) {
			
			tweetDAO.deleteAllTweets(userToDelete.getUser());
			userDeleted = userDAO.deleteUser(UserDAO.COLUMN_USER, userToDelete.getUser());
			
			if (userDeleted == false) {
				errors.addError("delete", "Account can not be deleted, sorry");
			} else {
				HttpSession session = this.getSession(request);
				session.invalidate();
			}

		}
		
		return errors;
	}
	
	private ErrorMessages deleteAllTweets(HttpServletRequest request) {
		ErrorMessages errors = new ErrorMessages();
		TweetDAO tweetDAO = new TweetDAO();
		BeanUser tweetsAuthor = null;
		String jsonData = request.getParameter("data");
		
		if (ValidationUtils.isEmpty(jsonData) == false) {
			tweetsAuthor = JSONUtils.returnJSONObject(jsonData, BeanUser.class, "yyyy-MM-dd");
		}
		
		boolean tweetsDeleted = false;
		
		if (tweetsAuthor != null) {
			
			String author = tweetsAuthor.getUser();
			
			int numberOfTweets = tweetDAO.countAuthorTweets(author);
			
			if (numberOfTweets > 0) {
			
				tweetsDeleted = tweetDAO.deleteAllTweets(author);
				
				if (tweetsDeleted == false) {
					errors.addError("delete", "Tweets can not be deleted, sorry");
				}
				
			} else {
				errors.addError("delete", "You not have tweets to delete");
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
