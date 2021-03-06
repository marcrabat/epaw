package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.FeedbackDAO;
import database.LikeDAO;
import database.RelationshipDAO;
import database.TweetDAO;
import database.UserDAO;
import models.BeanUser;
import utils.ErrorMessages;
import utils.SessionUtils;
import utils.ValidationUtils;
import utils.JSONUtils;
import utils.Servlet;

@WebServlet("/checkProfileErrors")

public class ProfileController extends Servlet {
	
	private UserDAO userDAO;
	private TweetDAO tweetDAO;
	private RelationshipDAO relationshipDAO;
	private FeedbackDAO feedbackDAO;
	private LikeDAO likeDAO;
	
	public ProfileController() {
		this.userDAO = new UserDAO();
		this.tweetDAO = new TweetDAO();
		this.relationshipDAO = new RelationshipDAO();
		this.feedbackDAO = new FeedbackDAO();
		this.likeDAO = new LikeDAO();
		
	}

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
		ErrorMessages errors = new ErrorMessages();
		String jsonData = request.getParameter("data");
		
		if (ValidationUtils.isEmpty(jsonData) == false) {
			vistaUser = JSONUtils.returnJSONObject(jsonData, BeanUser.class, "yyyy-MM-dd");
		}

		if (vistaUser != null) {
			
			errors.addError(this.editUser(vistaUser, request));
			

		}
		return errors;
	}
	
	private ErrorMessages editUser(BeanUser editInformation, HttpServletRequest request) {
		
		ErrorMessages errors = this.validateEditInformation(editInformation);
		
		if (errors.haveErrors() == false) {
			
			BeanUser sessionUser = SessionUtils.getSessionUser(request);
			String userToLook = SessionUtils.getSessionUserToLook(request);
			
			if (ValidationUtils.isNull(userToLook) == true) {
				userToLook = editInformation.getUser();
			}
			
			if (sessionUser == null) {
				errors.addError("session", "you don't have session!");
			}
			
			if (errors.haveErrors() == false) {
				boolean existUser = this.userDAO.existUserSelectingField(UserDAO.COLUMN_USER, userToLook);
	
				if (existUser == true) {
					
					editInformation.setUser(userToLook);

					boolean editUser = this.userDAO.updateUserAllInfo(editInformation);
					
					if (editUser == false) {
						errors.addError("userEdit", "sorry, you can't modify your information");
					} else {
						if (ValidationUtils.equals(sessionUser.getUser(), userToLook) == true) {
							BeanUser userBD = this.userDAO.returnUser(UserDAO.COLUMN_NAME, sessionUser.getUser());
							HttpSession session = request.getSession();
							session.setAttribute("userInfo", JSONUtils.getJSON(userBD));
						}
					}
					
				} else {
					
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
		BeanUser userToDelete = null;
		String jsonData = request.getParameter("data");
		
		if (ValidationUtils.isEmpty(jsonData) == false) {
			userToDelete = JSONUtils.returnJSONObject(jsonData, BeanUser.class, "yyyy-MM-dd");
		}
		
		if (userToDelete != null) {
			
			boolean userDeleted = this.deleteAccountInDatabase(userToDelete.getUser());
			
			if (userDeleted == false) {
				errors.addError("delete", "Account can not be deleted, sorry");
			} else {
				if (userToDelete.getIsAdmin() == false) {
					HttpSession session = this.getSession(request);
					session.invalidate();
				}
			}

		} else {
			errors.addError("delete", "Account can not be deletd, sorry");
		}
		
		return errors;
	}
	
	private boolean deleteAccountInDatabase(String userToDelete) {
		boolean deleted = false;
		
		this.deleteAccountTweetsInDatabase(userToDelete);
		
		this.relationshipDAO.deleteUserRelationship(userToDelete);
		deleted = this.userDAO.deleteUser(UserDAO.COLUMN_USER, userToDelete);
		
		
		return deleted;
	}
	
	private boolean deleteAccountTweetsInDatabase(String author) {
		boolean deleted = false;
		
		this.likeDAO.deleteAllUserLikes(author);
		this.feedbackDAO.deleteAllFeedbackReletedToUser(author);
		deleted = this.tweetDAO.deleteAllTweets(author);
		
		return deleted;
	}
	
	private ErrorMessages deleteAllTweets(HttpServletRequest request) {
		ErrorMessages errors = new ErrorMessages();
		BeanUser tweetsAuthor = null;
		String jsonData = request.getParameter("data");
		
		if (ValidationUtils.isEmpty(jsonData) == false) {
			tweetsAuthor = JSONUtils.returnJSONObject(jsonData, BeanUser.class, "yyyy-MM-dd");
		}

		if (tweetsAuthor != null) {
			
			String author = tweetsAuthor.getUser();
			
			int numberOfTweets = this.tweetDAO.countAuthorTweets(author);
			
			if (numberOfTweets > 0) {
			
				boolean tweetsDeleted = this.deleteAccountTweetsInDatabase(author);
				
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
